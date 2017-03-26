package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.GearIntakeDown;
import org.usfirst.frc.team2791.robot.commands.GearIntakeUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts at Boiler/Wall corner and Hangs the boiler side gear with PID 
 */
public class BoilerGearAuton extends CommandGroup {
	
	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
	public BoilerGearAuton(boolean red) {

		addSequential(new DriveStraightEncoderGyro(-(127.0-33)/12.0, .7, 10));
		
		if(red){
			addSequential(new StationaryGyroTurn((-60.0/12),.7));
		}else{
			addSequential(new StationaryGyroTurn((60.0/12),.7));
		}
		double dist = Math.cos(Math.PI/6)/90.5 - 33;
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 10));
		addSequential(new GearIntakeDown());
		addSequential(new DelayDrivetrain(1.0));
		addSequential(new DriveStraightEncoderGyro(3.0, .7, 4));
		addSequential(new GearIntakeUp());


	}
}