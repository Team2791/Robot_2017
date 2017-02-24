package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
//unjams the hopper if needed
public class RunHopperBackwards extends Command{
	public RunHopperBackwards(){
		super("RunHopperBackwards");
		requires(Robot.hopper);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.hopper.setHopperSpeed(-0.66);
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