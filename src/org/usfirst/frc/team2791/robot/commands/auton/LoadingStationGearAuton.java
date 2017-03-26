package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.GearIntakeDown;
import org.usfirst.frc.team2791.robot.commands.GearIntakeUp;
import org.usfirst.frc.team2791.robot.util.BasicPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts at LoadingStation/Wall corner and Hangs the loading station side gear with PID 
 */
public class LoadingStationGearAuton extends CommandGroup {

	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
    public LoadingStationGearAuton(boolean red) {
    	
    	if(red){
        	addSequential(new DriveStraightEncoderGyro(-(127.5-36)/12.0, .7, 6));//was 129.5 at TVR Q48, wasn't run
        	addSequential(new StationaryGyroTurn(60.0, 1));
    	}else{
    		addSequential(new DriveStraightEncoderGyro(-(128.5-33)/12.0, .7, 6)); //130.5 was too much TVR Q48
        	addSequential(new StationaryGyroTurn(-60.0, 1));
    	}
    	
		double dist = 88.5/Math.cos(Math.PI/6) - 36;
		
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 6));
    	addSequential(new GearIntakeDown());
    	addSequential(new DelayDrivetrain(1.0));
    	addSequential(new DriveStraightEncoderGyro(1.0, .7, 4));
    	addSequential(new GearIntakeUp());


    }
}
