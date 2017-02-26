package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {
	public DriveWithJoystick() {
		
		super("DriveWithJoystick");
		requires(Robot.drivetrain);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor");
	}
	protected void initialize() {
		System.out.println("came to initialize");
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Drive with joystick executig");
		
		//logic interprets driver Joystick position for motor outputs 
	    double combinedLeft, combinedRight;
	    
	    //if we need to change the speed we can change the .35 FIRST and then the /3 ONLY if thats not enough
	    double turn=(double)(Robot.oi.driver.getAxisLeftX())/3.0;
	    
	    if(Robot.oi.driver.getButtonRB()){
	    	
		combinedLeft = 0.35-turn;
		combinedRight = 0.35+turn;
		
	    }else if(Robot.oi.driver.getButtonLB()){
	    	
		combinedLeft = -1*(0.35+turn);
		combinedRight = -1*(0.35-turn);
		
	    }else{
	    	
	 	combinedRight = Robot.oi.driver.getGtaDriveRight();
		combinedLeft = Robot.oi.driver.getGtaDriveLeft();
		
	    }
//	    System.out.println("DT left = "+combinedLeft+" DT right = "+combinedRight);
	    Robot.drivetrain.setLeftRightMotorOutputs(combinedLeft, combinedRight);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {//false because always running unless interrupted
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.setLeftRightMotorOutputs(0.0,0.0);
	}

	protected void interrupted() {
		System.out.println("Drive with joystik interrupted.");
	}

}
