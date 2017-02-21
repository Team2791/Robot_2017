package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunIntake extends Command{
	public RunIntake(){
		super("RunIntake");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
//		System.out.println("came to constructor of intake");
	}
	protected void initialize(){
//		System.out.println("came to initialize of intake");
	}
	protected void execute(){
//		System.out.println("I'm trying to execute intake on");
		Robot.intake.disengageRatchetWing();
		Robot.intake.moveIntakeOut(true);
		Robot.intake.motorOnIntake();
		
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		Robot.intake.motorOffIntake();
		Robot.intake.moveIntakeOut(false);
	}
	protected void interrupted(){
		Robot.intake.motorOffIntake();
		Robot.intake.moveIntakeOut(false);
	}
}