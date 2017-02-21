package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class RunWallShot extends Command{
	public RunWallShot() {
		// Use requires() here to declare subsystem dependencies
		super("RunWallShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
//		System.out.println("Came to wall shooter constructor");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
//		System.out.println("initializing wall shot");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
//		System.out.println("Running a wall shot");
		Robot.shooter.setShooterSolenoidState(false);
		Robot.shooter.prepWallShot();
		
		// if we need more balls or the shooter is ready
		if(!Robot.hopper.isBallAtTop() || Robot.shooter.atSpeed()) {
//			System.out.println("Running hopper");
			Robot.hopper.runHopper();
		} else { 
//			System.out.println("Stopping hopper");
			Robot.hopper.stopHopper();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.shooter.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		new StopHopper();
		new TurnShooterOff();
	}
}

