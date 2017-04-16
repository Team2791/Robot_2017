package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnGyroBangBang extends Command {
	
	double turn, amountToTurn;
	double stopAngle;

    public TurnGyroBangBang(double turn, double angle) {
    	requires(Robot.drivetrain);
    	this.turn = turn;
    	amountToTurn = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Starting gyro bang bang turn");
    	stopAngle = Robot.drivetrain.getGyroAngle() + amountToTurn;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.setLeftRightMotorOutputs(turn, -turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(turn > 0)
    		return Robot.drivetrain.getGyroAngle() > stopAngle;
    	else
    		return Robot.drivetrain.getGyroAngle() < stopAngle;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
