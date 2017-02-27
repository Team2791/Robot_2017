package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Pauses Drivetrain for a given amount of time (default is 30s)
 *
 */
public class PauseDrivetrain extends Command {
	
	private double timePeriod;
	private double startTime;
	
	public PauseDrivetrain() {
		super("PauseDrivetrain");
		requires(Robot.drivetrain);
		
		timePeriod=30.0;
	}
	
	public PauseDrivetrain(double time_){
		super("PauseDrivetrain");
		requires(Robot.drivetrain);
		
		timePeriod=time_;
	}
	
	
	
	
	protected void initialize() {
		startTime=Timer.getFPGATimestamp();
	}
	
	@Override
	protected void execute() {
		System.out.println("PauseDrivetrain is Executing");
		if(Timer.getFPGATimestamp()-startTime < timePeriod){
			Robot.drivetrain.setLeftRightMotorOutputs(0.0, 0.0);
		}else{
			end();
		}
			
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		System.out.println("PauseDrivetrain has ended");
	}

	protected void interrupted() {
		end();
	}

}
