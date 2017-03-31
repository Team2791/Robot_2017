package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the {@link ShakerHopper} motors backwards, can be used to unjam the hopper
 */
public class RunHopperBackwards extends Command{
	public RunHopperBackwards(){
		super("RunHopperBackwards");
		requires(Robot.hopper);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		Robot.hopper.setHopperSpeed(-1.0);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.hopper.setHopperSpeed(0.0);
	}
	
	protected void interrupted(){
		end();
	}
}