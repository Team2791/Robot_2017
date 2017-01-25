package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.OI;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerDriver;
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

public class ShakerDrivetrain extends Subsystem{
	
    protected Encoder leftDriveEncoder = null;
    protected Encoder rightDriveEncoder = null;
     	
    protected ShakerGyro gyro;
	
    protected RobotDrive shakyDrive = null;

    private Talon leftTalonA;
    private Talon leftTalonB;
    private Talon leftTalonC;
    
    private Talon rightTalonA;
    private Talon rightTalonB;
    private Talon rightTalonC;
    
    public void initDefaultCommand() {
	//is there where I declare a drivetrain or will it reset a drivetrain every single time?
		
	// Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
		
	this.leftTalonA = new Talon(RobotMap.DRIVE_TALON_LEFT_PORT_FRONT);
        this.leftTalonB = new Talon(RobotMap.DRIVE_TALON_LEFT_PORT_BACK);
        this.leftTalonC = new Talon(RobotMap.DRIVE_TALON_LEFT_PORT_C);

        this.rightTalonA = new Talon(RobotMap.DRIVE_TALON_RIGHT_PORT_FRONT);
        this.rightTalonB = new Talon(RobotMap.DRIVE_TALON_RIGHT_PORT_BACK);
        this.rightTalonC = new Talon(RobotMap.DRIVE_TALON_RIGHT_PORT_C);

        this.leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_PORT_A, RobotMap.LEFT_DRIVE_ENCODER_PORT_B);
        this.rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCOODER_PORT_A,
                RobotMap.RIGHT_DRIVE_ENCODER_PORT_B);
        
        // use the talons to create a roboDrive (it has methods that allow for easier control)
        this.shakyDrive = new RobotDrive(new TalonSet(leftTalonA, leftTalonB, leftTalonC),
                new TalonSet(rightTalonA, rightTalonB, rightTalonC));
        //robotDrive = new RobotDrive(leftTalonA, leftTalonB, rightTalonA, rightTalonB);
        // stop all motors right away just in case
        shakyDrive.stopMotor();

	leftDriveEncoder.reset();
	rightDriveEncoder.reset();
		
	leftDriveEncoder.setDistancePerPulse(Util.tickToFeet(CONSTANTS.driveEncoderTicks, CONSTANTS.WHEEL_DIAMETER)); 
	rightDriveEncoder.setDistancePerPulse(-Util.tickToFeet(CONSTANTS.driveEncoderTicks, CONSTANTS.WHEEL_DIAMETER)); 

//	gyro = new ShakerGyro(SPI.Port.kOnboardCS1);
//	(new Thread(gyro)).start();
		
	}
	
    public void driveWithJoystick(){
	    //logic interprets driver Joystick position for motor outputs 
	    double combinedLeft, combinedRight;
	    //if we need to change the speed we can change the .35 FIRST and then the /3 ONLY if thats not enough
	    if(OI.driver.getButtonRB()){
		combinedLeft=0.35+OI.driver.getAxisLeftX()/3;
		combinedRight=0.35-(double) (OI.driver.getAxisLeftX())/3.0;
	    }
	    else if(OI.driver.getButtonLB()){
		combinedLeft=-1*(0.35+OI.driver.getAxisLeftX()/3);
		combinedRight=-1*(0.35-OI.driver.getAxisLeftX()/3);
	    }
	    else{
	 	combinedLeft=OI.driver.getGtaDriveLeft();
		combinedRight=OI.driver.getGtaDriveRight();
	    }
	    shakerDrive(combinedLeft,combinedRight);
	}
	
    //set motor output according to above interpretation
    public void shakerDrive(double left, double right){
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
