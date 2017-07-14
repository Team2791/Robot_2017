package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.util.GTADrive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Uses the {@link GTADrive} I/O Logic to control the {@link ShakerDrivetrain Drivetrain}.
 * @see GTADrive
 */
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
		double left = speedMultiplier*GTADrive.getLeftValue(Robot.oi.driver);
		double right = speedMultiplier*GTADrive.getRightValue(Robot.oi.driver);
		Robot.drivetrain.setLeftRightMotorOutputs(left, right);
		
		SmartDashboard.putNumber("Left Joystick Output", left);
		SmartDashboard.putNumber("Right Joystick Output", right);

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
		System.out.println("Drive with joystik interrupted.");
	}

}
