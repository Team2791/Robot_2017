package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Wiggles the robot to empty a hopper, TOTALLY NOT DONE OR GOOD IN ANYWAY
 * @author team2791
 *
 */
public class EmptyFieldHopper extends Command {

	private double wiggleOutput=0.2;
	private double startTime;
	private double timePeriod=0.2;
	
	public EmptyFieldHopper() {
		super("DriveWithJoystick");
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		startTime=Timer.getFPGATimestamp();
	}
	@Override
	protected void execute() {
		System.out.println("Wiggle is Executing");
		if(Timer.getFPGATimestamp()-startTime < timePeriod){
			Robot.drivetrain.setLeftRightMotorOutputs(wiggleOutput, -wiggleOutput);
		}else{
			end();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.drivetrain.setLeftRightMotorOutputs(wiggleOutput,-wiggleOutput);
	}

	protected void interrupted() {
		System.out.println("Drive with joystik interrupted.");
	}

}
