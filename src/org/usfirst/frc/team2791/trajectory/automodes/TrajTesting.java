package org.usfirst.frc.team2791.trajectory.automodes;

import org.usfirst.frc.team2791.robot.commands.DrivetrainDelay;
import org.usfirst.frc.team2791.robot.commands.GearMechDown;
import org.usfirst.frc.team2791.trajectory.automodes.FollowPath.Color;
import org.usfirst.frc.team2791.trajectory.automodes.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 */
public class TrajTesting extends CommandGroup{
	
	public TrajTesting(String color){
		addSequential(new RunCenterAutoLongShot(5.0));
		addSequential(new FollowPath("TestingOneTwo", "RED", "BACKWARDS"));
//		addSequential(new GearMechDown());
		addSequential(new DrivetrainDelay(1));
		addSequential(new DriveForward());
//		addSequential(new StationaryGyroTurn((-90),.7));
//		addSequential(new RunAutoLongShot(10));

	}
}
