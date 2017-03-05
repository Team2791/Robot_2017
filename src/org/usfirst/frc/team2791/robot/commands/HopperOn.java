package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HopperOn extends Command {
	public HopperOn (){
		super("HopperOn");
		requires(Robot.hopper);
		System.out.println("constructing hopper on");
	}
	protected void initialize(){
		System.out.println("initializing hopper on");
	}
	
	protected void execute(){
		System.out.println("executing hopper on");
			Robot.hopper.runHopper();
	}
	
	protected boolean isFinished(){
		System.out.println("isFinished hopper on");
		return false;
	}
	
	protected void end(){
		System.out.println("ending hopper on");
		Robot.hopper.setHopperSpeed(0.0);
	}
	
	protected void interrupted(){
		System.out.println("interrupted hopper on");
		end();
	}
}
