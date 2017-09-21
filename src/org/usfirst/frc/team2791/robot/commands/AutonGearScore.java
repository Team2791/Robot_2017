package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts the {@link ShakerGear} Mechanism down and stops the gear motors from running.</br>
 * The {@link ShakerGear} Mech will come back up after 1.5 secods</br>
 * This will work regardless of the status of the ir sensors.</br>
 * This Command has a timed ending (0.75 seeconds after init)
 */
public class AutonGearScore extends Command{
	Timer t = new Timer();
	public AutonGearScore (){
		super("ScoreGear");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		t.reset();
		t.start();

		Robot.gearMechanism.stopGearIntake();
		Robot.gearMechanism.setGearIntakeDown(true);
	}
	
	protected void execute(){
		Robot.gearMechanism.stopGearIntake(); 
	}
	
	protected boolean isFinished(){
		return t.get()>0.75; //here is where the delay is
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake();
	}
	
	protected void interrupted(){
		end();
	}
}
