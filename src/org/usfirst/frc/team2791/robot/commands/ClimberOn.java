package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;

/**
 * Runs the {@link ShakerIntake Climber} and Engages the {@link ShakerIntake#ratchetWingSolenoid ratchet} to prevent the robot from falling down the rope
 * </br>This Command has no automatic ending (The ending is user requested)
 */
public class ClimberOn extends Command{
	// if you need the climber to stop after it hits the top in order to prevent brownouts, then set this to true;
	// we have it set to false because we didn't see signficant brownouts when climbing
	private boolean useCurrentSensing = false;
	
	public ClimberOn(){
		super("RunClimber");
		requires(Robot.intake);
	}
	
	protected void initialize(){
		Robot.intake.isClimbing = true;
	}
	
	protected void execute(){
		Robot.intake.isClimbing = true;

		Robot.intake.engageRatchetWing();
		Robot.intake.debug();
		
		if(Robot.intake.getCurrentUsage()<56.0 && useCurrentSensing) {
			Robot.intake.motorOnClimber();
		
		}else if(useCurrentSensing) {
			Robot.intake.motorOffIntake();
		
		}else {
			Robot.intake.motorOnClimber();
		}

	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.isClimbing = false;

		Robot.intake.motorOffIntake();
	}
	protected void interrupted(){
		end();
	}
}
