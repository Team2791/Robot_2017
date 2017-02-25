package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunHopper extends Command{
	public RunHopper(){
		super("RunHopper");
		requires(Robot.hopper);
	}
	
	protected void initialize(){
		Robot.hopper.runHopper();
	}
	
	protected void execute(){
		SmartDashboard.putBoolean("isBallAtTop", Robot.hopper.isBallAtTop());
			Robot.hopper.runHopper();
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.hopper.stopHopper();
	}
	
	protected void interrupted(){
		Robot.hopper.stopHopper();
	}
}