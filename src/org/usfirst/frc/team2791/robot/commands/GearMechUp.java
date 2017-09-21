package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} up and runs the motors for .5 secs to keep the gear secure
 *</br>This Command has a timed ending (0.5 seconds after it starts)
 */
public class GearMechUp extends Command {

	private double timeout = 0.5;
	
    public GearMechUp() {
    	super("GearMechUp");
    	requires(Robot.gearMechanism);
    	setTimeout(timeout);
    }

    protected void initialize() {
    	Robot.gearMechanism.setGearIntakeDown(false);
    	setTimeout(0.75);
    }

    protected void execute() {
    	Robot.gearMechanism.runGearIntake();
    }

    protected boolean isFinished() {
        return this.isTimedOut();
    }

    protected void end() {
    	Robot.gearMechanism.stopGearIntake();


    }

    protected void interrupted() {
    	end();   
    }
}
