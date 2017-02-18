package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunHopper extends Command{
	public RunHopper(){
		super("RunHopper");
		requires(Robot.hopper);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of hopper run");
		initialize();
	}
	protected void initialize(){
		System.out.println("came to initialize of hopper run");
		execute();
	}
	protected void execute(){
		while(Robot.hopper.moreBalls()){
			System.out.println("I'm trying to execute hopper run");
			Robot.hopper.runHopper();
		}
		//if lag still present, then use an if-else statement and say that if-true, then RunHopper, else->Robot.hopper.stopMotor();
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