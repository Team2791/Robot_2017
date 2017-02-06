package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.OI;
import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerDriver;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.Util;
import org.usfirst.frc.team2791.robot.util.TalonSet;
//import org.usfirst.frc.team2791.shakerJoystick.ShakerDriver;
//import org.usfirst.frc.team2791.util.RobotMap;
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
     	
    protected ShakerGyro gyro;
	
    protected RobotDrive shakyDrive = null;

    private Talon leftSparkA;
    private Talon leftSparkB;
    private Talon leftSparkC;
    
    private Talon rightSparkA;
    private Talon rightSparkB;
    private Talon rightSparkC;
    
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
    
    protected double angleTarget = 0.0;
    protected double turnPIDMaxOutput = 0.5;
    protected boolean PIDAtTarget = false;
    protected boolean anglePIDQuickExit = false;
    
    public void initDefaultCommand() {
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
		
	this.leftSparkA = new Talon(RobotMap.DRIVE_TALON_LEFT_PORT);
    

    this.rightSparkA = new Talon(RobotMap.DRIVE_TALON_RIGHT_PORT);
    

    this.leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_PORT_A, RobotMap.LEFT_DRIVE_ENCODER_PORT_B);
    this.rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_PORT_A,RobotMap.RIGHT_DRIVE_ENCODER_PORT_B);
    
    // use the talons to create a roboDrive (it has methods that allow for easier control)
    this.shakyDrive = new RobotDrive(rightSparkA, leftSparkA);
    //robotDrive = new RobotDrive(leftTalonA, leftTalonB, rightTalonA, rightTalonB);
    // stop all motors right away just in case
    shakyDrive.stopMotor();

	leftDriveEncoder.reset();
	rightDriveEncoder.reset();
		
	leftDriveEncoder.setDistancePerPulse(Util.tickToFeet(CONSTANTS.driveEncoderTicks, CONSTANTS.WHEEL_DIAMETER)); 
	rightDriveEncoder.setDistancePerPulse(-Util.tickToFeet(CONSTANTS.driveEncoderTicks, CONSTANTS.WHEEL_DIAMETER)); 

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
//	(new Thread(gyro)).start();
		
	}
	
    @SuppressWarnings("deprecation")
	public void updateSmartDash() {
		// put values on the smart dashboard
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
		SmartDashboard.putNumber("Encoder Angle Rate Change", getEncoderAngleRate());
		SmartDashboard.putNumber("Angle PID Error", stationaryAnglePID.getError());
		SmartDashboard.putNumber("Angle PID Output", stationaryAnglePID.getOutput());
		SmartDashboard.putNumber("Average Encoder Distance", getAverageDist());
		SmartDashboard.putNumber("Left Encoder Distance", getLeftDistance());
		SmartDashboard.putNumber("Right Encoder Distance", getRightDistance());
		SmartDashboard.putNumber("Distance PID output", distancePID.getOutput());
		SmartDashboard.putNumber("Distance PID error", distancePID.getError());
	}
	public void updatePIDGains() {
		movingAnglePID.changeGains(CONSTANTS.DRIVE_ANGLE_P, CONSTANTS.DRIVE_ANGLE_I, CONSTANTS.DRIVE_ANGLE_D);
		distancePID.changeGains(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);
		stationaryAnglePID.changeGains(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I,
				CONSTANTS.STATIONARY_ANGLE_D);
	}
	public double getAngleEncoder() {
		return (360 / 7.9) * (getLeftDistance() - getRightDistance()) / 2.0;
	}
	public double getGyroAngle() {
		return gyro.getAngle();
//		return 0;
	}

	public double getEncoderAngleRate() {
		return (360/7.9) * (leftDriveEncoder.getRate() - rightDriveEncoder.getRate()) / 2.0;

	}

	public double getAverageAcceleration() {
		double acceleration = currentRate - previousRate;
		acceleration /= (currentTime - previousRateTime);
		previousRate = currentRate;
		previousRateTime = currentTime;
		currentRate = getAverageVelocity();
		currentTime = Timer.getFPGATimestamp();
		return acceleration;
	}

	public void reset() {
		this.disable();
		this.rightDriveEncoder.reset();
		this.leftDriveEncoder.reset();
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
	public void disable() {
		// Stops all the motors
		this.shakyDrive.stopMotor();
	}
	 public void resetGyro() {
	 // zero the gyro
		 gyro.reset();
	 }
	//
	 public double getGyroRate() {
	 // recalibrate the gyro for
		 return gyro.getRate();
	 }
	//

	public double getLeftVelocity() {
		return leftDriveEncoder.getRate();
	}

	public double getRightVelocity() {
		return rightDriveEncoder.getRate();
	}

	public double getAverageVelocity() {
		// average of both encoder velocities
		return (getLeftVelocity() + getRightVelocity()) / 2;
	}

	public double getAverageDist() {
		// average distance of both encoders
		return (getLeftDistance() + getRightDistance()) / 2;
	}

	public void calibrateGyro() {
//		 recalibrate the gyro
		System.out.println("Gyro calibrating");
		gyro.recalibrate();
		System.out.println("Done calibrating " + " The current rate is " + gyro.getRate());
	}

	public double getLeftDistance() {
		// distance of left encoder
		return leftDriveEncoder.getDistance();// convert distance from feet
		// to inches;
	}

	public double getAngle() {
		return getAngleEncoder();

	}
    //set motor output according to above interpretation
    public void setLeftRightMotorOutputs(double left, double right){
    	shakyDrive.setLeftRightMotorOutputs(left, right);
    }
	
    //don't need any of this stuff below b/c of the interpreter
    /*
    public void forward(double distance) {}
	
    public void left(double distance) {}
	
    public void right(double distance) {}
	
    public void back(double distance) {}
    */
	
}
