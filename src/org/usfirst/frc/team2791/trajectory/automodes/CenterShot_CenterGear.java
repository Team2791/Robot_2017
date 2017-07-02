package org.usfirst.frc.team2791.trajectory.automodes;

import org.usfirst.frc.team2791.robot.commands.AutonGearScore;
import org.usfirst.frc.team2791.robot.commands.DrivetrainDelay;
import org.usfirst.frc.team2791.robot.commands.GearMechScore;
import org.usfirst.frc.team2791.trajectory.automodes.FollowPath.Color;
import org.usfirst.frc.team2791.trajectory.automodes.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class CenterShot_CenterGear extends CommandGroup{
	
	public CenterShot_CenterGear(String color){
		
//		color = color.toUpperCase();
		
		addSequential(new RunCenterAutoLongShot(5.0));
		System.out.println(color+"_"+"CenterShotToCenterGear");
		addSequential(new FollowPath(color+"_"+"CenterShotToCenterGear", color, "BACKWARD"));
		addSequential(new AutonGearScore());
		addSequential(new DrivetrainDelay(1));
		addSequential(new DriveForward());

	}
}
