package org.usfirst.frc.team2791.robot.commands.safeties;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;


/**
 * Stops the {@link ShakerIntake} motors and brings the intake in, does not effect the ratchet
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
		Robot.intake.setIntakePosition(false);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.setIntakePosition(false);
	}
	
	protected void interrupted(){
		Robot.intake.setIntakePosition(false);
	}
}
