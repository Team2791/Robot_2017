package org.usfirst.frc.team2791.robot.commands.autos.traj;

import org.usfirst.frc.team2791.robot.commands.ScoreGearAutoReturn;
import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class WallShot_BoilerGear extends CommandGroup{
	
	public WallShot_BoilerGear(String color){
		
//		color = color.toUpperCase();

		addSequential(new RunAutoWallShot(5.0));
		addSequential(new FollowPath(color+"_"+"BoilerWallToBoilerGear", color, "BACKWARDS"));
		addSequential(new ScoreGearAutoReturn());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
