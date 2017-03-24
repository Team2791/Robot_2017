package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearIntakeDown extends Command{
	public GearIntakeDown (){
		super("RemoveGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected void execute(){}
	
	protected boolean isFinished(){
		return true;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
	}
}
