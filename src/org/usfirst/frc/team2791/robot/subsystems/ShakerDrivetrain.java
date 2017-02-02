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
import edu.wpi.first.wpilibj.command.Subsystem;

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
	//is there where I declare a drivetrain or will it reset a drivetrain every single time?
		
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
		
	this.leftSparkA = new Talon(RobotMap.DRIVE_TALON_LEFT_PORT);
    this.leftSparkB = new Talon(RobotMap.DRIVE_TALON_LEFT_PORT);
    this.leftSparkC = new Talon(RobotMap.DRIVE_TALON_LEFT_PORT);

    this.rightSparkA = new Talon(RobotMap.DRIVE_TALON_RIGHT_PORT);
    this.rightSparkB = new Talon(RobotMap.DRIVE_TALON_RIGHT_PORT);
    this.rightSparkC = new Talon(RobotMap.DRIVE_TALON_RIGHT_PORT);

    this.leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_PORT_A, RobotMap.LEFT_DRIVE_ENCODER_PORT_B);
    this.rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_PORT_A,RobotMap.RIGHT_DRIVE_ENCODER_PORT_B);
    
    // use the talons to create a roboDrive (it has methods that allow for easier control)
    this.shakyDrive = new RobotDrive(new TalonSet(leftSparkA, leftSparkB, leftSparkC), new TalonSet(rightSparkA, rightSparkB, rightSparkC));
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
	
   /* public void driveWithJoystick(){
	    //logic interprets driver Joystick position for motor outputs 
	    double combinedLeft, combinedRight;
	    //if we need to change the speed we can change the .35 FIRST and then the /3 ONLY if thats not enough
	    if(Robot.oi.driver.getButtonRB()){
		combinedLeft=0.35+Robot.oi.driver.getAxisLeftX()/3;
		combinedRight=0.35-(double) (Robot.oi.driver.getAxisLeftX())/3.0;
	    }
	    else if(Robot.oi.driver.getButtonLB()){
		combinedLeft=-1*(0.35+Robot.oi.driver.getAxisLeftX()/3);
		combinedRight=-1*(0.35-Robot.oi.driver.getAxisLeftX()/3);
	    }
	    else{
	 	combinedLeft=Robot.oi.driver.getGtaDriveLeft();
		combinedRight=Robot.oi.driver.getGtaDriveRight();
	    }
	    setLeftRightMotorOutputs(combinedLeft,combinedRight);
	} */
	
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
