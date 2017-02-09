package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class runHopperWithJoystick extends Command {
	public runHopperWithJoystick() {
		
		super("HopperWithJoystick");
		requires(Robot.hopper);// Use requires() here to declare subsystem dependencies
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
		//logic interprets operator Joystick position for motor outputs 
	    double combinedLeft;
	    
	 	combinedLeft=Robot.oi.operator.getGtaDriveLeft();
//	 	Robot.hopper.setHopperOutput(combinedLeft);
    }

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {//false because always running unless interrupted
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.hopper.stopMotor();
	}

	protected void interrupted() {
	}

}
