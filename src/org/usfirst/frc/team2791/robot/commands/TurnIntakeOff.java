package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnIntakeOff extends Command{
	public TurnIntakeOff(){
		super("TurnIntakeOff");
		requires(Robot.intake);		
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.intake.moveIntakeOut(false);
		Robot.intake.motorOffIntake();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.moveIntakeOut(false);
	}
	
	protected void interrupted(){
		Robot.intake.moveIntakeOut(false);
	}
}
