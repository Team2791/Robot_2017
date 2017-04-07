package org.usfirst.frc.team2791.robot.commands.autos.traj;

import org.usfirst.frc.team2791.robot.commands.ScoreGearAutoReturn;
import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class CenterShot_CenterGear extends CommandGroup{
	
	public CenterShot_CenterGear(String color){
		
		addSequential(new RunCenterAutoLongShot(5.0));
		addSequential(new FollowPath("CenterShotToCenterGear", color, "BACKWARDS"));
		addSequential(new ScoreGearAutoReturn());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}