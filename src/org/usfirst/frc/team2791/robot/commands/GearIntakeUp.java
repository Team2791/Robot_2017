package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts Gear Mechanism down w/o motor running
 */
public class GearIntakeUp extends Command{
	public GearIntakeUp (){
		super("RemoveGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected void execute(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected boolean isFinished(){
		return !Robot.gearMechanism.isDown();
	}
	
	protected void end(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected void interrupted(){
		Robot.gearMechanism.changeGearSolenoidState(true);

	}
}
