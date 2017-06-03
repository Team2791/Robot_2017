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
public class DriveStraightEncoderGyro extends Command {

	private final double MIN_POWER_TO_MOVE = 0.15;

	protected static BasicPID movingAnglePID;
	protected static BasicPID distancePID;
	double distanceToDrive, maxOutput;

	private Timer timer =new Timer();
	private double timeForRelease;
	private double drivingErrorThreshold = 1.5;
	
	private double temp = 0.0;
	
	/**
	  * @param distanceToDrive the distance in feet that you would like to drive ***negative if reversing*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param timeOut the time in seconds before you would like to wait before the PID times out and the command ends
	 * @param maxThreshold the maximum error for driving forward before the PID accepts it and finishes
	 */
	public DriveStraightEncoderGyro(double distanceToDrive, double maxOutput, double timeOut, double maxThreshold){
		this(distanceToDrive, maxOutput, timeOut);
		this.drivingErrorThreshold = maxThreshold;		
	}
	
	/**
	 * Driving Error Threshold is defaulted to 1.5
	 * @param distanceToDrive the distance in feet that you would like to drive ***negative if reversing*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 * @param timeOut the time in seconds before you would like to wait before the PID times out and the command ends
	 */
	public DriveStraightEncoderGyro(double distanceToDrive, double maxOutput, double timeOut) {
//		super("DriveStraight w/ Encoders");
		timeForRelease = timeOut;
		setInterruptible(false);
		
//		requires(Robot.drivetrain);
		this.distanceToDrive = distanceToDrive;
		this.maxOutput = maxOutput;

		movingAnglePID = new BasicPID(CONSTANTS.DRIVE_ANGLE_P, CONSTANTS.DRIVE_ANGLE_I, CONSTANTS.DRIVE_ANGLE_D);
		distancePID = new BasicPID(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);

		movingAnglePID.setName("angle");
		distancePID.setName("distance");
		
		distancePID.setInvertOutput(true);
		movingAnglePID.setInvertOutput(true);

		movingAnglePID.setMaxOutput(0.5);
		movingAnglePID.setMinOutput(-0.5);

		distancePID.setMaxOutput(maxOutput);
		distancePID.setMinOutput(-maxOutput);

		distancePID.setIZone(0.15);
		movingAnglePID.setIZone(4);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();

		temp = Robot.drivetrain.getAverageDist();
		distancePID.setSetPoint(Robot.drivetrain.getAverageDist() + distanceToDrive);
		movingAnglePID.setSetPoint(Robot.drivetrain.getGyroAngle());    
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		distancePID.debug("start of execute()");
//		distancePID.setSetPoint(temp + distanceToDrive);
		
		// uncomment this line if we are debugging
		updatePIDGains();

		double drivePIDOutput = distancePID.updateAndGetOutput(Robot.drivetrain.getAverageDist());
		double anglePIDOutput = movingAnglePID.updateAndGetOutput(Robot.drivetrain.getGyroAngle());

		//setLeftRightVoltage(drivePIDOutput + anglePIDOutput, drivePIDOutput - anglePIDOutput);
		setLeftRightMotorOutputsPIDDriving(drivePIDOutput + anglePIDOutput, drivePIDOutput - anglePIDOutput);
		System.out.println("distError: " + distancePID.getError() + " output: " + drivePIDOutput);
		System.out.println("angleError: " + movingAnglePID.getError() + " output: " + anglePIDOutput);

		distancePID.debug("end of execute()");
		debug();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		System.err.println("\t\t\tdistToDrive = " + distanceToDrive);
		distancePID.debug("start of isFinished()");
		System.out.println("Stright drive stop conditions");
		System.out.println(Math.abs(distancePID.getError()) < drivingErrorThreshold);
		System.out.println(Math.abs(movingAnglePID.getError()) < 3);
		System.out.println(Math.abs(Robot.drivetrain.getLeftVelocity()) < 0.05);
		System.out.println(Math.abs(Robot.drivetrain.getRightVelocity()) < 0.05);
		
		boolean isPIDDone = (Math.abs(distancePID.getError()) < 0.05 &&
				Math.abs(movingAnglePID.getError()) < 3 &&
				Math.abs(Robot.drivetrain.getLeftVelocity()) < 0.05 &&
				Math.abs(Robot.drivetrain.getRightVelocity()) < 0.05);
		
		System.out.println(isPIDDone);
		
		if(isPIDDone) {
//			System.err.println("\tisPIDDone = true");
		}


		distancePID.debug("end of isFinished()");
		return (isPIDDone || timer.hasPeriodPassed(timeForRelease));
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

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
}
