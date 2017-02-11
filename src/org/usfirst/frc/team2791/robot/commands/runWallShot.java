package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2791.robot.commands.runHopper;

public class runWallShot extends Command{
	public runWallShot() {
		// Use requires() here to declare subsystem dependencies
		super("runWallShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
		System.out.println("Came to wall shooter constructor");
		initialize();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("initializing wall shot");
		execute();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Running a wall shot");
		Robot.shooter.setShooterSolenoidState(false);
		Robot.shooter.prepWallShot();
		while(Robot.hopper.moreBalls()){
			System.out.println("I'm trying to execute hopper run");
			Robot.hopper.runHopper();
		}//gets balls right up to before shooter
		
		if(Robot.shooter.shooterAtSpeed()){//pushes a ball into the shooter
			while(Robot.hopper.getDistanceState()){//runs the hopper so the ball can get in but stops before next ball
					Robot.hopper.runHopper();
					System.out.println("ball pushed in");
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		System.out.println("I just shot....Woohoo!");
		if(Robot.oi.operator.getButtonX() == false)
			return true;
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.hopper.stopHopper();
		Robot.shooter.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
