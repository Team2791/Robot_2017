package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class HopperShot extends CommandGroup{
	
	public HopperShot(Color color, Direction direction){
		addSequential(new FollowPath("FarHopper", color, Direction.REVERSE));
		addSequential(new DelayDrivetrain(.5));
		addSequential(new RunAutoLongShot(15));

	}
}
