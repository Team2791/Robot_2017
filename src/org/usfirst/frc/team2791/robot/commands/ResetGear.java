package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetGear extends Command{
	public ResetGear (){
		super("ResetGear");
		requires(Robot.gearMechanism);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of gear");
		initialize();
	}
	protected void initialize(){
		System.out.println("came to initialize of gear");
		execute();
	}
	protected void execute(){
		System.out.println("I'm trying to execute gear reset");
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		
	}
	protected void interrupted(){
	}
}
