package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 * Stops the intake motors and brings the intake in, does not effect the ratchet
 * TODO: get rid of this redundant command (due to the fact that we can use toggleWhenPressed in OI)
 */
public class TurnIntakeOff extends Command{
	public TurnIntakeOff(){
		super("TurnIntakeOff");
		requires(Robot.intake);		
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.intake.motorOffIntake();
		Robot.intake.moveIntakeOut(false);
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
