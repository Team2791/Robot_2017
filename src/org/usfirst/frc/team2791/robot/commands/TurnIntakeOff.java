package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnIntakeOff extends Command{
	public TurnIntakeOff(){
		super("TurnIntakeOff");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of off-ing intake");
		
	}
	protected void initialize(){
		System.out.println("came to initialize of off-ing intake");
		
	}
	protected void execute(){
		System.out.println("I'm trying to execute intake off");
		Robot.intake.setIntakeOut(false);
		Robot.intake.motorOffIntake();
		
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		Robot.intake.setIntakeOut(false);
	}
	protected void interrupted(){
		Robot.intake.setIntakeOut(false);
	}
}
