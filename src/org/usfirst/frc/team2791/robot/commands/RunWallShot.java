package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Simultanesouly runs the shooter and hopper. Sets shooter speed and hood for wallShot. The hopper is set to meter its speed.
 */
public class RunWallShot extends Command{
	public RunWallShot() {
		super("RunWallShotFullHopper");
		requires(Robot.shooter);
		requires(Robot.hopper);
		System.out.print("shooter construct");
	}

	@Override
	protected void initialize() {
		System.out.print("shooter init");
		Robot.shooter.prepWallShot();
	}

	@Override
	protected void execute() {
		System.out.print("shooter execute");
		Robot.shooter.setShooterSolenoidState(false); //down position
		Robot.shooter.prepWallShot(); //bringing shooter up to speed

		// if we need more balls or the shooter is ready
		Robot.hopper.runHopper();
	}

	@Override
	protected boolean isFinished() {
		System.out.print("shooter isFinished");
		return false;
	}

	@Override
	protected void end() {
		System.out.print("shooter end");
		Robot.hopper.stopHopper();
		Robot.shooter.stopMotors();
	}

	@Override
	protected void interrupted() {
		System.out.print("shooter interrupted");
		end(); // we don't think it's called automatically
	}
}

