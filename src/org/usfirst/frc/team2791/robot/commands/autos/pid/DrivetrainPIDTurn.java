package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *Uses BasicPID util class to create a PID for auto-turning. PID makes sure that the angle is correct
 *@see BasicPID
 */
public abstract class DrivetrainPIDTurn extends Command {
	
	private final double MIN_POWER_TO_TURN = 0.0;
	protected double errorThreshold = 0.25;
	protected static BasicPID stationaryAnglePID;
	
	/**
	 * @param angleToTurn the angle in degrees you would like to turn, ***negative if counterclockwise*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 */
    public DrivetrainPIDTurn(double maxOutput, double errorThreshold) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        
        this.errorThreshold = errorThreshold;
        
		stationaryAnglePID = new BasicPID(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I, CONSTANTS.STATIONARY_ANGLE_D);
		stationaryAnglePID.setIZone(6);
		stationaryAnglePID.setMaxOutput(maxOutput);
		stationaryAnglePID.setMinOutput(-maxOutput);
		
		stationaryAnglePID.setInvertOutput(true);
    }
    
    /**
     * This function returns the realtime varaible that is used as in input to the PID.
     */
    protected abstract double getProcessVaraible();

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Uncomment for debugging
    	updatePIDGains();
    	
    	double PIDOutput = stationaryAnglePID.updateAndGetOutput(getProcessVaraible());
    	setLeftRightMotorOutputsPIDTurning(PIDOutput, -PIDOutput);
    	
    	debug();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(stationaryAnglePID.getError()) < errorThreshold;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Ending drive train PID trun");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("drive train PID trun interrupted");
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
    public double getThreshold(){
    	return this.errorThreshold;
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
