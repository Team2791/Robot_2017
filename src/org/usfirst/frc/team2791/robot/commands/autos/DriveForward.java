package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

	private double POWER_VOUT = .5;
	private Timer timer = new Timer();
	
    public DriveForward() {
    	super("DriveForward");
        requires(Robot.drivetrain);
    }

    protected void initialize() {
    	System.out.println("Starting to drive forward.");
    	timer.start();
	    Robot.drivetrain.setLeftRightMotorOutputs(POWER_VOUT, POWER_VOUT);
    }

    protected void execute() {
    	System.out.println("I AM GOING TO DRIVE BACKWARDS!!!");
	    Robot.drivetrain.setLeftRightMotorOutputs(POWER_VOUT, POWER_VOUT);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.hasPeriodPassed(1.0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.reset();
    	Robot.drivetrain.setLeftRightMotorOutputs(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	timer.reset();
    	execute();
    }
}
