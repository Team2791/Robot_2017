package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Pauses Drivetrain for a given amount of time (default is 30s)
 */
public class PauseDrivetrain extends Command {
	
	private double VOUT = 1.o;
	
	public DriveForward() {
		super("DriveForward");
		requires(Robot.drivetrain);
		
		timePeriod = 30.0;
	}
	
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		System.out.println("DriveForward is Executing");
		Robot.drivetrain.setLeftRightMotorOutputs(VOUT, VOUT);
			
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		System.out.println("DriveForward has ended");
	}

	protected void interrupted() {
		end();
	}

}
