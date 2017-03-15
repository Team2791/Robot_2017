package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.ResetGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to loading station
 * For now, geometrically designed for BLUE SIDE
 */
public class BLUELeftGearLoadingStation extends CommandGroup{
	
	public BLUELeftGearLoadingStation(){
		addSequential(new FollowPath("BLUELeftGear", true, false));
		addSequential(new RemoveGear());
		addSequential(new PauseDrivetrain(1));
		addSequential(new FollowPath("BLUELeftGearToLeftWall", false, true));
		
		addSequential(new ResetGear());
		
		addSequential(new FollowPath("BLUELeftWallToLoadingStation", true, true));//it works but needs to go further
	}
}
