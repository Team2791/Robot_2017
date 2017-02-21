package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunClimber extends Command{
	public RunClimber(){
		super("RunClimber");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of climber");
	}
	protected void initialize(){
		System.out.println("came to initialize of climber");
	}
	protected void execute(){
		//System.out.println("I'm trying to execute climbing");
		Robot.intake.debug();
		Robot.intake.engageRatchetWing();
		if(Robot.intake.getCurrentUsage()<56.0){
			Robot.intake.motorOnClimber();
		}
		else
			Robot.intake.motorOffIntake();
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
//		Robot.intake.stopMotor();
		Robot.intake.motorOffIntake();
	}
	protected void interrupted(){
//		new TurnIntakeOff();
		Robot.intake.motorOffIntake();

	}
}
