package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class runIntake extends Command{
	public runIntake(){
		super("runIntake");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of intake");
		initialize();
	}
	protected void initialize(){
		System.out.println("came to initialize of intake");
		execute();
	}
	protected void execute(){
		System.out.println("I'm trying to execute intake on");
		Robot.intake.moveIntakeOut(true);
		Robot.intake.motorOnIntake();
		
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		Robot.intake.stopMotor();
	}
	protected void interrupted(){
	}
}