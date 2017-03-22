package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendIntake extends Command{
	public ExtendIntake(){
		super("ExtendIntake");
		requires(Robot.intake);
	}
	protected void initialize(){
	}
	
	protected void execute(){
		Robot.intake.moveIntakeOut(true);
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		Robot.intake.moveIntakeOut(false);
	}
	protected void interrupted(){
		end();
	}
}