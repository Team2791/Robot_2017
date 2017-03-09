package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveGearMech extends Command{
	public MoveGearMech (){
		super("RemoveGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		boolean currentState = Robot.gearMechanism.getGearState();
		Robot.gearMechanism.changeGearSolenoidState(!currentState);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		new DriveWithJoystick();
	}
}