package org.usfirst.frc.team2791.robot.commands.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;

/**
 *Uses BasicPID util class to create a PID for auto-driving. PID makes sure that the distance is correct and the robot drives straight
 *@see BasicPID
 */
public class DriveStraightVision extends DrivetrainPIDStraightDrive {
	
	public DriveStraightVision(double distanceFromBoiler, double maxOutput) {
		super(distanceFromBoiler, maxOutput, 10.0, 1.5);
		distancePID.setInvertOutput(false);
	}

	protected void initialize() {
		timer.start();

		distancePID.setSetPoint(0);
		movingAnglePID.setSetPoint(Robot.drivetrain.getGyroAngle());    
	}

	protected boolean isFinished() {
		
		boolean isPIDDone = (Math.abs(distancePID.getError()) < drivingErrorThreshold &&
				Math.abs(movingAnglePID.getError()) < 1.5);
		
		return (isPIDDone || timer.hasPeriodPassed(timeForRelease));
	}

	@Override
	protected double getProcessVariable() {
		return (Robot.visionTable.getRealtimeDistanceToBoiler() / 12) - distanceToDrive; //the distance error
	}

}
