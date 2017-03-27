
package org.usfirst.frc.team2791.robot.commands.autos;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.trajectory.AutoPaths;
import org.usfirst.frc.team2791.trajectory.Path;
import org.usfirst.frc.team2791.trajectory.TrajectoryDriveHelper;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Follows a given path using Trajectory Lib
 * @author unbun
 */

public class FollowPath extends Command {
	protected TrajectoryDriveHelper trajHelper;

	double heading; //not sure where this is assigned a value
	protected Path path;
	private boolean reversed;

	/**
	 * 
	 * @param path_ String name of path, should match the file name
	 * @param color enum for Color of team
	 * @param direction enum for Direction, forward or reverse
	 */
	public FollowPath(String path_, Color color, Direction direction) {
		super("FollowPath");
		requires(Robot.drivetrain);

		path=AutoPaths.get(path_);

		switch(color){
		case BLUE: path.goRight();
		case RED: path.goLeft();
		}

		switch(direction){
		case FORWARD: reversed = true;
		case REVERSE: reversed = false;
		}
		
		trajHelper=new TrajectoryDriveHelper(reversed);

		System.out.println("Beginning to Follow"+ path.getName());
	}

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
		//		return false; //UNTESTED
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

	//Enums
	public enum Color{
		RED, BLUE
	}

	public enum Direction{
		FORWARD, REVERSE
	}

}
