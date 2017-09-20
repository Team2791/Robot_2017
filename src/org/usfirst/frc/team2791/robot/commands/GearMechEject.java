package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Puts the {@link ShakerGear Gear Intake} up and runs the motors for .5 secs
 *
 *This class was originally named GearPoop and that fact must be known
 */
public class GearMechEject extends Command {

	private Timer t = new Timer();
	
    public GearMechEject() {
    	super("GearMechUp");
    	requires(Robot.gearMechanism);
    	t.start();
    }

    protected void initialize() {
    	Robot.gearMechanism.setGearIntakeDown(true);
    }

    protected void execute() {
    	if(t.get() > 0.5)
    		Robot.gearMechanism.runGearOutake();
    }

    protected boolean isFinished() {
        return t.get() > 1.2;
    }

    protected void end() {
    	Robot.gearMechanism.stopGearIntake();
    	Robot.gearMechanism.setGearIntakeDown(false);
    }

    protected void interrupted() {
    	Robot.gearMechanism.stopGearIntake();
    	end();   
    }
}
