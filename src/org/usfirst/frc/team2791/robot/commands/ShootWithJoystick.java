package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.ShakerJoystick.OverriddenJoystick;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;
import org.usfirst.frc.team2791.robot.util.GTADrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command was used when first testing the {@link ShakerShooter}. 
 * It runs the shooter at varying speeds according to the {@link OverriddenJoystick Joystick}
 * TODO: get rid of this unused command
 *
 */
public class ShootWithJoystick extends Command {
	public ShootWithJoystick() {
		super("ShootWithJoystick");
		requires(Robot.shooter);
	}
	
	protected void initialize() {}

	@Override
	protected void execute() {
		Robot.hopper.runHopper();
		Robot.shooter.setVBusWithTrigger(-GTADrive.getLeftValue(Robot.oi.operator));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.disable();
	}

	protected void interrupted() {
		new TurnShooterOff();
	}

}
