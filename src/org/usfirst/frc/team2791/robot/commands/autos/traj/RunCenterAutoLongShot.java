package org.usfirst.frc.team2791.robot.commands.autos.traj;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RunCenterAutoLongShot extends Command{
	
	Timer timer = new Timer();
	private boolean shooterSpunUp = false;
	private double runTime;
	
	public RunCenterAutoLongShot(double runTime_) {
		
		super("RunLongShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
		
		runTime = runTime_;

	}

	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
		Robot.shooter.prepAutoCenterShot();
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(true); // down position is false
		Robot.shooter.prepAutoCenterShot(); // bringing shooter up to speed

		if(Robot.shooter.atSpeed()){
			shooterSpunUp = true;
		}
		
		if(timer.get() > 0.7) {
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
		return timer.hasPeriodPassed(runTime);
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

