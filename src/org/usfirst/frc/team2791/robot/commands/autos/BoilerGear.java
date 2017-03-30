package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.ScoreGear;
import org.usfirst.frc.team2791.robot.commands.auton.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.FollowPath.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class BoilerGear extends CommandGroup{
	
	public BoilerGear(Color color){
		addSequential(new FollowPath("BoilerGear", color, Direction.REVERSE));
		addSequential(new ScoreGear());
		addSequential(new DelayDrivetrain(1));
		addSequential(new DriveForward());

	}
}
