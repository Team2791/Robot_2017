package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.OI;
import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopClimberAndUnattach extends Command{
	public StopClimberAndUnattach(){
		super("StopClimberAndUnattach");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of climber");
		//initialize();
	}
	protected void initialize(){
		System.out.println("came to initialize of climber");
		//execute();
	}
	protected void execute(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOffIntake();
	}
	protected boolean isFinished(){
		return true;
	}
	protected void end(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOffIntake();
	}
	protected void interrupted(){
		new TurnIntakeOff();
	}
}
