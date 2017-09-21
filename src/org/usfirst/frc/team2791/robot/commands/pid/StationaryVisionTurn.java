package org.usfirst.frc.team2791.robot.commands.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;
import org.usfirst.frc.team2791.robot.vision.VisionNetworkTable;


/**
 *Uses BasicPID util class to create a PID for auto-turning. PID makes sure that the angle is correct.
 *Uses Vision to determine, error-check, and update the setpoint. </br></br>
 *Superclass: {@link  DrivetrainPIDTurn}
 *
 *@see BasicPID
 *@see VisionNetworkTable
 */
public class StationaryVisionTurn extends DrivetrainPIDTurn {

	/**
	 * 
	 * @param maxOutput the maximum output desired for the motors (up to 1.0)
	 * @param errorThreshold the maximum error allowed for the Vision
	 */
	public StationaryVisionTurn(double maxOutput, double errorThreshold) {
		super(maxOutput, errorThreshold);
        requires(Robot.drivetrain);
		setInterruptible(true);
	}
	
	@Override
	protected double getProcessVaraible() {
		return Robot.visionTable.getRealtimeBoilerAngleError();
	}
	
	@Override
    protected void initialize() {
		setInterruptible(true);
		System.out.println("Starting vision turn");
    	stationaryAnglePID.setSetPoint(0);
    }
    
    @Override
    protected boolean isFinished() {
        return Math.abs(stationaryAnglePID.getError()) < getThreshold() &&
        	   Math.abs(Robot.drivetrain.getGyroRate()) < 1;
    }

    protected void interuppted(){
    	stationaryAnglePID.setSetPoint(getProcessVaraible());
    	setLeftRightMotorOutputsPIDTurning(0.0,0.0);
    	end();
    }
}
