package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts Gear Mechanism down and stops the gear motors from running.</br>
 * This will work regardless of the status of the limit switches
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
		Robot.gearMechanism.stopGearIntake(); // might get rid of this 
		Robot.gearMechanism.changeGearSolenoidState(true); //might get rid of this
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake(); //might get rid of this
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	
	protected void interrupted(){
		end();
	}
}
