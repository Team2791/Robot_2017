package org.usfirst.frc.team2791.robot.commands.autos.traj;

import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class HopperShot extends CommandGroup{
	
	public HopperShot(Color color, Direction direction){
		addSequential(new FollowPath("FarHopper", color, Direction.REVERSE));
		addSequential(new DelayDrivetrain(.5));
		addSequential(new RunCenterAutoLongShot(15));

	}
}
