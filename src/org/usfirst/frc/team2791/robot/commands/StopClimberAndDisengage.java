package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopClimberAndDisengage extends Command{
	
	public StopClimberAndDisengage(){
		super("StopClimberAndDisengage");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOffIntake();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOffIntake();
	}
	
	protected void interrupted(){
		new TurnIntakeOff();
	}
}
