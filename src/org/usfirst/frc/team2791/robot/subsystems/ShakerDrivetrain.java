package org.usfirst.frc.team2791.robot.subsystems;

import static org.usfirst.frc.team2791.robot.Robot.driverJoystick;

import org.usfirst.frc.team2791.util.Constants;
import org.usfirst.frc.team2791.util.ShakerGyro;

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
		
	this.leftTalonA = new Talon(Constants.DRIVE_TALON_LEFT_PORT_FRONT);
        this.leftTalonB = new Talon(Constants.DRIVE_TALON_LEFT_PORT_BACK);
        this.leftTalonC = new Talon(Constants.DRIVE_TALON_LEFT_PORT_C);

        this.rightTalonA = new Talon(Constants.DRIVE_TALON_RIGHT_PORT_FRONT);
        this.rightTalonB = new Talon(Constants.DRIVE_TALON_RIGHT_PORT_BACK);
        this.rightTalonC = new Talon(Constants.DRIVE_TALON_RIGHT_PORT_C);

        this.leftDriveEncoder = new Encoder(Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B);
        this.rightDriveEncoder = new Encoder(Constants.RIGHT_DRIVE_ENCOODER_PORT_A,
                Constants.RIGHT_DRIVE_ENCODER_PORT_B);
        
        // use the talons to create a roboDrive (it has methods that allow for easier control)
        this.shakyDrive = new RobotDrive(new TalonSet(leftTalonA, leftTalonB, leftTalonC),
                new TalonSet(rightTalonA, rightTalonB, rightTalonC));
        //robotDrive = new RobotDrive(leftTalonA, leftTalonB, rightTalonA, rightTalonB);
        // stop all motors right away just in case
        shakyDrive.stopMotor();

	leftDriveEncoder.reset();
	rightDriveEncoder.reset();
		
	leftDriveEncoder.setDistancePerPulse(Util.tickToFeet(driveEncoderTicks, Constants.WHEEL_DIAMETER)); 
	rightDriveEncoder.setDistancePerPulse(-Util.tickToFeet(driveEncoderTicks, Constants.WHEEL_DIAMETER)); 

//	gyro = new ShakerGyro(SPI.Port.kOnboardCS1);
//	(new Thread(gyro)).start();
		
	}
	
	//this should be "joystickInterpreter" or something
	public void driveWithJoystick(){
	    //logic interprets driver Joystick position for motor outputs 
	    if(driver.getButtonRB())
		shakyDrive.setLeftRightMotorOutputs(0.35+driverJoystick.getAxisLeftX()/3,0.35-driverJoystick.getAxisLeftX()/3); //if we need to change the speed we can change the .35 FIRST and then the /3 ONLY if thats not enough
	    else{
		double leftAxis;//this sets the amount of power for the left side for GTA
	        if (super.getAxisLeftX() < 0)
	            	leftAxis = -Math.pow(super.getAxisLeftX(), 2) - offset;
	        else
	          	leftAxis = Math.pow(super.getAxisLeftX(), 2) + offset;
	        double combinedLeft = leftAxis + super.getAxisRT() - super.getAxisLT();
	        
	        if (combinedLeft > 1.0)
	           	combinedLeft = 1.0;
	        else if (combinedLeft < -1.0)
	            	combinedLeft = -1.0;
	        
	        double rightAxis;//this sets the amount of power for the right side for GTA
	        if (super.getAxisLeftX() < 0)
	            	rightAxis = -Math.pow(super.getAxisLeftX(), 2) - offset;
	       	else
	            	rightAxis = Math.pow(super.getAxisLeftX(), 2) + offset;
	       	double combinedRight = -rightAxis + super.getAxisRT() - super.getAxisLT();
	        if (combinedRight > 1.0)
	           	combinedRight = 1.0;
	       	else if (combinedRight < -1.0)
	            	combinedRight = -1.0;
	        
	    }
	    shakerDrive(combinedLeft,combinedRight);

	}
	
	//set motor output according to above interpretation
	public void shakerDrive(double left, double right){
		shakyDrive.setLeftRightMotorOutput(left, right);
	}
	
	//don't need any of this stuff below b/c of the interpreter
	/*
	public void forward(double distance) {
		
	}
	
	public void left(double distance) {
		
	}
	
	public void right(double distance) {
		
	}
	
	public void back(double distance) {
		
	}
	*/
	
}
