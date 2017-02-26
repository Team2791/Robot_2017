package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CalibrateGyro extends Command{
	public CalibrateGyro (){
		super("CalibrateGyro");
		requires(Robot.drivetrain);
	}
	protected void initialize(){}
	
	protected void execute(){
		Robot.drivetrain.calibrateGyro();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){}
	
	protected void interrupted(){}
}
