package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} down and runs the motors
 */
public class IntakeGearFromFloor extends Command {

	Timer timer = new Timer();
	boolean gotGear;
	
	public IntakeGearFromFloor() {
		super("IntakeGearFromFloor");
		requires(Robot.gearMechanism);
	}

	protected void initialize() {
		Robot.gearMechanism.setGearIntakeDown(true);
		Robot.gearMechanism.runGearIntake();
		gotGear = false;
		
		timer.stop();
		timer.reset();
	}

	protected void execute() {

		System.out.println("gear pickup timer = " + timer.get());
		
		if(Robot.gearMechanism.hasGear() && !gotGear){
			gotGear = true;
			timer.start();
		}
		
		if(timer.get() > 0.5) {
			Robot.gearMechanism.setGearIntakeDown(false);
		}
		
		if(timer.get() > 1.5) {
			Robot.gearMechanism.stopGearIntake();
		} else {
			Robot.gearMechanism.runGearIntake();
		}
	}

	protected boolean isFinished() {
		return timer.get() > 1.51;
	}

	protected void end() {
		Robot.gearMechanism.stopGearIntake();
	}

	protected void interrupted() {
		end();
	}
}
