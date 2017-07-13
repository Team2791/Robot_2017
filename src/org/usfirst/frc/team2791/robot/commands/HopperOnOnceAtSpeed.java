package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the {@link ShakerHopper} motors backwards, can be used to unjam the hopper
 */
public class HopperOnOnceAtSpeed extends Command{
	
	public boolean atSpeed = false;
	
	public HopperOnOnceAtSpeed(){
		super("HopperOn");
		requires(Robot.hopper);
	}
	
	protected void initialize(){}
	
	protected void execute(){
		if(Robot.shooter.atSpeed()){
			atSpeed = true;
		}
		
		if(atSpeed)
			Robot.hopper.runHopper();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){}
	
	protected void interrupted(){
		end();
	}
}