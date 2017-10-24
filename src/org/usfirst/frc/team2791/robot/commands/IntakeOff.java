package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Runs the {@link ShakerIntake} motors and actuates the intake to the outward position
 */
public class IntakeOff extends Command{
	public IntakeOff(){
		super("IntakeOff");
		requires(Robot.intake);
	}
	protected void initialize(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOnClimber();
	}
	
	protected void execute(){
		Robot.intake.disengageRatchetWing();//TODO: get rid of this here to fix double climb bug
		
		if(Robot.intake.isRatchetWingDisengaged()){
			Robot.intake.motorOffIntake();
			Robot.intake.setIntakePosition(false);
		}
	}
	protected boolean isFinished(){
		return true;
	}
	protected void end(){
	}
	protected void interrupted(){
		end();
	}
}