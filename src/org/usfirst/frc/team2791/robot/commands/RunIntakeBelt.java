package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunIntakeBelt extends Command{
	public RunIntakeBelt(){
		super("RunIntakeBelt");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
//		System.out.println("came to constructor of intake belts");
	}
	protected void initialize(){
//		System.out.println("came to initialize of intake belts");
	}
	protected void execute(){
//		System.out.println("I'm trying to execute intake belt");
		Robot.intake.motorOnIntake();
		
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