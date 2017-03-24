package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.GearIntakeDown;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class Shoot10_BoilerGear extends CommandGroup{
	
	public Shoot10_BoilerGear(Color color, Direction direction){
		addSequential(new RunAutoWallShot(5.0));
		addSequential(new FollowPath("CloseShotToBoilerGear", color, Direction.REVERSE));
		addSequential(new GearIntakeDown());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
