package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EmptyFieldHopper extends Command{
	public EmptyFieldHopper (){
		super("EmptyFieldHopper");
		requires(Robot.drivetrain);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		//TODO: write this to 'wiggle' the robot to engage the field Hopper
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
	}
}