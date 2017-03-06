
package org.usfirst.frc.team2791.robot.commands;


import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.trajectory.AutoPaths;
import org.usfirst.frc.team2791.trajectory.Path;
import org.usfirst.frc.team2791.trajectory.TrajectoryDriveHelper;

/**
 * Follows a given path
 * @author unbun
 */

public class FollowPath extends Command {
	protected TrajectoryDriveHelper trajHelper;

	double heading; //not sure where this is assigned a value
	protected Path path;

	public FollowPath(Path path_, double angle) {// double angle may not be correct
		super("FollowPath");
		requires(Robot.drivetrain);
		trajHelper=new TrajectoryDriveHelper();
		path=path_;
		heading=angle;
		System.out.println("Beginning to Follow"+ path.getName());
	}

	public FollowPath(String path_) {// double angle may not be correct
		super("FollowPath");
		requires(Robot.drivetrain);
		path = AutoPaths.get(path_);
		trajHelper = new TrajectoryDriveHelper();
		heading = 0;
		System.out.println("Beginning to Follow"+ path.getName());
	}
	
	public FollowPath(Path path_) {// double angle may not be correct
		super("FollowPath");
		requires(Robot.drivetrain);
		path = path_;
		trajHelper = new TrajectoryDriveHelper();
		heading = 0;
		System.out.println("Beginning to Follow"+ path.getName());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//System.out.println("Init Drive " + Timer.getFPGATimestamp());
		Robot.drivetrain.resetEncoders();
		trajHelper.loadProfile(path.getLeftWheelTrajectory(), path.getRightWheelTrajectory(), 1.0, heading);
		trajHelper.enable();
		System.out.println("I have started trajHelper");

	}


	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		trajHelper.update();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return !trajHelper.enabled();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		trajHelper.disable();
		System.out.println("I have stopped trajHelper");

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

}
