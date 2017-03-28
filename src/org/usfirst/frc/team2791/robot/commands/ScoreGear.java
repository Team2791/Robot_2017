package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts Gear Mechanism down and stops the gear motors from running
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
		Robot.gearMechanism.stopGearIntake();
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected boolean isFinished(){
		return false;//don't return true because we want command to end when driver releases button
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake();
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	
	protected void interrupted(){
		end();
	}
}
