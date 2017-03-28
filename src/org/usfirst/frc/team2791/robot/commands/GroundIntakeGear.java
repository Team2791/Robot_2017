package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Gear Intake goes down and runs, then goes up after.
 */
public class GroundIntakeGear extends Command {

	Timer timer = new Timer(); //must start before isFinished is called

	/**
	 * this is -1.0 at the beginning so we can tell if we have started timing already
	 */

	/**
	 * checks if we have the gear and are ready to lift the gear into the bot
	 */
	private boolean unnas = false;
	
	public GroundIntakeGear() {
		requires(Robot.gearMechanism);
	}

	protected void initialize() {
		Robot.gearMechanism.changeGearSolenoidState(true); //gear does down
		unnas = true;
	}

	protected void execute() {
		Robot.gearMechanism.runGearIntake();
		
		if(unnas && Robot.gearMechanism.getLimitSwitchState()){
			timer.reset();
			unnas = false;
		}
		if(Robot.gearMechanism.getLimitSwitchState()){
			Robot.gearMechanism.changeGearSolenoidState(false);
			timer.start();
		}
	}

	protected boolean isFinished() {
		return timer.get() >= 0.5;
	}

	protected void end(){
		Robot.gearMechanism.stopGearIntake();

	}
	
	protected void interrupted() {
		Robot.gearMechanism.changeGearSolenoidState(false);
		end();
	}
}
