package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunClimber extends Command{
	public RunClimber(){
		super("RunClimber");
		requires(Robot.intake);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.intake.debug();
		Robot.intake.engageRatchetWing();
//		if(Robot.intake.getCurrentUsage()<56.0){
			Robot.intake.motorOnClimber();
//		}
//		else
//			Robot.intake.motorOffIntake();
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
