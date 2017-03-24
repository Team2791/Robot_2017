package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeGear extends Command {

    public IntakeGear() {
        requires(Robot.gearMechanism);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearMechanism.changeGearSolenoidState(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearMechanism.runGearIntake();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.gearMechanism.changeGearSolenoidState(false);
    }
    protected void interrupted() {
    	end();
    }
}
