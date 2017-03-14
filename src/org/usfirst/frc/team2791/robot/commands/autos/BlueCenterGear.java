package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.DelayDrivertrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 * For now, geometrically designed for BLUE SIDE
 */
public class BlueCenterGear extends CommandGroup{
	
	public BlueCenterGear(){
		addSequential(new FollowPath("BLUECenterGear", true, false));
		addSequential(new RemoveGear());
		addSequential(new DelayDrivertrain(1));
		addSequential(new DriveForward());

	}
}
