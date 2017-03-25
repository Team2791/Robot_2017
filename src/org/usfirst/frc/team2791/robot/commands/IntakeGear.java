package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Gear Intake goes down and runs, then goes up after.
 */
public class IntakeGear extends Command {

    public IntakeGear() {
        requires(Robot.gearMechanism);
    }

    protected void initialize() {
    	Robot.gearMechanism.changeGearSolenoidState(true); //gear does down
    }

    protected void execute() {
    	System.out.println("GearIntake execute");
    	Robot.gearMechanism.runGearIntake();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	System.out.println("GearIntake end");
    	new GearIntakeUp();
    }
    protected void interrupted() {
    	System.out.println("GearIntake interrupted");
    	end();
    }
}
