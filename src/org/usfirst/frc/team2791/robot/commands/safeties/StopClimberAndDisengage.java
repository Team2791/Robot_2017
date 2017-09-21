package org.usfirst.frc.team2791.robot.commands.safeties;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the {@link ShakerIntake Climber} motor and disengages the {@link ShakerIntake#ratchetWingSolenoid ratchet}.
 *  This is if we need to let go of the rope while climbing.
 *  THE ROBOT WILL FALL IF YOU USE THIS SO USE IT UNDER EXTERME EMERGENCIES ONLY
 */
public class StopClimberAndDisengage extends Command{
	
	public StopClimberAndDisengage(){
		super("StopClimberAndDisengage");
		requires(Robot.intake);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOffIntake();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOffIntake();
	}
	
	protected void interrupted(){
		new TurnIntakeOff();
	}
}
