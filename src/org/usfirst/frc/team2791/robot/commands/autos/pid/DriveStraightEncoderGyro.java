package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *Uses BasicPID util class to create a PID for auto-driving. PID makes sure that the distance is correct and the robot drives straight
 *@see BasicPID
 */
public class DriveStraightEncoderGyro extends DrivetrainPIDStraightDrive {
	
	/**
	  * @param distanceToDrive the distance in feet that you would like to drive ***negative if reversing*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param timeOut the time in seconds before you would like to wait before the PID times out and the command ends
	 * @param maxThreshold the maximum error for driving forward before the PID accepts it and finishes
	 */
	public DriveStraightEncoderGyro(double distanceToDrive, double maxOutput, double timeOut, double maxThreshold){
		super(distanceToDrive, maxOutput, timeOut, maxThreshold);
	}
	
	/**
	 * Driving Error Threshold is defaulted to 1.5
	 * @param distanceToDrive the distance in feet that you would like to drive ***negative if reversing*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param timeOut the time in seconds before you would like to wait before the PID times out and the command ends
	 */
	public DriveStraightEncoderGyro(double distanceToDrive, double maxOutput, double timeOut) {
		super(distanceToDrive, maxOutput, timeOut, 1.5);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		
		distancePID.setSetPoint(getProcessVariable() + distanceToDrive);
		movingAnglePID.setSetPoint(Robot.drivetrain.getGyroAngle());    
	}


	protected boolean isFinished() {
		
		boolean isPIDDone = (Math.abs(distancePID.getError()) < 0.05 &&
				Math.abs(movingAnglePID.getError()) < 1.5 &&
				Math.abs(Robot.drivetrain.getLeftVelocity()) < 0.05 &&
				Math.abs(Robot.drivetrain.getRightVelocity()) < 0.05);
		
		return (isPIDDone || timer.hasPeriodPassed(timeForRelease));
	}

	protected void end() {
		System.out.println("PID Driving Finished");
	}

	@Override
	protected double getProcessVariable() {
		return Robot.drivetrain.getAverageDist();
	}

}
