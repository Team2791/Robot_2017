package org.usfirst.frc.team2791.robot.commands.pid.automodes;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.commands.AutonGearScore;
import org.usfirst.frc.team2791.robot.commands.pid.DriveStraightEncoderGyro;
import org.usfirst.frc.team2791.robot.commands.pid.StationaryGyroTurn;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts at LoadingStation/Wall corner and Hangs the loading station side gear with PID 
 */
public class LoadingStationGearAuton extends CommandGroup {

    public LoadingStationGearAuton() {
    	super("LoadingStation Gear");
    	
    	String color = Robot.teamColor.toString();
    	
    	Timer t = new Timer();
    	t.start();
    	
    	//dist = X/cos(PI/6) - 36
    	double dist;
    	double errorThreshold = 2.25/12.0;
 
    	if(color.equals("RED")){
        	addSequential(new DriveStraightEncoderGyro(-(109.5-36)/12.0, .7, 3.5, errorThreshold)); 
//    		addSequential(new DriveStraightEncoderGyro(-6.0 ,.7, 3.3));
        	addSequential(new StationaryGyroTurn(60.0, 1, 2));
    		dist = 90/Math.cos(Math.PI/6) - 36;
    	}else{
    		addSequential(new DriveStraightEncoderGyro(-(109.5-36)/12.0, .7, 3.5, errorThreshold)); 
        	addSequential(new StationaryGyroTurn(-60.0, 1, 2));
    		dist = 90/Math.cos(Math.PI/6) - 36;
    	}
    	
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 2.5, errorThreshold));
    	addSequential(new AutonGearScore());
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 2.0));
    	
    	//begin driving to other side of the field backwards
    	if(color.equals("RED")){
    		addSequential(new StationaryGyroTurn(-60.0, 1.0, 10, 2.0));
    	}else{
    		addSequential(new StationaryGyroTurn(60, 1.0, 10, 2.0));
    	}
    	
    	addSequential(new DriveStraightEncoderGyro(-17.0, .0005, 15.0));
    	
    }
}
