package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} down and intakes until the limit switch is activated motors as long as the gear mechanism is down and the switches aren't activated
 */
public class GearMechActiveIntake extends Command {

	private Timer timer = new Timer();
	private boolean gotGear = false;
	
	public GearMechActiveIntake() {
		super("GearMechActiveIntake");
		requires(Robot.gearMechanism);

	}

	protected void initialize() {	
		Robot.gearMechanism.setGearIntakeDown(true);
		Robot.gearMechanism.runGearIntake();
		
		timer.stop();
		timer.reset();
	}

	protected void execute() {

		Robot.gearMechanism.runGearIntake();
		
		if(Robot.gearMechanism.hasGear() && !gotGear){
			gotGear = true;
			timer.start();
			
		}
			
		if(timer.get() > 0.25){
			Robot.gearMechanism.setGearIntakeDown(false);
		}
			
		if(timer.get() > 1.5){
			Robot.gearMechanism.stopGearIntake();
		}else {
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
