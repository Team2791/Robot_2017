package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Simultanesouly runs the shooter and hopper. Sets shooter speed and hood for longShot. The hopper is set to meter its speed.
 */
public class RunLongShotFullHopper extends Command{
	public RunLongShotFullHopper() {
		super("RunLongShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}

	@Override
	protected void initialize() {
		Robot.shooter.prepFarHopperShot();
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(true); // down position is false
		Robot.shooter.prepFarHopperShot(); // bringing shooter up to speed

		// if we need more balls or the shooter is ready
		Robot.hopper.runHopper();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.stopMotors();
		Robot.hopper.stopHopper();
	}

	@Override
	protected void interrupted() {
		Robot.shooter.stopMotors();
		Robot.hopper.stopHopper();
	}
}

