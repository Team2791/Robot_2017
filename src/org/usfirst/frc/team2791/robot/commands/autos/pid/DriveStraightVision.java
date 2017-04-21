package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2791.robot.util.DelayedBoolean;

/**
 *Uses BasicPID util class to create a PID for auto-driving. PID makes sure that the distance is correct and the robot drives straight
 *@see BasicPID
 */
public class DriveStraightVision extends Command{
	
	private double targetDistance, MAX_DRIVE_OUTPUT = 0.7;
	private final double goodDistanceForShot = 114; //83 real inches
	private final double errorThreshold = 0.2;
	private final double MIN_POWER_TO_MOVE = 0.15;
	
	private DelayedBoolean goodToExit;
	
	private BasicPID drivePID;
	
	/**
	  * @param distanceToDrive the distance in feet that you would like to drive ***negative if reversing*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param timeOut the time in seconds before you would like to wait before the PID times out and the command ends
	 * @param maxThreshold the maximum error for driving forward before the PID accepts it and finishes
	 */
	public DriveStraightVision(){
		super("DriveStraightVision");
		System.out.println("Starting vision drive");
		requires(Robot.drivetrain);
		setInterruptible(true);
		drivePID = new BasicPID(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);
		
		drivePID.setMaxOutput(MAX_DRIVE_OUTPUT);
		drivePID.setMinOutput(-MAX_DRIVE_OUTPUT);
		drivePID.setInvertOutput(true);
		
		goodToExit = new DelayedBoolean(0.25);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		double vision_error = Robot.visionTable.getRealtimeDistanceToBoiler() - goodDistanceForShot;
		vision_error /= 12.0;
		System.out.println("Vision error = "+vision_error);
		targetDistance = vision_error + Robot.drivetrain.getAverageDist();
		System.out.println("target distance = "+targetDistance);
		
		drivePID.setSetPoint(targetDistance);
		drivePID.updateAndGetOutput(Robot.drivetrain.getAverageDist()*12.0);
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
//		double error = Robot.visionTable.getRealtimeDistanceToBoiler() - goodDistanceForShot;
//		double output = SmartDashboard.getNumber("shooting drive P",0)*error+MIN_POWER_TO_MOVE * Math.signum(error);
		
		double output = drivePID.updateAndGetOutput(Robot.drivetrain.getAverageDist());
		System.out.println("VT: output: "+output+" err: "+drivePID.getError());
		Robot.drivetrain.setLeftRightMotorOutputs(output, output);
		
//		if(distanceToTravel > 0)
//			Robot.drivetrain.setLeftRightMotorOutputs(0.30, 0.30);
//		else if(distanceToTravel < 0)
//			Robot.drivetrain.setLeftRightMotorOutputs(-0.30, -0.30);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		return Math.abs(Robot.visionTable.getRealtimeDistanceToBoiler() - goodDistanceForShot) < errorThreshold;
		return goodToExit.update(Math.abs(drivePID.getError()) < errorThreshold);
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Finishging vision drive");
		Robot.drivetrain.setLeftRightMotorOutputs(0,0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
