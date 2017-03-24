package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs intake belt without actuating the intake
 *
 */
public class RunIntakeBelt extends Command{
	public RunIntakeBelt(){
		super("RunIntakeBelt");
		requires(Robot.intake);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.intake.engageRatchetWing();//hard-coded in to ensure no destruction
		
		if(Robot.intake.isRatchetWingDisengaged()){
			Robot.intake.motorOnClimber();
		}
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.motorOffIntake();
	}
	
	protected void interrupted(){
		end();
	}
	
}