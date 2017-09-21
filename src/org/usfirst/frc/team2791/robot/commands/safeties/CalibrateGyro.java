package org.usfirst.frc.team2791.robot.commands.safeties;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.ShakerGyro;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Calibrates the {@link ShakerGyro Gyro}, should be called at robot init.
 * </br><i>Calibration takes a few seconds and is expected to slow down robot boot up</i>
 * ***The robot MUST BE STILL when the gyro is calibrating</br>
 * **This Command ends once the calibration is complete.
 */
public class CalibrateGyro extends Command{
	public CalibrateGyro (){
		super("CalibrateGyro");
		requires(Robot.drivetrain);
	}
	protected void initialize(){
		Robot.drivetrain.calibrateGyro();
	}
	
	protected void execute(){}
	
	protected boolean isFinished(){
		return true;
	}
	
	protected void end(){}
	
	protected void interrupted(){}
}
