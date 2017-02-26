package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetGear extends Command{
	public ResetGear (){
		super("ResetGear");
		requires(Robot.gearMechanism);
	}
	protected void initialize(){}
	
	protected void execute(){
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){}
	
	protected void interrupted(){
		new DriveWithJoystick();
	}
}
