package org.usfirst.frc.team2791.robot.commands.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;

/**
 *Uses BasicPID util class to create a PID for auto-turning. PID makes sure that the angle is correct
 *@see BasicPID
 */
public class StationaryGyroTurn extends DrivetrainPIDTurn {

	private double angleToTurn;
	
	/**
	 * @param angleToTurn the angle in degrees you would like to turn, ***negative if counterclockwise*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param maxThreshold the maximum error that PID can accept before finishing the command
	 * @param timeOut the time this command takes to time out
	 */
    public StationaryGyroTurn(double angleToTurn, double maxOutput, double maxThreshold, double timeOut) {
    	super(maxOutput, maxThreshold);
    	this.angleToTurn = angleToTurn;
    	this.setTimeout(timeOut);
    }
	
	/**
	 * @param angleToTurn the angle in degrees you would like to turn, ***negative if counterclockwise*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param maxThreshold the maximum error that PID can accept before finishing the command
	 */
    public StationaryGyroTurn(double angleToTurn, double maxOutput, double maxThreshold) {
    	//this(angle
    	this(angleToTurn, maxOutput, maxThreshold, 10.0);
    }
    
    /**
     * The Error Threshold is defaulted to .25
	 * @param angleToTurn the angle in degrees you would like to turn, ***negative if counterclockwise*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 */
    public StationaryGyroTurn(double angleToTurn, double maxOutput) {
    	
    	this(angleToTurn, maxOutput, 0.25, 10.0);
    }
    
    /**
     * This function returns the realtime varaible that is used as in input to the PID.
     */
    protected double getProcessVaraible(){
    	return Robot.drivetrain.getGyroAngle();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stationaryAnglePID.setSetPoint(getProcessVaraible() + angleToTurn);
    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(stationaryAnglePID.getError()) < getThreshold() &&
        	   Math.abs(Robot.drivetrain.getGyroRate()) < 2; //1
    }
}
