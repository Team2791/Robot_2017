package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PauseDrivetrain extends Command {
	
	private Timer timer = new Timer();
	private double delayTime;

    public PauseDrivetrain(double delayTime_) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("DelayDrivertrain");
    	requires(Robot.drivetrain);
    	delayTime = delayTime_;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("drive train waiting.");
    	Robot.drivetrain.setLeftRightMotorOutputs(0, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.hasPeriodPassed(delayTime);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Drive train Delay finished.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
