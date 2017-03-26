package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts Gear Mechanism down w/o motor running
 */
public class ScoreGear extends Command{
	public ScoreGear (){
		super("RemoveGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected void execute(){
		System.out.println("Unnas says hehehe");
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	
	protected void interrupted(){
		end();
	}
}
