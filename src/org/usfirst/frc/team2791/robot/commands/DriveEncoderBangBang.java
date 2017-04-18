package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveEncoderBangBang extends Command {
	
	double power, turn, distanceToDrive, timeToDrive;
	double stopDistance;
	
	protected Timer timer = new Timer();
	
    public DriveEncoderBangBang(double power, double turn, double distance, double timeOut) {
    	this(power, turn, distance);
    	timeToDrive = timeOut;
    }

    /**
     * Defaults the time out to 5.0 seconds
     * @param power
     * @param turn
     * @param distance
     */
    public DriveEncoderBangBang(double power, double turn, double distance) {
    	requires(Robot.drivetrain);
    	this.power = power;
    	this.turn = turn;
    	distanceToDrive = distance;
    	
    	timeToDrive = 5.0;
    }

    protected void initialize() {
    	timer.start();
    	System.out.println("Starting encoder bang bang drive");
    	stopDistance = Robot.drivetrain.getAverageDist() + distanceToDrive;
    }

    protected void execute() {
    	Robot.drivetrain.setLeftRightMotorOutputs(power + turn, power - turn);
    }

    protected boolean isFinished() {
    	if(timer.get() > timeToDrive)
    		return true;
    	
    	if(power > 0)
    		return Robot.drivetrain.getAverageDist() > stopDistance;
    	else
    		return Robot.drivetrain.getAverageDist() < stopDistance;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
