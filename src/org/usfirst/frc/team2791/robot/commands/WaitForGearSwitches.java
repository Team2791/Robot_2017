package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} up if it is already down and it has a gear according to the {@link ShakerGear#limitSwitch limit switch} 
 *</br> This is the default command for the {@link ShakerGear} Subsystem
 */
public class WaitForGearSwitches extends Command {

	public WaitForGearSwitches() {
		super("RunGearMotor");
		requires(Robot.gearMechanism);

	}

	protected void initialize() {}

	protected void execute() {}

	protected boolean isFinished() {
		return (Robot.gearMechanism.hasGear() && Robot.gearMechanism.isDown());
	}

	protected void end() {
		new GearMechUp();
	}

	protected void interrupted() {
		end();
	}
}
