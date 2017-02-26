package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.Util;
//import org.usfirst.frc.team2791.shakerJoystick.ShakerDriver;
import org.usfirst.frc.team2791.robot.util.ShakerGyro;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;

public class ShakerDrivetrain extends Subsystem{
	protected Encoder leftDriveEncoder = null;
	protected Encoder rightDriveEncoder = null;

	public ShakerGyro gyro;

	protected RobotDrive shakyDrive = null;

	private Talon leftSpark;    
	private Talon rightSpark;

	protected static BasicPID movingAnglePID;
	protected static BasicPID distancePID;
	protected static BasicPID stationaryAnglePID;

	protected static boolean usingPID = false;

	protected double driveTimePIDGoodTime = 0;
	protected double angleTimePIDGoodTime = 0;

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
	
	protected double angleTarget = 0.0;
	protected double turnPIDMaxOutput = 0.5;
	protected boolean PIDAtTarget = false;
	protected boolean anglePIDQuickExit = false;

	private double distancePerPulse = Util.tickToFeet(CONSTANTS.driveEncoderTicks, CONSTANTS.WHEEL_DIAMETER_IN_FEET);

	public ShakerDrivetrain(){
		leftSpark = new Talon(RobotMap.DRIVE_SPARK_LEFT_PORT);
		rightSpark = new Talon(RobotMap.DRIVE_SPARK_RIGHT_PORT);


		leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_PORT_A, RobotMap.LEFT_DRIVE_ENCODER_PORT_B);
		rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_PORT_A, RobotMap.RIGHT_DRIVE_ENCODER_PORT_B);

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

		movingAnglePID = new BasicPID(CONSTANTS.DRIVE_ANGLE_P, CONSTANTS.DRIVE_ANGLE_I, CONSTANTS.DRIVE_ANGLE_D);
		distancePID = new BasicPID(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);
		stationaryAnglePID = new BasicPID(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I, CONSTANTS.STATIONARY_ANGLE_D);

		movingAnglePID.setInvertOutput(true);
		stationaryAnglePID.setInvertOutput(true);
		movingAnglePID.setMaxOutput(0.5);
		movingAnglePID.setMinOutput(-0.5);

		stationaryAnglePID.setIZone(6);
		distancePID.setIZone(0.25);
		movingAnglePID.setIZone(4);
		gyro = new ShakerGyro(SPI.Port.kOnboardCS1);
		gyro.reset();
		(new Thread(gyro)).start();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	public double getAngleEncoder() {
		return (360 / 7.9) * (getLeftDistance() - getRightDistance()) / 2.0;
	}

	public double getGyroAngle() {
		return gyro.getAngle();
	}

	public double getEncoderAngleRate() {
		return (360/7.9) * (leftDriveEncoder.getRate() - rightDriveEncoder.getRate()) / 2.0;
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

	public double getRightDistance() {
		// distance of right encoder
		return rightDriveEncoder.getDistance();
	}

	public boolean isUsingPID() {
		return usingPID;
	}

	public void usePID() {
		usingPID = true;
	}

	public void doneUsingPID() {
		usingPID = false;
	}
	
	/**Stops all the drivetrain motors*/
	public void disable() {
		shakyDrive.stopMotor();
	}

	public void resetGyro() {
		// zero the gyro
		gyro.reset();
	}

	public double getGyroRate() {
		// recalibrate the gyro for
		return gyro.getRate();
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
	
	/**recalibrates the gyro*/
	public void calibrateGyro() {
		System.out.println("Gyro calibrating");
		gyro.recalibrate();
		System.out.println("Done calibrating " + " The current rate is " + gyro.getRate());
	}

	/**@return distance of left encoder
	 * TODO: determine which units we are given and which units we need*/
	public double getLeftDistance() {
		return leftDriveEncoder.getDistance();
	}

	public double getAngle() {
		return getAngleEncoder();

	}
	
	
	/** @param left motor output
	 * @param right motor output*/
	public void setLeftRightMotorOutputs(double left, double right){
		shakyDrive.setLeftRightMotorOutputs(left, right);
	}
	
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

	/**puts values on smart dashdashboard */
	@SuppressWarnings("deprecation")
	public void updateSmartDash() {
		CONSTANTS.STATIONARY_ANGLE_P = SmartDashboard.getNumber("Stat Angle P");
		CONSTANTS.STATIONARY_ANGLE_I = SmartDashboard.getNumber("Stat Angle I");
		CONSTANTS.STATIONARY_ANGLE_D = SmartDashboard.getNumber("Stat Angle D");

		CONSTANTS.DRIVE_ANGLE_P = SmartDashboard.getNumber("Angle P");
		CONSTANTS.DRIVE_ANGLE_I = SmartDashboard.getNumber("Angle I");
		CONSTANTS.DRIVE_ANGLE_D = SmartDashboard.getNumber("Angle D");

		CONSTANTS.DRIVE_DISTANCE_P = SmartDashboard.getNumber("DISTANCE P");
		CONSTANTS.DRIVE_DISTANCE_I = SmartDashboard.getNumber("DISTANCE I");
		CONSTANTS.DRIVE_DISTANCE_D = SmartDashboard.getNumber("Distance D");
		debug();
		updatePIDGains();
	}

	public void debug() {
		SmartDashboard.putNumber("Left Drive Encoders Rate", leftDriveEncoder.getRate());
		SmartDashboard.putNumber("Right Drive Encoders Rate", rightDriveEncoder.getRate());
		SmartDashboard.putNumber("Encoder Angle", getAngleEncoder());
		//		SmartDashboard.putNumber("Encoder Angle Rate Change", getEncoderAngleRate());
		//		SmartDashboard.putNumber("Angle PID Error", stationaryAnglePID.getError());
		SmartDashboard.putNumber("Angle PID Output", stationaryAnglePID.getOutput());
		SmartDashboard.putNumber("Average Encoder Distance", getAverageDist());
		SmartDashboard.putNumber("Left Encoder Distance", getLeftDistance());
		SmartDashboard.putNumber("Right Encoder Distance", getRightDistance());
		//		SmartDashboard.putNumber("Distance PID output", distancePID.getOutput());
		SmartDashboard.putNumber("Distance PID error", distancePID.getError());
		SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
		SmartDashboard.putNumber("Velocity", getAverageVelocity());
		SmartDashboard.putNumber("Acceleration", getAverageAcceleration());
		SmartDashboard.putString("LVel vs RVel vs AvgVel", ""+getLeftVelocity() + ":" + getRightVelocity() + ":" + getAverageVelocity() +"");
		SmartDashboard.putString("LDist vs RDist vs AvgDist", ""+getLeftDistance() + ":" + getRightDistance() + ":" + getAverageDist()+"");

	}
	public void updatePIDGains() {
		movingAnglePID.changeGains(CONSTANTS.DRIVE_ANGLE_P, CONSTANTS.DRIVE_ANGLE_I, CONSTANTS.DRIVE_ANGLE_D);
		distancePID.changeGains(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);
		stationaryAnglePID.changeGains(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I,
				CONSTANTS.STATIONARY_ANGLE_D);
	}

	public double getGyroAngleInRadians() {
		return getGyroAngle() * (Math.PI/180);
	}
}