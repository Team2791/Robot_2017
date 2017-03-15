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

public class ShakerDrivetrain extends Subsystem{
	
	private final double VBUS_RAMP_RATE = 5;// in %vbus per second
	protected double last_vbus_output_left = 0;
	protected double last_vbus_output_right = 0;
	protected double last_ramp_update_time = -1;

	protected Encoder leftDriveEncoder = null;
	protected Encoder rightDriveEncoder = null;

	public ADXRS450_Gyro gyro;
	public boolean gyroDisabled = false;

	protected RobotDrive shakyDrive = null;

	private Talon leftSpark;    
	private Talon rightSpark;

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

	private double distancePerPulse = Util.tickToFeet(CONSTANTS.driveEncoderTicks, CONSTANTS.WHEEL_DIAMETER_IN_FEET);
	
	/* 
	 * Spark speed controllers can be controlled with the WPI Talon class.
	 * Each Side of the robot has 3 talons, which all recieve signal/send feedback through 1 PWM Port
	 */

	public ShakerDrivetrain(){
		leftSpark = new Talon(RobotMap.DRIVE_SPARK_LEFT_PORT);
		rightSpark = new Talon(RobotMap.DRIVE_SPARK_RIGHT_PORT);

		leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_PORT_A, RobotMap.LEFT_DRIVE_ENCODER_PORT_B);
		rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_PORT_A,RobotMap.RIGHT_DRIVE_ENCODER_PORT_B);

		// use the talons to create a roboDrive (it has methods that allow for easier control)
		shakyDrive = new RobotDrive(leftSpark, rightSpark);
		shakyDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		shakyDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

		// stop all motors right away just in case
		shakyDrive.stopMotor();

		leftDriveEncoder.reset();
		rightDriveEncoder.reset();

		leftDriveEncoder.setDistancePerPulse(distancePerPulse); 
		rightDriveEncoder.setDistancePerPulse(-distancePerPulse); 

		try{
			gyro = new ADXRS450_Gyro();//SPI.Port.kOnboardCS1
			gyro.calibrate();
			gyro.reset();
		}catch(NullPointerException e){
			gyroDisabled = true;
			System.out.println("Gyro is unplugged, Disabling Gyro");
		}

	}
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	public void disable() {
		shakyDrive.stopMotor();
	}

	// TODO put this in a util method
	public double limitWithRampRate(double previousOutput, double currentDesiredOutput, double timeDiff) {
		double maxLimitedOutput = Math.abs(previousOutput) + timeDiff * VBUS_RAMP_RATE;
		
		if(Math.abs(currentDesiredOutput) > maxLimitedOutput) {
			return Math.copySign(maxLimitedOutput, currentDesiredOutput);
		} else {
			return currentDesiredOutput;
		}
	}
	
	/** @param left motor output
	 * @param right motor output*/
	public void setLeftRightMotorOutputs(double left, double right){
		double currentTime = Timer.getFPGATimestamp();
		double timeDiff = currentTime - last_ramp_update_time;
		last_ramp_update_time = currentTime;
		
		left = limitWithRampRate(last_vbus_output_left, left, timeDiff);
		last_vbus_output_left = left;
		
		right = limitWithRampRate(last_vbus_output_right, right, timeDiff);
		last_vbus_output_right = right;
		
		SmartDashboard.putString("LeftOutput vs RightOutput", left+":"+right);
		shakyDrive.setLeftRightMotorOutputs(left, right);
	}

	//************** Gyro and Encoder Helper Methods **************//

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

	public void reset() {
		disable();
		rightDriveEncoder.reset();
		leftDriveEncoder.reset();
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

	public double getGyroRate() {
		if(!gyroDisabled)
			return gyro.getRate();
		System.err.println("Gyro is Disabled, Rate is Incorrect");
		return 0.0;
	}

	public double getGyroAngleInRadians() {
		return getGyroAngle() * (Math.PI/180);
	}

	/**recalibrates the gyro*/


	//************** Pos/Vel/Acc Helper Methods **************// 

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

	public double getRightDistance() {
		// distance of right encoder
		return rightDriveEncoder.getDistance();
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

	/**@return average distance of both encoder velocities */
	public double getAverageDist() {
		return (getLeftDistance() + getRightDistance()) / 2;
	}

	public void calibrateGyro() {
		if(!gyroDisabled){
			System.out.println("Gyro calibrating");
			gyro.calibrate();
			System.out.println("Done calibrating " + " The current rate is " + gyro.getRate());
		}
	}

	/**@return distance of left encoder
	 * TODO: determine which units we are given and which units we need*/
	public double getLeftDistance() {
		return leftDriveEncoder.getDistance();
	}

	//*****************Debugging Methods*****************//

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
