package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HopperOn extends Command{
	public HopperOn (){
		super("HopperOn");
		requires(Robot.hopper);
	}
	protected void initialize(){}
	
	protected void execute(){
		Robot.hopper.setHopperSpeed(1.0);
		System.out.println("executing hopper on");
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		System.out.println("ending hopper on");
		Robot.hopper.setHopperSpeed(0.0);
	}
	
	protected void interrupted(){}
}
