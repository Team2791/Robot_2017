package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Uses BasicPID util class to create a PID for auto-driving. PID makes sure that the distance is correct and the robot drives straight
 *@see BasicPID
 */
public class DriveStraightVision extends Command{
	
	private volatile double distanceToTravel;
	private final double goodDistanceForShot = 114; //83 real inches
	private final double errorThreshold = 3.0;
	/**
	  * @param distanceToDrive the distance in feet that you would like to drive ***negative if reversing*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param timeOut the time in seconds before you would like to wait before the PID times out and the command ends
	 * @param maxThreshold the maximum error for driving forward before the PID accepts it and finishes
	 */
	public DriveStraightVision(){
		super("DriveStraightVision");
		requires(Robot.drivetrain);
		setInterruptible(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {  
		distanceToTravel = Robot.visionTable.getRealtimeDistanceToBoiler() - goodDistanceForShot;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(distanceToTravel > 0)
			Robot.drivetrain.setLeftRightMotorOutputs(0.30, 0.30);
		else if(distanceToTravel < 0)
			Robot.drivetrain.setLeftRightMotorOutputs(-0.30, -0.30);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(Robot.visionTable.getRealtimeDistanceToBoiler() - goodDistanceForShot) < errorThreshold;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.setLeftRightMotorOutputs(0,0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
