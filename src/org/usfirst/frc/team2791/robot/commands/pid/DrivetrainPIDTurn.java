package org.usfirst.frc.team2791.robot.commands.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *Uses BasicPID util class to create a PID for auto-turning. PID makes sure that the angle is correct.
 *Base class for PID turning (sub classes allow for differing systems for determing the setpoint [vision, encoders, etc.])
 *@see BasicPID
 */
public abstract class DrivetrainPIDTurn extends Command {
	
	private final double MIN_POWER_TO_TURN = 0.22;
	protected double errorThreshold = 1;
	protected static BasicPID stationaryAnglePID;
	
	/**
	 * @param angleToTurn the angle in degrees you would like to turn, ***negative if counterclockwise*** *
	 * @param maxOutput the maximum output you would like the motors to receive (up to 1.0)
	 */
    public DrivetrainPIDTurn(double maxOutput, double errorThreshold) {

    	super("Turning Base Class");

        requires(Robot.drivetrain);
        
        this.errorThreshold = errorThreshold;
        
		stationaryAnglePID = new BasicPID(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I, CONSTANTS.STATIONARY_ANGLE_D);
		stationaryAnglePID.setIZone(10);
		stationaryAnglePID.setMaxOutput(maxOutput - MIN_POWER_TO_TURN);
		stationaryAnglePID.setMinOutput(-maxOutput + MIN_POWER_TO_TURN);
		
		stationaryAnglePID.setInvertOutput(true);
		
		setInterruptible(true);
    }
    
    /**
     * This function returns the realtime varaible that is used as in input to the PID.
     */
    protected abstract double getProcessVaraible();
    
    protected void execute() {
    	// Uncomment for debugging
    	updatePIDGains();
    	
    	double PIDOutput = stationaryAnglePID.updateAndGetOutput(getProcessVaraible());
    	setLeftRightMotorOutputsPIDTurning(PIDOutput, -PIDOutput);
    	
    	debug();
    }

    protected boolean isFinished() {
        return Math.abs(stationaryAnglePID.getError()) < errorThreshold;
    }

    protected void end() {
    	setLeftRightMotorOutputsPIDTurning(0.0, 0.0);
    	System.out.println("Ending drive train PID turn");
    }

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
		SmartDashboard.putNumber("Stationary Angle PID Output", left);
		Robot.drivetrain.setLeftRightMotorOutputs(left, right);
	}
    public double getThreshold(){
    	return this.errorThreshold;
    }
    public void updatePIDGains() {
    	// get new values from smart dash
    	CONSTANTS.STATIONARY_ANGLE_P = SmartDashboard.getNumber("Stat Angle P", CONSTANTS.STATIONARY_ANGLE_P );
		CONSTANTS.STATIONARY_ANGLE_I = SmartDashboard.getNumber("Stat Angle I", CONSTANTS.STATIONARY_ANGLE_I );
		CONSTANTS.STATIONARY_ANGLE_D = SmartDashboard.getNumber("Stat Angle D", CONSTANTS.STATIONARY_ANGLE_D );
		
		stationaryAnglePID.changeGains(CONSTANTS.STATIONARY_ANGLE_P, CONSTANTS.STATIONARY_ANGLE_I,
				CONSTANTS.STATIONARY_ANGLE_D);
	}
    
    public void debug() {
        SmartDashboard.putNumber("Stationary Angle PID Error", stationaryAnglePID.getError());
        SmartDashboard.putNumber("Stationary Angle PID Output", stationaryAnglePID.getOutput());
    }
}
