package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.commands.GearMechUp;
import org.usfirst.frc.team2791.robot.commands.ScoreGearAutoReturn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts at Boiler/Wall corner and Hangs the boiler side gear with PID 
 */
public class BoilerGearAuton extends CommandGroup {
	
	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
	public BoilerGearAuton(String red) {

		double dist = 0;
		if(red.equals("RED")){
			addSequential(new DriveStraightEncoderGyro(-(121.0-33)/12.0, .7, 10));
			addSequential(new StationaryGyroTurn((-60.0/12),.7));
			dist = Math.cos(Math.PI/6)/91.0 - 33; //91.0 is the lateral distance

		}else{
			addSequential(new DriveStraightEncoderGyro(-(124.0-33)/12.0, .7, 10));
			addSequential(new StationaryGyroTurn((60.0/12),.7));
			dist = Math.cos(Math.PI/6)/89.5 - 33; //89.5 is the lateral distance

		}
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 10));
		addSequential(new ScoreGearAutoReturn());
		addSequential(new DriveStraightEncoderGyro(3.0, .7, 4));


	}
}