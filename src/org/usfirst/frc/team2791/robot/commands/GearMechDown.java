package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} down without effecting the motors
 */
public class GearMechDown extends Command {
	
	public GearMechDown() {
		super("GearMechDown");
		requires(Robot.gearMechanism);
	}

	protected void initialize() {
		Robot.gearMechanism.changeGearSolenoidState(true);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();   
	}
}
