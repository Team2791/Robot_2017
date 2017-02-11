
 package org.usfirst.frc.team2791.robot.commands;


import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2791.chezylib.trajectory.Path;
import org.usfirst.frc.team2791.chezylib.trajectory.TrajectoryDriveHelper;
import org.usfirst.frc.team2791.robot.Robot;

/*
 * DriveAction.java and DrivePathAction.java
 * Command to Follow a Given path
 * When you call this 
 */
public class FollowPath extends Command {
	protected TrajectoryDriveHelper trajHelper;
	
	double heading; //not sure where this is assigned a value
	Path path;
	
	/*
	 * @param path_ a path that is generated with the preferred AutoPaths.get() method, see AutoPath.java for more info
	 * @param angle ??????
	 */
	public FollowPath(Path path_, double angle) {// double angle may not be correct
		
		super("FollowPath");
		requires(Robot.drivetrain);
		trajHelper=new TrajectoryDriveHelper();
		path=path_;
		heading=angle;
		System.out.println("came to constructor FollowPath");
		initialize();
	}
	

	/*
	 * @param path_ a path that is generated outside of the class and sent
	 */
	public FollowPath(Path path_) {
		
		super("FollowPath");
		requires(Robot.drivetrain);
		trajHelper=new TrajectoryDriveHelper();
		path=path_;
		heading=0.0;//i still don't know
		System.out.println("came to constructor FollowPath");
		initialize();
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
		System.out.println("I am asking trajHelper for outputs");
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
