package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 * Geometrically designed for BLUE SIDE
 */
public class BLUELeftGearLeftHopper extends CommandGroup{
	
	public BLUELeftGearLeftHopper(){
		addSequential(new FollowPath("BLUELeftGear", true, false));
		addSequential(new RemoveGear());
		addSequential(new PauseDrivetrain(1));
		addSequential(new FollowPath("BLUELeftGearToLeftHopper", false, false));

	}
}
