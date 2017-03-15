package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RunWallShot extends Command{
	
	private Timer timer = new Timer();
	private double delayTime = -1;
	
	public RunWallShot() {
		super("RunWallShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
		delayTime = -1;
		System.out.print("shooter construct");
	}
	
	/**For autonomous**/
	public RunWallShot(double time_) {
		super("RunWallShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
		delayTime = time_;
		System.out.print("shooter construct");
	}


	@Override
	protected void initialize() {
		System.out.print("shooter init");
		Robot.shooter.prepWallShot();
    	timer.start();
	}

	@Override
	protected void execute() {
		System.out.print("shooter execute");
		Robot.shooter.setShooterSolenoidState(false); //down position
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
		System.out.print("shooter isFinished");
		if(delayTime ==-1)
			return false;
		else
			return timer.hasPeriodPassed(delayTime);
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
		end(); //
	}
}

