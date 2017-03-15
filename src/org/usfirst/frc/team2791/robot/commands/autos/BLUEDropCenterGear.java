package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 * For now, geometrically designed for BLUE SIDE
 */
public class BLUEDropCenterGear extends CommandGroup{
	
	public BLUEDropCenterGear(){
		addSequential(new FollowPath("BLUECenterGear", true, false));
		addSequential(new RemoveGear());
		addSequential(new PauseDrivetrain(.5));
		addSequential(new DriveForward());

	}
}
