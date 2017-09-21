package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} down and intakes until the breakbeam sensors are activated.</br>
 *When the sensors ARE activated, the intakes continues to run for a few seconds to keep the gear secure 
 *</br>This Command has a timed ending (1.51 seconds after the gear is recieved and the sensors are activated)
 **/

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
