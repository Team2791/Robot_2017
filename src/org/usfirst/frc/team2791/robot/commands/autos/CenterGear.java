package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class CenterGear extends CommandGroup{
	
	public CenterGear(Color color, Direction direction){
		addSequential(new FollowPath("BLUECenterGear", color, direction));
		addSequential(new RemoveGear());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
