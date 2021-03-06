package org.usfirst.frc.team2791.trajectory.automodes;

import org.usfirst.frc.team2791.robot.commands.AutonGearScore;
import org.usfirst.frc.team2791.robot.commands.DrivetrainDelay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class CenterShot_CenterGear extends CommandGroup{
	
	public CenterShot_CenterGear(String color){
		
//		color = color.toUpperCase();
		super("CenterShot_CenterGear");
		addSequential(new RunCenterAutoLongShot(5.0));
		addSequential(new FollowPath(color+"_"+"CenterShotToCenterGear", color, "BACKWARD"));
		addSequential(new AutonGearScore());
		addSequential(new DrivetrainDelay(1));
		addSequential(new DriveForward());

	}
}
