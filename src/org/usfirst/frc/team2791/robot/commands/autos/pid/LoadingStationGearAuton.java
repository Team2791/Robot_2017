package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.commands.GearMechUp;
import org.usfirst.frc.team2791.robot.commands.ScoreGearAutoReturn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts at LoadingStation/Wall corner and Hangs the loading station side gear with PID 
 */
public class LoadingStationGearAuton extends CommandGroup {

	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
    public LoadingStationGearAuton(String color) {
    	
		System.out.println("****************Made it to LSsssssssssssss****************");

    	double dist;
    	if(color.equals("RED")){
    		System.out.println("****************Made it to LS RRRRRRRRRED****************");
        	addSequential(new DriveStraightEncoderGyro(-(111-36)/12.0, .7, 4.5)); 
        	addSequential(new StationaryGyroTurn(60.0, .9));
    		dist = 92.5/Math.cos(Math.PI/6) - 36;

    	}else{
    		System.out.println("****************Made it to LS BLUE****************");
    		addSequential(new DriveStraightEncoderGyro(-(106-33)/12.0, .7, 4.5));  //107 too far
        	addSequential(new StationaryGyroTurn(-60.0, 1));
    		dist = 91/Math.cos(Math.PI/6) - 36;

    	}
		
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 6));
    	addSequential(new AutonGearScore());
    	addSequential(new DriveStraightEncoderGyro(1.0, .7, 4));


    }
}
