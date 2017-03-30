package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2791.robot.subsystems.*;

/**
 * Runs the {@link ShakerIntake Climber} and Engages the {link ShakerIntake# Ratchet to prevent the robot from falling down the rope
 */
public class RunClimber extends Command{
	public RunClimber(){
		super("RunClimber");
		requires(Robot.intake);
	}
	
	protected void initialize(){
		
	}
	
	protected void execute(){
		Robot.intake.engageRatchetWing();//TODO: put this in initialize and see if that fixes double climb bug
		Robot.intake.debug();
//		if(Robot.intake.getCurrentUsage()<56.0){
			Robot.intake.motorOnClimber();
//		}
//		else
//			Robot.intake.motorOffIntake();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.motorOffIntake();
	}
	protected void interrupted(){
		Robot.intake.motorOffIntake();
	}
}
