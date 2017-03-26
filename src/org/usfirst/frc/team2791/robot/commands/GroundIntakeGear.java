package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Gear Intake goes down and runs, then goes up after.
 */
public class GroundIntakeGear extends Command {
	
	Timer timer = new Timer();
	private double startTime;
	
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
    	
    	if(Robot.gearMechanism.getLimitSwitchState() && Robot.gearMechanism.getState()){ //if you have the gear and the piston is down
    		Robot.gearMechanism.changeGearSolenoidState(false);//bring the piston up
    		startTime = timer.get();
    	}
    }

    protected boolean isFinished() {	
    	return (motorRunTime == timer.get() - startTime);
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
