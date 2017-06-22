package org.usfirst.frc.team2791.trajectory.automodes;

import org.usfirst.frc.team2791.robot.commands.DrivetrainDelay;
import org.usfirst.frc.team2791.robot.commands.GearMechScore;
import org.usfirst.frc.team2791.trajectory.automodes.FollowPath.Color;
import org.usfirst.frc.team2791.trajectory.automodes.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class WallShot_BoilerGear extends CommandGroup{
	
	public WallShot_BoilerGear(String color){
		
//		color = color.toUpperCase();

		addSequential(new RunAutoWallShot(5.0));
		addSequential(new FollowPath(color+"_"+"BoilerWallToBoilerGear", color, "BACKWARDS"));
		addSequential(new GearMechScore());
		addSequential(new DrivetrainDelay(1));
		addSequential(new DriveForward());

	}
}
