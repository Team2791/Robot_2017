package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;

/**
 * Runs the {@link ShakerIntake Climber} and Engages the {@link ShakerIntake#ratchetWingSolenoid ratchet} to prevent the robot from falling down the rope
 */
public class ClimberOn extends Command{
	public ClimberOn(){
		super("RunClimber");
		requires(Robot.intake);
	}
	
	protected void initialize(){
		Robot.intake.isClimbing = true;
	}
	
	protected void execute(){
		Robot.intake.isClimbing = true;

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
		Robot.intake.isClimbing = false;

		Robot.intake.motorOffIntake();
	}
	protected void interrupted(){
		Robot.intake.motorOffIntake();
	}
}
