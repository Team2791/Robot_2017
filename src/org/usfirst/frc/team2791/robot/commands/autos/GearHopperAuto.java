package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 * For now, geometrically designed for BLUE SIDE
 */
public class GearHopperAuto extends CommandGroup{
	
	public GearHopperAuto(){
		addSequential(new FollowPath("BLUELeftGear", true, false));
		addSequential(new RemoveGear());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
