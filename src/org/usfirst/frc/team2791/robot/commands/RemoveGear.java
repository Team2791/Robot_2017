package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RemoveGear extends Command{
	public RemoveGear (){
		super("RemoveGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected boolean isFinished(){
		return Robot.gearMechanism.getGearState();
	}
	
	protected void end(){
		System.out.println("RemoveGear has ended");
	}
	
	protected void interrupted(){
		end();
	}
}