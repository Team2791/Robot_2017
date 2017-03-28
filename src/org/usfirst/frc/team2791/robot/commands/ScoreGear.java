package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts Gear Mechanism down w/o motor running
 */
public class ScoreGear extends Command{
	public ScoreGear (){
		super("ScoreGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected void execute(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected boolean isFinished(){
		return false;//don't return true with condition because want command to end when driver releases button
	}
	
	protected void end(){
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	
	protected void interrupted(){
		end();
	}
}
