package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class removeGear extends Command{
	public removeGear() {
		super("removeGear");
		requires(Robot.gearMechanism);// Use requires() here to declare subsystem dependencies
		System.out.println("came to constructor of gear");
		initialize();
	}

	protected void initialize() {
		System.out.println("came to initialize of gear");
		execute();
	}

	protected void execute() {
		System.out.println("I'm trying to execute gear removal");
		Robot.gearMechanism.changeGearSolenoidState(true);
	}
	
	protected boolean isFinished() {
		return false;
	}

	protected void end(){	
	}

	protected void interrupted(){
	}
}
