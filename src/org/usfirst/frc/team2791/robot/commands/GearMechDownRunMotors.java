package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Holds {@link ShakerGear} Mechanism down and starts running gear motors to intake.</br>
 * This will work regardless of the status of the ir sensor
 * </br>This Command has no automatic ending (The ending is user requested)
 */
public class GearMechDownRunMotors extends Command{
	public GearMechDownRunMotors (){
		super("GearMechDownRunMotors");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.setGearIntakeDown(true);
	}
	
	protected void execute(){
		Robot.gearMechanism.runGearIntake(); 
}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake();
}
	
	protected void interrupted(){
		end();
	}
}
