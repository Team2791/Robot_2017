package org.usfirst.frc.team2791.robot.commands.autos.traj;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RunAutoLongShot extends Command{
	
	public Timer timer = new Timer();
	private double runTime;
	
	public RunAutoLongShot(double runTime_) {
		
		super("RunLongShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
		
		runTime = runTime_;

	}

	@Override
	protected void initialize() {
		timer.start();
		Robot.shooter.prepFarHopperShot();
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(true); // down position is false
		Robot.shooter.prepFarHopperShot(); // bringing shooter up to speed

		Robot.hopper.runHopper();
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

