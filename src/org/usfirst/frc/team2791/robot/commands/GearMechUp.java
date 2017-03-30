package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

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
    	Robot.gearMechanism.changeGearSolenoidState(false);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();   
    }
}
