package org.usfirst.frc.team2791.robot.commands.pid;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns a set angle based on Encoders, Gyro, and power outputs without using PID.
 * Faster, but less accurate than PID
 */
public class TurnGyroBangBang extends Command {

	double turn, amountToTurn;
	double stopAngle;
	double timeToTurn = 2791;

	protected Timer timer = new Timer();

	/**
	 * @param turn amount of power you want in the turn
	 * @param angle degrees that you want to turn
	 * @param timeOut seconds before you want to time out
	 */
	public TurnGyroBangBang(double turn, double angle, double timeOut) {
		this(turn, angle);
//		System.out.println("IIIIIIII AMMMMMMMMMMMMMM TURNINGGGGGGGGGGGGGGGGGG");

		timeToTurn = timeOut;
	}

	/**
	 * Time Out is defaulted to 2791 seconds
	 * @param turn amount of power you want in the turn
	 * @param angle degrees that you want to turn  
	 */
	public TurnGyroBangBang(double turn, double angle) {
		super("TurnGyro w/ BangBang");
		requires(Robot.drivetrain);
		this.turn = turn;
		amountToTurn = angle;
	}

	protected void initialize() {
		timer.start();
		System.out.println("Starting gyro bang bang turn");
		stopAngle = Robot.drivetrain.getGyroAngle() + amountToTurn;
	}

	protected void execute() {
		Robot.drivetrain.setLeftRightMotorOutputs(turn, -turn);
	}

	protected boolean isFinished() {
		if(timer.get() > timeToTurn){
			return true;
		}

		if(turn > 0)
			return Robot.drivetrain.getGyroAngle() > stopAngle;
		else
			return Robot.drivetrain.getGyroAngle() < stopAngle;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
