package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Spin up the shooter the a desired rpm and leave the shooter spinning at that speed. Does NOT activate the hopper or start shooting.
 * </br>This Command will end when the shooter reaches the desired speed (but the shooter does not stop running afterwards).

 */
public class SpinUpShooter extends Command {

	private double speed;
	/**
	 * 
	 * @param speed the desired speed in RPM.
	 */
    public SpinUpShooter(double speed) {
    	super("SpinUpShooter");
        requires(Robot.shooter);
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.prepCustomShot(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.shooter.atSpeed();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
