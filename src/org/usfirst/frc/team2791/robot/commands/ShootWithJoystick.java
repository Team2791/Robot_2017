package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShootWithJoystick extends Command {
	public ShootWithJoystick() {
		
		super("DriveWithJoystick");
		requires(Robot.shooter);// Use requires() here to declare subsystem dependencies
		requires(Robot.hopper);
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
		Robot.hopper.runHopper();
		System.out.println("I'm trying to execute");
		//logic interprets driver Joystick position for motor outputs 
	    double combinedLeft, combinedRight;
	    //if we need to change the speed we can change the .35 FIRST and then the /3 ONLY if thats not enough
	    if(Robot.oi.operator.getButtonRB()){
		combinedLeft=0.35+Robot.oi.operator.getAxisLeftX()/3;
		combinedRight=0.35-(double) (Robot.oi.operator.getAxisLeftX())/3.0;
	    }
	    else if(Robot.oi.driver.getButtonLB()){
		combinedLeft=-1*(0.35+Robot.oi.operator.getAxisLeftX()/3);
		combinedRight=-1*(0.35-Robot.oi.operator.getAxisLeftX()/3);
	    }
	    else{
	 	combinedLeft=Robot.oi.operator.getGtaDriveLeft();
		combinedRight=Robot.oi.operator.getGtaDriveRight();
	    }
	    Robot.shooter.setTrigger(combinedLeft);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {//false because always running unless interrupted
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.shooter.disable();
	}

	protected void interrupted() {//called when another command requires the same subsystem to run itself
		new ShootWithJoystick();
	}

}
