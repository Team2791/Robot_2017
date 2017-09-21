package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Shoots w/ a wheel speed based on the distance away from the Boiler. Uses {@link VisionNetworkTable} and {@link ShooterLookupTable}.
 * Simultanesouly runs the {@link ShakerShooter} and {@link ShakerHopper}. Sets shooter speed and hood for visionShot. 
 * The hopper is set to only fed balls to the shooter when the shooter is at the desired rpm.
 * The hopper breifly runs backwards at the beginning to ensure no balls are touching the shooter when it spins up.
 * </br>This Command has no automatic ending (The ending is user requested)
 */
public class RunVisionShot extends Command{
	Timer timer = new Timer();
	private boolean shooterSpunUp = false;
	
	public RunVisionShot() {
		super("RunVisionShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}

	/*
	 *TODO: It might be best to find one rpm at the beginning of the command 
	 *and use it in a shooter.customShot(). Because if the image filtering is off, the 
	 *contours might flucuate and mess with the rpm 
	 */
	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
		
		Robot.shooter.prepVisionShot();
		shooterSpunUp = false;
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(false); // down position is false
		Robot.shooter.prepVisionShot();

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

