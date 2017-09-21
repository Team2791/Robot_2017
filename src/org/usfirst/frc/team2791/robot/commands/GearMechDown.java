package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts {@link ShakerGear} Mechanism down and stops the gear motors from running.</br>
 * This will work regardless of the status of the ir sensors
 * </br>This Command has an immediate ending (there are only non-continous actions)
 */
public class GearMechDown extends Command{
	public GearMechDown (){
		super("GearMechDown");

		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.setGearIntakeDown(true);
	}	
	protected void execute(){
		Robot.gearMechanism.stopGearIntake(); 
	}

	
	protected boolean isFinished(){
		return true;
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake();
	}
	
	protected void interrupted(){
		end();

	}
}
