package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveEncoderBangBang extends Command {
	
	double power, turn, distanceToDrive;
	double stopDistance;

    public DriveEncoderBangBang(double power, double turn, double distance) {
    	requires(Robot.drivetrain);
    	this.power = power;
    	this.turn = turn;
    	distanceToDrive = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Starting encoder bang bang drive");
    	stopDistance = Robot.drivetrain.getAverageDist() + distanceToDrive;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.setLeftRightMotorOutputs(power + turn, power - turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(power > 0)
    		return Robot.drivetrain.getAverageDist() > stopDistance;
    	else
    		return Robot.drivetrain.getAverageDist() < stopDistance;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
