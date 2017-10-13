package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *Puts the {@link ShakerGear Gear Intake} down and intakes until the current draw limit is exceeded motors as long as the gear mechanism is down
 */
public class GearMechWithCurrentSensor extends Command {

	private Timer timer = new Timer();
	private boolean status = false; //Noah added this
	private boolean triggered = false; //Noah added this
	public GearMechWithCurrentSensor() {
		super("GearMechActiveIntake");
		requires(Robot.gearMechanism);

	}

	protected void initialize() {	
		Robot.gearMechanism.setGearIntakeDown(true);
		Robot.gearMechanism.runGearIntake();
		SmartDashboard.putNumber("GearCurrent", CONSTANTS.GEAR_CURRENT);
		
		timer.stop();
		timer.reset();
		timer.start();
		status = false;
		triggered = false;
	}

	protected void execute() {

		Robot.gearMechanism.runGearIntake();
		if(status==false){
			if(timer.get() > 1.0){
			status=true;
			timer.stop();
			timer.reset();
			}
			}
		
		if(status==true){
		if(Robot.gearMechanism.getCurrentUsage() > CONSTANTS.GEAR_CURRENT && triggered == false){
			timer.start();
			triggered = true;
			
		}
			
		if(timer.get() > 0.25){
			Robot.gearMechanism.setGearIntakeDown(false);
		}
			
		if(timer.get() > 1.0){
			Robot.gearMechanism.stopGearIntake();
		}else {
			Robot.gearMechanism.runGearIntake();
		}
		}

	}

	protected boolean isFinished() {
		return timer.get() > 1.51;
	}

	protected void end() {
		Robot.gearMechanism.stopGearIntake();
		status = false;
	}

	protected void interrupted() {
		end();
	}
}
