package org.usfirst.frc.team2791.robot.commands.autos.traj;

import org.usfirst.frc.team2791.robot.commands.ScoreGearAutoReturn;
import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class LoadingStationGear extends CommandGroup{
	
	public LoadingStationGear(Color color){
		addSequential(new FollowPath("LoadingStationGear", color, Direction.REVERSE));
		addSequential(new ScoreGearAutoReturn());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
