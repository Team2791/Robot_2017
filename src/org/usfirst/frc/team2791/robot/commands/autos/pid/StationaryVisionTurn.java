package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.Robot;

public class StationaryVisionTurn extends DrivetrainPIDTurn {

	public StationaryVisionTurn(double maxOutput, double errorThreshold) {
		super(maxOutput, errorThreshold);
	}
	
	@Override
	protected double getProcessVaraible() {
		return Robot.visionTable.getRealtimeBoilerAngleError();
	}
	
    // Called just before this Command runs the first time
	@Override
    protected void initialize() {
    	stationaryAnglePID.setSetPoint(0);
    }
    
    @Override
    protected boolean isFinished() {
        return Math.abs(stationaryAnglePID.getError()) < getThreshold() &&
        	   Math.abs(Robot.drivetrain.getGyroRate()) < 1;
    }

}
