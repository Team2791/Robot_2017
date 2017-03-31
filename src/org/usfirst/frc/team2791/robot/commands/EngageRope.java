package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the {@link ShakerIntake Climber} at a slower speed to catch the rope.
 */
public class EngageRope extends Command{
	public EngageRope(){
		super("EngageRope");
		requires(Robot.intake);
	}
	
	protected void initialize(){}
	
	protected void execute(){
			Robot.intake.motorOnClimberSlow();
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
