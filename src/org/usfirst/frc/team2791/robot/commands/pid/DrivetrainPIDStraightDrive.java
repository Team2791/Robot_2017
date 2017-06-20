package org.usfirst.frc.team2791.robot.commands.pid;

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
public abstract class DrivetrainPIDStraightDrive extends Command {

	private final double MIN_POWER_TO_MOVE = 0.15;

	protected static BasicPID movingAnglePID;
	protected static BasicPID distancePID;
	double distanceToDrive, maxOutput;

	protected Timer timer =new Timer();
	protected double timeForRelease;
	protected double drivingErrorThreshold = 1.5;
	
	public DrivetrainPIDStraightDrive(double distanceToDrive, double maxOutput, double timeOut, double maxThreshold){
		this(distanceToDrive, maxOutput, timeOut);
		this.drivingErrorThreshold = maxThreshold;		
	}

	public DrivetrainPIDStraightDrive(double distanceToDrive, double maxOutput, double timeOut) {
		super("Encoder Driving Base Class");
		timeForRelease = timeOut;
		
		requires(Robot.drivetrain);
		this.distanceToDrive = distanceToDrive;
		this.maxOutput = maxOutput;

		movingAnglePID = new BasicPID(CONSTANTS.DRIVE_ANGLE_P, CONSTANTS.DRIVE_ANGLE_I, CONSTANTS.DRIVE_ANGLE_D);
		distancePID = new BasicPID(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);

		distancePID.setInvertOutput(true);
		movingAnglePID.setInvertOutput(true);

		movingAnglePID.setMaxOutput(0.5);
		movingAnglePID.setMinOutput(-0.5);

		distancePID.setMaxOutput(maxOutput);
		distancePID.setMinOutput(-maxOutput);

		distancePID.setIZone(0.15);
		movingAnglePID.setIZone(4);
		
		setInterruptible(true);
	}

	protected abstract double getProcessVariable();

	protected void execute() {
		// uncomment this line if we are debugging
		updatePIDGains();

		double drivePIDOutput = distancePID.updateAndGetOutput(getProcessVariable());
		double anglePIDOutput = movingAnglePID.updateAndGetOutput(Robot.drivetrain.getGyroAngle());

		setLeftRightMotorOutputsPIDDriving(drivePIDOutput + anglePIDOutput, drivePIDOutput - anglePIDOutput);

		debug();
	}

	protected boolean isFinished() {
//		printStopBooleans();

		boolean isPIDDone = (Math.abs(distancePID.getError()) < drivingErrorThreshold &&
				Math.abs(movingAnglePID.getError()) < 1.5);
	
		return (isPIDDone || timer.hasPeriodPassed(timeForRelease));
	}

	protected void end() {
		System.out.println("PID Driving Finished");
	}

	protected void interrupted() {}

	public void setLeftRightMotorOutputsPIDDriving(double left, double right){
		if(left < 0) {
			left -= MIN_POWER_TO_MOVE;
		} else {
			left += MIN_POWER_TO_MOVE;
		}
		if(right < 0) {
			right -= MIN_POWER_TO_MOVE;
		} else {
			right += MIN_POWER_TO_MOVE;
		}
		
		Robot.drivetrain.setLeftRightMotorOutputs(left, right);
	}
	
	public void updatePIDGains() {
		// get new values from smart dash
		CONSTANTS.STATIONARY_ANGLE_P = SmartDashboard.getNumber("Stat Angle P");
		CONSTANTS.STATIONARY_ANGLE_I = SmartDashboard.getNumber("Stat Angle I");
		CONSTANTS.STATIONARY_ANGLE_D = SmartDashboard.getNumber("Stat Angle D");

		CONSTANTS.DRIVE_ANGLE_P = SmartDashboard.getNumber("Moving Angle P");
		CONSTANTS.DRIVE_ANGLE_I = SmartDashboard.getNumber("Moving Angle I");
		CONSTANTS.DRIVE_ANGLE_D = SmartDashboard.getNumber("Moving Angle D");

		CONSTANTS.DRIVE_DISTANCE_P = SmartDashboard.getNumber("Distance P");
		CONSTANTS.DRIVE_DISTANCE_I = SmartDashboard.getNumber("Distance I");
		CONSTANTS.DRIVE_DISTANCE_D = SmartDashboard.getNumber("Distance D");

		movingAnglePID.changeGains(CONSTANTS.DRIVE_ANGLE_P, CONSTANTS.DRIVE_ANGLE_I, CONSTANTS.DRIVE_ANGLE_D);
		distancePID.changeGains(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);
	}

	public void debug() {
		SmartDashboard.putNumber("Moving Angle PID Error", movingAnglePID.getError());
		SmartDashboard.putNumber("Moving Angle PID Output", movingAnglePID.getOutput());
		SmartDashboard.putNumber("Distance PID output", distancePID.getOutput());
		SmartDashboard.putNumber("Distance PID error", distancePID.getError());
	}
	
	protected void printStopBooleans(){
		System.out.println("Stright drive stop conditions");
		System.out.println(Math.abs(distancePID.getError()) < drivingErrorThreshold);
		System.out.println(Math.abs(movingAnglePID.getError()) < 3);
		System.out.println(Math.abs(Robot.drivetrain.getLeftVelocity()) < 0.05);
		System.out.println(Math.abs(Robot.drivetrain.getRightVelocity()) < 0.05);
	}
}
