package org.usfirst.frc.team2791.robot.commands.autos.traj;

import org.usfirst.frc.team2791.robot.commands.GearMechDown;
import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class CenterGear extends CommandGroup{
	
	public CenterGear(Color color){
		addSequential(new FollowPath("CenterGear", color, Direction.FORWARD));
		addSequential(new GearMechDown());
		addSequential(new DelayDrivetrain(1));
//		addSequential(new DriveForward());
//		addSequential(new StationaryGyroTurn((-90),.7));
//		addSequential(new RunAutoLongShot(10));

	}
}