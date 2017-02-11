/*
package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.Command;

public class runWallShot extends Command{
	public runWallShot() {
		// Use requires() here to declare subsystem dependencies
		super("runWallShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
		System.out.println("Came to wall shooter constructor");
		initialize();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("initializing wall shot");
		execute();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Running a wall shot");
		Robot.shooter.setShooterSolenoidState(false);
		Robot.shooter.prepWallShot();
		Robot.hopper.runHopper();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if(Robot.oi.operator.getButtonX() == false)
			return true;
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.hopper.stopHopper();
		Robot.shooter.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
*/