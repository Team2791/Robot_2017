package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Runs the intake motors and actuates the intake to the outward position
 */
public class RunIntake extends Command{
	public RunIntake(){
		super("RunIntake");
		requires(Robot.intake);
	}
	protected void initialize(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOnClimber();
	}
	
	protected void execute(){
		Robot.intake.disengageRatchetWing();//TODO: get rid of this here to fix double climb bug
		
		if(Robot.intake.isRatchetWingDisengaged()){
			Robot.intake.setIntakePosition(true);
			Robot.intake.motorOnIntake();
		}
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		Robot.intake.motorOffIntake();
		Robot.intake.setIntakePosition(false);
	}
	protected void interrupted(){
		Robot.intake.motorOffIntake();
		Robot.intake.setIntakePosition(false);
	}
}