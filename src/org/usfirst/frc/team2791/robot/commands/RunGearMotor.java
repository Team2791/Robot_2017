package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Runs the Gear Intake's motors
 */
public class RunGearMotor extends Command {

	private Timer timer = new Timer();
	private double runTime;
	
    public RunGearMotor(double runTime) {
    	requires(Robot.gearMechanism);
    	this.runTime = runTime;
    	
    }

    protected void initialize() {
    	timer.start();
    }

    protected void execute() {
    	Robot.gearMechanism.runGearIntake();
    }

    protected boolean isFinished() {
        return timer.hasPeriodPassed(runTime);
    }

    protected void end() {
    	Robot.gearMechanism.stopGearIntake();
    }

    protected void interrupted() {
    	end();
    }
}
