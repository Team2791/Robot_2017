package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the {@link ShakerHopper} motors backwards, can be used to unjam the hopper
 */
public class HopperOn extends Command{
	public HopperOn(){
		super("HopperOn");
		requires(Robot.hopper);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.hopper.runHopper();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.hopper.stopHopper();
	}
	
	protected void interrupted(){
		end();
	}
}