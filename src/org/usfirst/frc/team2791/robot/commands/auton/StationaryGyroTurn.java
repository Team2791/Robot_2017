package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class StationaryGyroTurn extends Command {
	
	private  final double MIN_POWER_TO_TURN = 0.0;
	
	protected static BasicPID stationaryAnglePID;
	private double angleToTurn;
	
	/**
	 * 
	 * @param angleToTurn negative if counterclockwise
	 * @param maxOutput
	 */
    public StationaryGyroTurn(double angleToTurn, double maxOutput) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    	this.angleToTurn = angleToTurn;

		stationaryAnglePID = new BasicPID(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I, CONSTANTS.STATIONARY_ANGLE_D);
		stationaryAnglePID.setIZone(6);
		stationaryAnglePID.setMaxOutput(maxOutput);
		stationaryAnglePID.setMinOutput(-maxOutput);
		
		stationaryAnglePID.setInvertOutput(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stationaryAnglePID.setSetPoint(Robot.drivetrain.getGyroAngle() + angleToTurn);
    	stationaryAnglePID.updateAndGetOutput(Robot.drivetrain.getGyroAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Uncomment for debugging
    	updatePIDGains();
    	
    	double PIDOutput = stationaryAnglePID.updateAndGetOutput(Robot.drivetrain.getGyroAngle());
    	setLeftRightMotorOutputsPIDTurning(PIDOutput, -PIDOutput);
    	
    	debug();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(stationaryAnglePID.getError()) < 1 &&
        	   Math.abs(Robot.drivetrain.getGyroRate()) < 0.1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Ending gyro trun");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("gyro trun interrupted");
    }
    
    public void setLeftRightMotorOutputsPIDTurning(double left, double right){
		if(left < 0) {
			left -= MIN_POWER_TO_TURN;
		} else {
			left += MIN_POWER_TO_TURN;
		}
		if(right < 0) {
			right -= MIN_POWER_TO_TURN;
		} else {
			right += MIN_POWER_TO_TURN;
		}
		Robot.drivetrain.setLeftRightMotorOutputs(left, right);
	}
    
    public void updatePIDGains() {
    	// get new values from smart dash
    	CONSTANTS.STATIONARY_ANGLE_P = SmartDashboard.getNumber("Stat Angle P");
		CONSTANTS.STATIONARY_ANGLE_I = SmartDashboard.getNumber("Stat Angle I");
		CONSTANTS.STATIONARY_ANGLE_D = SmartDashboard.getNumber("Stat Angle D");
		
		stationaryAnglePID.changeGains(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I,
				CONSTANTS.STATIONARY_ANGLE_D);
	}
    
    public void debug() {
        SmartDashboard.putNumber("Stationary Angle PID Error", stationaryAnglePID.getError());
        SmartDashboard.putNumber("Stationary Angle PID Output", stationaryAnglePID.getOutput());
    }
}
