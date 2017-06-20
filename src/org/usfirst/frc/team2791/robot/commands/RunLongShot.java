package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Simultanesouly runs the {@link ShakerShooter} and {@link ShakerHopper}. Sets shooter speed and hood for closeShot. The hopper is set to meter its speed.
 */
public class RunLongShot extends Command{
	Timer timer = new Timer();
	private boolean shooterSpunUp = false;
	public RunLongShot() {
		super("RunLongShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}

	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
		Robot.shooter.prepLongShot();
		shooterSpunUp = false;
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(false); // down position is false
		Robot.shooter.prepLongShot(); // bringing shooter up to speed

		// if we need more balls or the shooter is ready
		if(Robot.shooter.atSpeed()){
			shooterSpunUp = true;
		}
		
		if(timer.get() > 0.3) {
			if(shooterSpunUp) {
				Robot.hopper.runHopper();
			} else {
				Robot.hopper.stopHopper();
			}
		} else {
			Robot.hopper.runHopperBackwards();
		}
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

