package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
//import org.usfirst.frc.team2791.shakerJoystick.ShakerDriver;
//import org.usfirst.frc.team2791.util.RobotMap;
import org.usfirst.frc.team2791.robot.util.ShakerGyro;
import org.usfirst.frc.team2791.robot.util.Util;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class corresponds to the drivetrain. The drivetrain is 6 wheel, center drop with 2 CIMs, 1 miniCIM on each
 * side. Each motor output is controlled by a Spark speed controller. The Rio outputs signal through a single PWM 
 * per side which is branched off to all three speed controllers. There is a 256 count Greyhill encoder on each side.
 * We have an ADXRS453 gyro in the robot (writing side up). Max velocity ~15 feet/sec.
 * 
 * @author team2791: See Robot.java for contact info
 */
public class ShakerDrivetrain extends Subsystem{

	//Spark speed controllers can be controlled with the WPI Talon class.
	private Talon leftSpark;    
	private Talon rightSpark;
	
	protected Encoder leftDriveEncoder = null;
	protected Encoder rightDriveEncoder = null;

	public ADXRS450_Gyro gyro;
	public boolean gyroDisabled = false;

	protected RobotDrive shakyDrive = null;

	private final double VBUS_RAMP_RATE = 5;// in %vbus per second
	protected double last_vbus_output_left = 0;
	protected double last_vbus_output_right = 0;
	protected double last_ramp_update_time = -1;
	
	protected double previousRate = 0;
	protected double previousRateTime = 0;
	protected double currentRate = 0;
	protected double currentTime = 0;

	protected double leftPreviousRate = 0;
	protected double leftPreviousTime = 0;
	protected double leftCurrentRate = 0;
	protected double leftCurrentTime = 0;
	protected double leftFilteredAccel = 0;

	protected double rightPreviousRate = 0;
	protected double rightPreviousTime = 0;
	protected double rightCurrentRate = 0;
	protected double rightCurrentTime = 0;
	protected double rightFilteredAccel = 0;

	//Determines the amount of distance traveled for every pulse read on the encoders
	private double distancePerPulse = Util.tickToFeet(CONSTANTS.driveEncoderTicks, CONSTANTS.WHEEL_DIAMETER_IN_FEET);

	public ShakerDrivetrain(){
		
		leftSpark = new Talon(RobotMap.DRIVE_SPARK_LEFT_PORT);
		rightSpark = new Talon(RobotMap.DRIVE_SPARK_RIGHT_PORT);

		leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_PORT_A, RobotMap.LEFT_DRIVE_ENCODER_PORT_B);
		rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_PORT_A,RobotMap.RIGHT_DRIVE_ENCODER_PORT_B);

		//Uses the Sparks to create a robotDrive (it has methods that allow for easier control of the whole drivetrain at once)
		shakyDrive = new RobotDrive(leftSpark, rightSpark);
		
		//Inverts the motor outputs so that the right and left motors both turn the right direction for forward drive
		shakyDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		shakyDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

		// stops all motors right away just in case
		shakyDrive.stopMotor();

		leftDriveEncoder.reset();
		rightDriveEncoder.reset();

		//configures the encoder so that it can be used in drive PID functions
		leftDriveEncoder.setDistancePerPulse(distancePerPulse); 
		rightDriveEncoder.setDistancePerPulse(-distancePerPulse); 

		//keeps the gyro from throwing startCompetition() errors and allows us to troubleshoot errors
		try{
			gyro = new ADXRS450_Gyro();//SPI.Port.kOnboardCS1
			gyro.calibrate(); //takes 5 seconds
			gyro.reset();
		}catch(NullPointerException e){
			gyroDisabled = true;
			System.out.println("Gyro is unplugged, Disabling Gyro");
		}

	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	/**
	 * Stops the drivetrain
	 */
	public void disable() {
		shakyDrive.stopMotor();
	}
	
	/** 
	 * Drivetrain motor outputs; Accepts values between -1.0 and +1.0
	 * @param left motor output
	 * @param right motor output*/
	public void setLeftRightMotorOutputs(double left, double right){
		double currentTime = Timer.getFPGATimestamp();
		double timeDiff = currentTime - last_ramp_update_time;
		last_ramp_update_time = currentTime;
		
		left = Util.limitWithRampRate(last_vbus_output_left, left, timeDiff, VBUS_RAMP_RATE);
		last_vbus_output_left = left;
		
		right = Util.limitWithRampRate(last_vbus_output_right, right, timeDiff, VBUS_RAMP_RATE);
		last_vbus_output_right = right;
		
		SmartDashboard.putString("LeftOutput vs RightOutput", left+":"+right);
		shakyDrive.setLeftRightMotorOutputs(left, right);
	}

	/**
	 * Drivetrain sfx outputs
	 */
	public void debug() {

		SmartDashboard.putNumber("Left Drive Encoders Rate", leftDriveEncoder.getRate());
		SmartDashboard.putNumber("Right Drive Encoders Rate", rightDriveEncoder.getRate());
		SmartDashboard.putNumber("Encoder Angle", getAngleEncoder());

		SmartDashboard.putNumber("LEncoderDistance", leftDriveEncoder.getDistance());
		SmartDashboard.putNumber("REncoderDistance", rightDriveEncoder.getDistance());
		SmartDashboard.putNumber("AvgEncoderDistance", getAverageDist());

		SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
		SmartDashboard.putNumber("Gyro rate", gyro.getRate());

		SmartDashboard.putNumber("Avg Acceleration", getAverageAcceleration());

		SmartDashboard.putString("LDist vs RDist vs AvgDist", getLeftDistance()+":"+getRightDistance()+":"+getAverageDist());
		SmartDashboard.putString("LVel vs RVel vs AvgVel", getLeftVelocity()+":"+getRightVelocity()+":"+getAverageVelocity());
		SmartDashboard.putString("LAcc vs RAcc vs AvgAcc", getLeftAcceleration()+":"+getRightAcceleration()+":"+getAverageAcceleration());
		
	}
	
	
	
	//************** Gyro and Encoder Helper Methods **************//

	@Deprecated
	public double getAngleEncoder() {
		return (360 / 7.9) * (getLeftDistance() - getRightDistance()) / 2.0;
	}

	public double getGyroAngle() {
		if(!gyroDisabled)  
			return gyro.getAngle();
		System.err.println("Gyro is Disabled, Angle is Incorrect");
		return 0.0;
	}
	
	@Deprecated
	public double getEncoderAngleRate() {
		return (360/7.9) * (leftDriveEncoder.getRate() - rightDriveEncoder.getRate()) / 2.0;
	}
	
	public double getGyroRate() {
		if(!gyroDisabled)
			return gyro.getRate();
		System.err.println("Gyro is Disabled, Rate is Incorrect");
		return 0.0;
	}

	public double getGyroAngleInRadians() {
		return getGyroAngle() * (Math.PI/180);
	}

	/**
	 * resets the left and right drive encoders;
	 * resets the gyro;
	 * stops the drivetrain
	 */
	public void reset() {
		disable();
		resetGyro();
		resetEncoders();
	}

	public void resetEncoders() {
		// zero the encoders
		leftDriveEncoder.reset();
		rightDriveEncoder.reset();
	}
	public void resetGyro() {
		if(!gyroDisabled)
			gyro.reset();
		else{
			System.err.println("Gyro is Disabled, Unable to Reset");
		}
	}
	
	public void calibrateGyro() {
		if(!gyroDisabled){
			System.out.println("Gyro calibrating");
			gyro.calibrate();
			System.out.println("Done calibrating " + " The current rate is " + gyro.getRate());
		}
	}

		
	
	//************** Pos/Vel/Acc Helper Methods **************// 
	
	/**
	 * @return distance traveled by left side based on encoder
	 */
	public double getLeftDistance() {
		return leftDriveEncoder.getDistance();
	}
	
	/**
	 * @return distance traveled by right side based on encoder
	 */
	public double getRightDistance() {
		return rightDriveEncoder.getDistance();
	}

	/**@return average distance of both encoder velocities */
	public double getAverageDist() {
//		return (getLeftDistance() + getRightDistance()) / 2;
//		left side commented out due to potential wiring issues
		return getLeftDistance();
	}
	
	public double getLeftVelocity() {
		return leftDriveEncoder.getRate();
	}

	public double getRightVelocity() {
		return rightDriveEncoder.getRate();
	}

	/**@return average velocity of both encoder velocities */
	public double getAverageVelocity() {
		return (getLeftVelocity() + getRightVelocity()) / 2;
	}
	
	public double getLeftAcceleration() {

		leftCurrentRate = getLeftVelocity();
		leftCurrentTime = Timer.getFPGATimestamp();

		double acceleration = (leftCurrentRate - leftPreviousRate) / (leftCurrentTime - leftPreviousTime);

		leftPreviousRate = leftCurrentRate;
		leftPreviousTime = leftCurrentTime;

		leftFilteredAccel = acceleration * 0.5 + leftFilteredAccel * 0.5;

		return leftFilteredAccel;
	}	

	public double getRightAcceleration() {

		rightCurrentRate = getLeftVelocity();
		rightCurrentTime = Timer.getFPGATimestamp();

		double acceleration = (rightCurrentRate - rightPreviousRate) / (rightCurrentTime - rightPreviousTime);

		rightPreviousRate = rightCurrentRate;
		rightPreviousTime = rightCurrentTime;

		rightFilteredAccel = acceleration * 0.5 + rightFilteredAccel * 0.5;

		return rightFilteredAccel;
	}	

	public double getAverageAcceleration() {

		currentRate = getAverageVelocity();
		currentTime = Timer.getFPGATimestamp();

		double acceleration = (currentRate - previousRate) / (currentTime - previousRateTime);

		previousRate = currentRate;
		previousRateTime = currentTime;

		return acceleration;
	}

	
	
	//*****************Debugging Methods*****************//

	/**
	 * @return total current usage for all 6 motors in the drivetrain
	 */
	public double getCurrentUsage() {
		double totalCurrent = 0.0;
		for(int i=0; i<=2; i++){
			totalCurrent += Robot.pdp.getCurrent(i);
		}
		for(int i=13; i<=15; i++){
			totalCurrent += Robot.pdp.getCurrent(i);
		}
		return totalCurrent;
	}

}
