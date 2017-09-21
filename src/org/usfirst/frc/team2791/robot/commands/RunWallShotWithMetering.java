package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Simultanesouly runs the {@link ShakerShooter} and {@link ShakerHopper}. Sets shooter speed and hood for wallShot. 
 * The hopper is set to meter its speed so it feeds balls to the shooter faster when the shooter is closer to the desired rpm.
 * </br>This Command has no automatic ending (The ending is user requested)
 */
public class RunWallShotWithMetering extends Command{
	public RunWallShotWithMetering() {
		super("RunWallShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}

	@Override
	protected void initialize() {
		Robot.shooter.prepWallShot();
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(true); //down position
		Robot.shooter.prepWallShot(); //bringing shooter up to speed
		
		// if we need more balls or the shooter is ready
		if(Robot.shooter.atSpeed()) {
			Robot.hopper.runHopper();
		} else {
			Robot.hopper.slowHopper();
		}
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
		end(); 
	}
}

