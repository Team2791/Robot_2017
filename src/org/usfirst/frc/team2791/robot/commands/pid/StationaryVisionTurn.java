package org.usfirst.frc.team2791.robot.commands.pid;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class StationaryVisionTurn extends DrivetrainPIDTurn {
	Timer timeoutTimer = null;
	double timeOut;
	
	public StationaryVisionTurn(double maxOutput, double errorThreshold, double timeOut) {
		this(maxOutput, errorThreshold);
		timeoutTimer = new Timer();
		this.timeOut = timeOut;
        requires(Robot.drivetrain);
		setInterruptible(true);
	}
	
	public StationaryVisionTurn(double maxOutput, double errorThreshold) {
		super(maxOutput, errorThreshold);
        requires(Robot.drivetrain);
		setInterruptible(true);
	}
	
	@Override
	protected double getProcessVaraible() {
		System.out.println("NOAH THE BoilerAngle is " + Robot.visionTable.getRealtimeBoilerAngleError());
		return Robot.visionTable.getRealtimeBoilerAngleError();
	}
	
    // Called just before this Command runs the first time
	@Override
    protected void initialize() {
		if(timeoutTimer != null) {
			timeoutTimer.reset();
			timeoutTimer.start();
		}

		setInterruptible(true);
		System.out.println("Starting vision turn");
    	stationaryAnglePID.setSetPoint(0);
    }
    
    @Override
    protected boolean isFinished() {
    	// check if we timed out
    	if(timeoutTimer != null && timeoutTimer.get() >= timeOut) {
    		return true;
    	}
    	// check if we are aiming at the target
        return Math.abs(stationaryAnglePID.getError()) < 0.5 &&
        	   Math.abs(Robot.drivetrain.getGyroRate()) < 1;
    }

    protected void interuppted(){
    	stationaryAnglePID.setSetPoint(getProcessVaraible()); //NOAH ADDED THE OFFSET
    	setLeftRightMotorOutputsPIDTurning(0.0,0.0);
    	end();
    }
}
