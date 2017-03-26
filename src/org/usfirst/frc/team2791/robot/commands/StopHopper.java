package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the Hopper motors safely
 */
public class StopHopper extends Command{
	public StopHopper(){
		super("StopHopper");
		requires(Robot.hopper);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.hopper.setHopperSpeed(0.0);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.hopper.setHopperSpeed(0.0);
	}
	
	protected void interrupted(){
	}
}