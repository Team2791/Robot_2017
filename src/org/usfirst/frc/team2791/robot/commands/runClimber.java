package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.OI;
import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class runClimber extends Command{
	public runClimber() {
		super("runClimber");
		requires(Robot.intake);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of climber");
		initialize();
	}

	protected void initialize() {
		Robot.intake.wingDeployment();
		System.out.println("came to initialize of climber");
		execute();
	}

	protected void execute() {
		if ((Robot.intake.getClimberCurrent()<30.0) && !(OI.operator.getButtonB())) {
			System.out.println("I'm trying to execute climbing\tClimber Current: "+Robot.intake.getClimberCurrent());
			Robot.intake.motorOnClimber();
		}
		else
			Robot.intake.stopClimber();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.intake.stopClimber();
	}

	protected void interrupted() {
	}
}
