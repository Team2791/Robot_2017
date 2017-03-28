package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the Gear mechanism and keeps the motors running
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
