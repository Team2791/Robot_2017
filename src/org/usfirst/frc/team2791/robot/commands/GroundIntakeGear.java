package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Gear Intake goes down and runs, then goes up after.
 */
public class GroundIntakeGear extends Command {

	Timer timer = new Timer();
	private double startTime = -1;

	private double motorRunTime = .5;

	public GroundIntakeGear() {
		requires(Robot.gearMechanism);
	}

	protected void initialize() {
		timer.start();
		Robot.gearMechanism.changeGearSolenoidState(true); //gear does down
	}

	protected void execute() {
		Robot.gearMechanism.runGearIntake();

		if(Robot.gearMechanism.getLimitSwitchState() && Robot.gearMechanism.getState() &&  startTime != -1){ //if you have the gear and the piston is down
			System.out.println("bringing gear up");
			Robot.gearMechanism.changeGearSolenoidState(false);//bring the piston up
			startTime = timer.get();
		}
	}

	protected boolean isFinished() {
		boolean done = (motorRunTime == timer.get() - startTime);
		System.out.println(done);
		return done;
	}

	protected void end(){
		System.out.println("Ended at time:" +timer.get());
		Robot.gearMechanism.changeGearSolenoidState(false);//bring the piston u
		Robot.gearMechanism.stopGearIntake();

	}
	protected void interrupted() {
		end();
	}
}
