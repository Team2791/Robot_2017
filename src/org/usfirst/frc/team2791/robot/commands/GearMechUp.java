package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} up and runs the gears for .5 secs
 */
public class GearMechUp extends Command {

	private double timeout = 0.5;
	
    public GearMechUp() {
    	super("GearMechDown");
    	requires(Robot.gearMechanism);
    	setTimeout(timeout);
    }

    protected void initialize() {
    	Robot.gearMechanism.changeGearSolenoidState(false);
    }

    protected void execute() {
    	//
    	Robot.gearMechanism.runGearIntake();
    }

    protected boolean isFinished() {
    	//return true;
        return isTimedOut();
    }

    protected void end() {
    	//
    	Robot.gearMechanism.stopGearIntake();
    }

    protected void interrupted() {
    	end();   
    }
}
