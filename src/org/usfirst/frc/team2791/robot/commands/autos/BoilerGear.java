package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.GearIntakeDown;
import org.usfirst.frc.team2791.robot.commands.auton.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class BoilerGear extends CommandGroup{
	
	public BoilerGear(Color color, Direction direction){
		addSequential(new FollowPath("BoilerGear", color, Direction.REVERSE));
		addSequential(new GearIntakeDown());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
