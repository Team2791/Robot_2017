package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Stops the Drivetrain for a given amount of time
 */
public class DelayDrivetrain extends Command {
	
	private Timer timer = new Timer();
	private double delayTime;
	
	/**
	 * @param delayTime_ the amount of time you would like to remain stationary
	 */
    public DelayDrivetrain(double delayTime_) {
    	super("DelayDrivertrain");
    	requires(Robot.drivetrain);
    	delayTime = delayTime_;
    }

    protected void initialize() {
    	timer.start();
    }

    protected void execute() {
    	System.out.println("drive train waiting.");
    	Robot.drivetrain.setLeftRightMotorOutputs(0, 0);
    }

    protected boolean isFinished() {
    	return timer.hasPeriodPassed(delayTime);
    }

    protected void end() {
    	System.out.println("Drive train Delay finished.");
    }

    protected void interrupted() {
    }
}
