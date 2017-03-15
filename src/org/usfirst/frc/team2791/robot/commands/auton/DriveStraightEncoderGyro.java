package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightEncoderGyro extends Command {
	
	private final double MIN_POWER_TO_MOVE = 0.15;
	
	protected static BasicPID movingAnglePID;
	protected static BasicPID distancePID;
	double distanceToDrive, maxOutput;
	
	public DriveStraightEncoderGyro(double distanceToDrive, double maxOutput) {
    	requires(Robot.drivetrain);
    	this.distanceToDrive = distanceToDrive;
    	this.maxOutput = maxOutput;
    	
    	movingAnglePID = new BasicPID(CONSTANTS.DRIVE_ANGLE_P, CONSTANTS.DRIVE_ANGLE_I, CONSTANTS.DRIVE_ANGLE_D);
		distancePID = new BasicPID(CONSTANTS.DRIVE_DISTANCE_P, CONSTANTS.DRIVE_DISTANCE_I, CONSTANTS.DRIVE_DISTANCE_D);
		
		movingAnglePID.setMaxOutput(0.5);
		movingAnglePID.setMinOutput(-0.5);
		
		distancePID.setMaxOutput(maxOutput);
		distancePID.setMinOutput(-maxOutput);

		distancePID.setIZone(0.15);
		movingAnglePID.setIZone(4);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	 distancePID.setSetPoint(Robot.drivetrain.getAverageDist() + distanceToDrive);
         movingAnglePID.setSetPoint(Robot.drivetrain.getGyroAngle());    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// uncomment this line if we are debugging
		updatePIDGains();

        double drivePIDOutput = -distancePID.updateAndGetOutput(Robot.drivetrain.getAverageDist());
        double anglePIDOutput = movingAnglePID.updateAndGetOutput(Robot.drivetrain.getGyroAngle());

        //setLeftRightVoltage(drivePIDOutput + anglePIDOutput, drivePIDOutput - anglePIDOutput);
        setLeftRightMotorOutputsPIDDriving(drivePIDOutput + anglePIDOutput, drivePIDOutput - anglePIDOutput);
        System.out.println("distError: " + distancePID.getError() + " output: " + drivePIDOutput);
        System.out.println("angleError: " + movingAnglePID.getError() + " output: " + anglePIDOutput);

    	debug();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(distancePID.getError()) < 0.05 &&
    		   Math.abs(movingAnglePID.getError()) < 1.5 &&
    		   Math.abs(Robot.drivetrain.getLeftVelocity()) < 0.01 &&
    		   Math.abs(Robot.drivetrain.getRightVelocity()) < 0.01;
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
