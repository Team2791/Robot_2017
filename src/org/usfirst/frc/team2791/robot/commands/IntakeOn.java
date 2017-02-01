package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeOn extends Command{
	public IntakeOn(){
		super("IntakeOn");
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
		Robot.intake.runIntake();
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
