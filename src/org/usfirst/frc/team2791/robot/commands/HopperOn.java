package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the hopper to feed balls into the shooter
 */
public class HopperOn extends Command {
	public HopperOn (){
		super("HopperOn");
		requires(Robot.hopper);
	}
	protected void initialize(){
	}
	
	protected void execute(){
			Robot.hopper.runHopper();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.hopper.setHopperSpeed(0.0);
	}
	
	protected void interrupted(){
		end();
	}
}
