package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunIntakeBelt extends Command{
	public RunIntakeBelt(){
		super("RunIntakeBelt");
		requires(Robot.intake);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.intake.disengageRatchetWing();//hard-coded in to ensure no destruction
		
		if(Robot.intake.isRatchetWingDisengaged()){
			Robot.intake.motorOnIntake();
		}
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.motorOffIntake();
	}
	
	protected void interrupted(){
	}
	
}