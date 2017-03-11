package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import org.usfirst.frc.team2791.robot.commands.autos.DriveForward;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 * For now, geometrically designed for BLUE SIDE
 */
public class DriveForward extends CommandGroup{
	
	public DriveForward(){
		addSequential(new FollowPath("BLUELeftGear", true));
		addSequential(new RemoveGear());
		addSequential(new DriveForward());

	}
}
