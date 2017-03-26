package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the Gear Position to down and runs the intake motors
 */
public class GearIntakeDown extends Command{
	public GearIntakeDown (){
		super("RemoveGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected void execute(){
//		Robot.gearMechanism.runGearIntake();
	}
	
	protected boolean isFinished(){
		return true;
//		return false;
	}
	
	protected void end(){
//		Robot.gearMechanism.stopGearIntake();		
	}
	
	protected void interrupted(){
		end();
	}
}
