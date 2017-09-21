package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Runs the {@link ShakerIntake} motors and actuates the intake to the outward position
 * </br>This Command has no automatic ending (The ending is user requested)
 */
public class IntakeOn extends Command{
	public IntakeOn(){
		super("IntakeOn");
		requires(Robot.intake);
	}
	protected void initialize(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOnClimber();
		Robot.intake.setIntakePosition(true);
	}
	
	protected void execute(){
		Robot.intake.disengageRatchetWing();
		
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
		end();
	}
}