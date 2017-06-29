package org.usfirst.frc.team2791.robot.commands.pid;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.BasicPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *Uses BasicPID util class to create a PID for auto-driving. PID makes sure that the distance is correct and the robot drives straight
 *@see BasicPID
 */
public class DriveStraightVision extends DrivetrainPIDStraightDrive {
	
	/**
	 * 
	 * @param distanceFromBoiler the desired distance from the boiler in inches
	 */
	public DriveStraightVision(double distanceFromBoiler, double maxOutput) {
		super(distanceFromBoiler, maxOutput, 20.0, 0.3); //kind of high threshold rn b/c i'm trash at image filtering
 		setInterruptible(true);
		distancePID.setInvertOutput(false);
	}
	
	@Override
	protected void execute(){
		super.execute();
		debug();
	}
	
	@Override
	protected void initialize() {
		distancePID.setSetPoint(0);
		movingAnglePID.setSetPoint(Robot.drivetrain.getGyroAngle());    
	}
	
	@Override
	protected double getProcessVariable() {
		return (Robot.visionTable.getRealtimeBoilerDistanceError(distanceToDrive * 12)) /12;
	}
	
	public void debug(){
		super.debug();
	}

}
