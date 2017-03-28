package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Gear Intake goes down and runs, then goes up after.
 */
public class GroundIntakeGear extends Command {

	Timer timer = new Timer(); //must start before isFinished is called

	/**
	 * this is -1.0 at the beginning so we can tell if we have started timing already
	 */
	private double startTime = -1.0;
	private double motorRunTime = .5;

	/**
	 * checks if we have the gear and are ready to lift the gear into the bot
	 */
	private boolean readyForLiftoff = false;

	public GroundIntakeGear() {
		requires(Robot.gearMechanism);
	}

	protected void initialize() {
		timer.start();
		Robot.gearMechanism.changeGearSolenoidState(true); //gear does down
	}

	protected void execute() {
		Robot.gearMechanism.runGearIntake();
		
		if(readyForLiftoff){
			System.out.println("bringing gear up");
			Robot.gearMechanism.changeGearSolenoidState(false);//bring the piston up

			startTime = timer.get();
		}
		
		readyForLiftoff = Robot.gearMechanism.getLimitSwitchState() &&  //checks if has gear
				Robot.gearMechanism.isDown() && //checks if gear mechanism is down
				(startTime == -1.0);  //makes sure we set startTime only once
	}

	protected boolean isFinished() {
		boolean done = (motorRunTime == timer.get() - startTime);
		System.out.println(done);
		return done;
	}

	protected void end(){
		System.out.println("Ended at time:" +timer.get());
		Robot.gearMechanism.changeGearSolenoidState(false);//bring the piston up
		Robot.gearMechanism.stopGearIntake();
		timer.reset();

	}
	
	protected void interrupted() {
		Robot.gearMechanism.runGearIntake();
		end();
	}
}
