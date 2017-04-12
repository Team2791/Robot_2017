package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.Robot;

public class StationaryVisionTurn extends DrivetrainPIDTurn {

	public StationaryVisionTurn(double maxOutput) {
		super(maxOutput);
		// TODO Auto-generated constructor stub
	}
	
	protected double getProcessVaraible() {
		return Robot.visionTable.getRealtimeBoilerAngleError();
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	stationaryAnglePID.setSetPoint(0);
    }

}
