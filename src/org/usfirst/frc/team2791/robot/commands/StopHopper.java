package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopHopper extends Command{
	public StopHopper(){
		super("StopHopper");
		requires(Robot.hopper);
	}
	
	protected void initialize(){
		Robot.hopper.stopHopper();
	}
	
	protected void execute(){
		Robot.hopper.stopHopper();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.hopper.stopHopper();
	}
	
	protected void interrupted(){
		Robot.hopper.stopHopper();
	}
}