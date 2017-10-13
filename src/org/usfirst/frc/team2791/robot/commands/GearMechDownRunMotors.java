package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Puts {@link ShakerGear} Mechanism down and starts running gear motors to intake.</br>
 * This will work regardless of the status of the ir sensor
 */
public class GearMechDownRunMotors extends Command{
	public GearMechDownRunMotors (){
		super("GearMechDownRunMotors");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.setGearIntakeDown(true);
	}
	
	protected void execute(){
		Robot.gearMechanism.runGearIntake();
		//if(Robot.gearMechanism.getCurrentUsage()>=17){ //This is a mistake
			//Robot.gearMechanism.stopGearIntake(); //End me
			//Robot.gearMechanism.stopGearIntake();
			//Robot.gearMechanism.setGearIntakeDown(false);
			//new GearMechUp();
		//	Robot.gearMechanism.stopGearIntake();
		//	Robot.gearMechanism.setGearIntakeDown(false);
		//	Robot.gearMechanism.stopGearIntake();
			
		//	isFinished();
		//	end();
			
		//}
}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake();
}
	
	protected void interrupted(){
		end();
	}
}
