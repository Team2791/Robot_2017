package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Simultanesouly runs the {@link ShakerShooter} and {@link ShakerHopper}. Sets shooter speed and hood for wallShot. The hopper is set to meter its speed.
 */
public class RunWallShot extends Command{
	boolean shooterSpunUp = false;
	Timer hopperBackwardsTimer = new Timer();
	
	public RunWallShot() {
		super("RunWallShotFullHopper");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}

	@Override
	protected void initialize() {
		hopperBackwardsTimer.reset();
		hopperBackwardsTimer.start();
		Robot.hopper.runHopperBackwards();
		Robot.shooter.prepWallShot();
		shooterSpunUp = false;
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(true); //down position
		Robot.shooter.prepWallShot(); //bringing shooter up to speed
		
		if(Robot.shooter.atSpeed()){
			shooterSpunUp = true;
		}
		// first run the hopper backwards to clear out ball jams
		// then wait for the shooter to spin up to start running
		if(hopperBackwardsTimer.get() > 0.3) {
			if(shooterSpunUp) {
				Robot.hopper.setHopperSpeed(1.0);
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
		Robot.hopper.stopHopper();
		Robot.shooter.stopMotors();
	}

	@Override
	protected void interrupted() {
		end(); 
	}
}

