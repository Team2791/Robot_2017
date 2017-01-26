package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {
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
		Robot.drivetrain.driveWithJoystick();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {//false because always running unless interrupted
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.shakerDrive(0.0,0.0);
	}

	protected void interrupted() {
	}

}
