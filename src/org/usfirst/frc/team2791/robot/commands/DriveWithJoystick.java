package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {
	
	private double speedMultiplier = .5;
	
	public DriveWithJoystick() {
		
		super("DriveWithJoystick");
		requires(Robot.drivetrain);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor");
		initialize();
	}
	protected void initialize() {
		System.out.println("came to initialize");
		execute();
	}
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("I'm trying to execute");
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
	    Robot.drivetrain.setLeftRightMotorOutputs(speedMultiplier*combinedLeft,speedMultiplier*combinedRight);
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

	protected void interrupted() {//called when another command requires the same subsystem to run itself
		new DriveWithJoystick();
	}

}
