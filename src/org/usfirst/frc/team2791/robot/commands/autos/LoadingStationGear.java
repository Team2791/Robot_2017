package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.GearIntakeDown;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class LoadingStationGear extends CommandGroup{
	
	public LoadingStationGear(Color color, Direction direction){
		addSequential(new FollowPath("LoadingStationGear", color, Direction.REVERSE));
		addSequential(new GearIntakeDown());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
