package org.usfirst.frc.team2791.robot.commands.autos.traj;

import org.usfirst.frc.team2791.robot.commands.ScoreGearAutoReturn;
import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class LoadingStationGear_Midfield extends CommandGroup{
	
	public LoadingStationGear_Midfield(Color color, Direction direction){
		addSequential(new FollowPath("LoadingStationGear", color, Direction.REVERSE));
		addSequential(new ScoreGearAutoReturn());
		addSequential(new DelayDrivetrain(.5));
		addSequential(new FollowPath("GearToMidFieldPart1", color, Direction.FORWARD));
		addSequential(new DelayDrivetrain(.05));
		addSequential(new FollowPath("GearToMidFieldPart2", color, Direction.REVERSE));

	}
}