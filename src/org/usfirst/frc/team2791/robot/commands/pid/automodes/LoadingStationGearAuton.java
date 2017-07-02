package org.usfirst.frc.team2791.robot.commands.pid.automodes;

import org.usfirst.frc.team2791.robot.commands.AutonGearScore;
import org.usfirst.frc.team2791.robot.commands.pid.DriveStraightEncoderGyro;
import org.usfirst.frc.team2791.robot.commands.pid.StationaryGyroTurn;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts at LoadingStation/Wall corner and Hangs the loading station side gear with PID 
 */
public class LoadingStationGearAuton extends CommandGroup {

	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
    public LoadingStationGearAuton(String color) {
    	super("LoadingStation Gear");
    	
    	Timer t = new Timer();
    	t.start();
    	
    	//dist = X/cos(PI/6) - 36
    	double dist;
 
    	if(color.equals("RED")){
        	addSequential(new DriveStraightEncoderGyro(-(110-36)/12.0, .7, 3.3)); //128 was 1/2 a robot width off, the robot is 36 inches wide
        	addSequential(new StationaryGyroTurn(60.0, 1));
    		dist = 80/Math.cos(Math.PI/6) - 36;
    	}else{
    		addSequential(new DriveStraightEncoderGyro(-(110-36)/12.0, .7, 3.3)); //128 was 1/2 a robot width off, the robot is 36 inches wide
        	addSequential(new StationaryGyroTurn(-60.0, 1));
    		dist = 80.5/Math.cos(Math.PI/6) - 36;
    	}
		
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 2.5));
    	addSequential(new AutonGearScore());
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 2.0));
    	
    	//begin driving to other side of the field backwards
    	if(color.equals("RED")){
    		addSequential(new StationaryGyroTurn(-60.0, 1.0, 10, 2.0));
    	}else{
    		addSequential(new StationaryGyroTurn(60, 1.0, 10, 2.0));
    	}
    	
    	addSequential(new DriveStraightEncoderGyro(-25.0, .05, 15.0));
    	
    }
}
