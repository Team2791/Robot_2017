package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} without effecting the motors
 */
public class GearMechUp extends Command {

    public GearMechUp() {
    	super("GearMechDown");
    	requires(Robot.gearMechanism);
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
