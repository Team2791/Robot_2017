package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Gear Intake goes down and runs, then goes up after.
 */
public class GroundIntakeGear extends Command {
	Timer timer = new Timer();
	private double motorRunTime = .5;
	
    public GroundIntakeGear() {
        requires(Robot.gearMechanism);
    }

    protected void initialize() {
    	Robot.gearMechanism.changeGearSolenoidState(true); //gear does down
    }

    protected void execute() {
    	Robot.gearMechanism.runGearIntake();
    }

    protected boolean isFinished() {
        return Robot.gearMechanism.getLimitSwitchState();
    }

    protected void end() {
    	timer.start();
    	Robot.gearMechanism.changeGearSolenoidState(false);
    	while(!timer.hasPeriodPassed(motorRunTime))
    		Robot.gearMechanism.runGearIntake();
    	Robot.gearMechanism.stopGearIntake();
    }
    protected void interrupted() {
    	end();
    }
}
