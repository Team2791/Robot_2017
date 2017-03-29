package org.usfirst.frc.team2791.robot.commands.safeties;

import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Safely turns shooter motors off, Shooter Talons should be set to Coast.
 */
public class TurnShooterOff extends Command{
	public TurnShooterOff() {
		super("StopShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}
	
	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		Robot.hopper.stopHopper();
		Robot.shooter.stopMotors();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.stopMotors();
	}

	@Override
	protected void interrupted() {
		new TurnShooterOff();
	}
}

