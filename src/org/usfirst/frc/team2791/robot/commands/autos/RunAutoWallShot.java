package org.usfirst.frc.team2791.robot.commands.autos;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RunAutoWallShot extends Command{
	
	public Timer timer = new Timer();
	private double runTime;
	
	public RunAutoWallShot(double runTime_) {
		super("RunWallShotFullHopper");
		requires(Robot.shooter);
		requires(Robot.hopper);
		
		runTime = runTime_;
	}

	@Override
	protected void initialize() {
		timer.start();
		Robot.shooter.prepWallShot();
	}

	@Override
	protected void execute() {
		
		Robot.shooter.setShooterSolenoidState(false); //down position
		
		Robot.shooter.prepWallShot(); //bringing shooter up to speed
		Robot.hopper.runHopper();
	}

	@Override
	protected boolean isFinished() {
		return timer.hasPeriodPassed(runTime);
	}

	@Override
	protected void end() {

		Robot.hopper.stopHopper();
		Robot.shooter.stopMotors();
	}

	@Override
	protected void interrupted() {
		System.out.print("shooter interrupted");
		end(); // we don't think it's called automatically
	}
}

