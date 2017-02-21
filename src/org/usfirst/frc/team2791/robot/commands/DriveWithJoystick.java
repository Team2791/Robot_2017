package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.GTADrive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {
	
	private double speedMultiplier = 1.0;
	
	public DriveWithJoystick() {
		super("DriveWithJoystick");
		requires(Robot.drivetrain);
	}
	
	protected void initialize() {
	}
	@Override
	protected void execute() {
		Robot.drivetrain.setLeftRightMotorOutputs(speedMultiplier*GTADrive.getLeftValue(Robot.oi.driver),
				speedMultiplier*GTADrive.getRightValue(Robot.oi.driver));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Robot.drivetrain.setLeftRightMotorOutputs(0.0,0.0);
	}

	protected void interrupted() {
		new DriveWithJoystick();
	}

}
