package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts {@link ShakerGear} Mechanism down and stops the gear motors from running.</br>
 * This will work regardless of the status of the limit switches
 */
public class ScoreGearAutoReturn extends Command{
	public ScoreGearAutoReturn (){
		super("ScoreGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.setGearIntakeDown(true);
	}
	
	protected void execute(){
		Robot.gearMechanism.stopGearIntake(); 
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake();
		Robot.gearMechanism.setGearIntakeDown(false);
	}
	
	protected void interrupted(){
		end();
	}
}
