package org.usfirst.frc.team2791.robot.commands.safeties;

import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2791.robot.subsystems.*;

/**
 * Safely turns {@link ShakerShooter} and {@link ShakerHopper} motors off, Shooter Talons should be set to Coast.
 */
public class ShooterHopperSafety extends Command{
	public ShooterHopperSafety() {
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
		Robot.hopper.stopHopper();
		Robot.shooter.stopMotors();
	}

	@Override
	protected void interrupted() {
		new ShooterHopperSafety();
	}
}

