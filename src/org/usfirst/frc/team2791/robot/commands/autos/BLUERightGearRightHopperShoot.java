package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.RunWallShot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 * For now, geometrically designed for BLUE SIDE
 */
public class BLUERightGearRightHopperShoot extends CommandGroup{
	
	public BLUERightGearRightHopperShoot(){
		addSequential(new FollowPath("BLUEBoilerToRightGear", true, false));
		addSequential(new RemoveGear());
		addSequential(new FollowPath("BLUERightGearToRightHopper", true, false));
		addSequential(new RunWallShot(10.0));


		
		

	}
}
