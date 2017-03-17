package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EngageRope extends Command{
	public EngageRope(){
		super("EngageRope");
		requires(Robot.intake);
	}
	
	protected void initialize(){}
	
	protected void execute(){
			Robot.intake.motorOnClimberSlow();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.motorOffIntake();
	}
	protected void interrupted(){
		Robot.intake.motorOffIntake();
	}
}
