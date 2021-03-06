package org.usfirst.frc.team2791.robot.commands.pid.automodes;

import org.usfirst.frc.team2791.robot.commands.GearMechUp;
import org.usfirst.frc.team2791.robot.commands.pid.DriveStraightEncoderGyro;
import org.usfirst.frc.team2791.robot.commands.pid.StationaryGyroTurn;
import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.commands.GearMechScore;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts at Boiler/Wall corner and Hangs the boiler side gear with PID 
 */
public class BoilerGearAuton extends CommandGroup {
	
	public BoilerGearAuton() {
		super("Boiler Side Gear");
		
		String color = Robot.teamColor.toString();

		//dist = cos(PI/6)/X - 36
		double dist = 0;
		
		if(color.equals("RED")){
			addSequential(new DriveStraightEncoderGyro(-(128-33)/12.0, .7, 10));//based on what we saw with the loading station side, this will be 18 in too high
			addSequential(new StationaryGyroTurn((-60.0/12),.7));
			dist = Math.cos(Math.PI/6)/84 - 36; //84.0 is the lateral distance

		}else{
			addSequential(new DriveStraightEncoderGyro(-(128-33)/12.0, .7, 10));//based on what we saw with the loading station side, this will be 18 in too high
			addSequential(new StationaryGyroTurn((60.0/12),.7));
			dist = Math.cos(Math.PI/6)/88 - 36; //88 is the lateral distance

		}
		
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 10));
		addSequential(new GearMechScore());
		addSequential(new DriveStraightEncoderGyro(3.0, .7, 4));


	}
}