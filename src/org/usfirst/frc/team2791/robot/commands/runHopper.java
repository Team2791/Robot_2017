package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class runHopper extends Command{
	public runHopper(){
		super("runHopper");
		requires(Robot.hopper);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of hopper run");
		initialize();
	}
	protected void initialize(){
		System.out.println("came to initialize of hopper run");
		execute();
	}
	protected void execute(){
		System.out.println("I'm trying to execute hopper run");
		Robot.hopper.runHopper();
		
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		Robot.hopper.stopMotor();
	}
	protected void interrupted(){
	}
}