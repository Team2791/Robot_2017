package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} down and runs the gear intake
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
		//
		Robot.gearMechanism.runGearIntake();
	}

	protected boolean isFinished() {
		//return true;
		return 	Robot.gearMechanism.hasGear();
;
	}

	protected void end() {
		//
		System.out.println("Calling GearMechUp from GearMechDown")
		new GearMechUp();
	}

	protected void interrupted() {
		Robot.gearMechanism.stopGearIntake();
		end();   
	}
}
