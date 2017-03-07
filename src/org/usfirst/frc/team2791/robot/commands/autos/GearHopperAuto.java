package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.autos.EmptyFieldHopper;
import org.usfirst.frc.team2791.robot.commands.FollowPath;
import org.usfirst.frc.team2791.robot.commands.PauseDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Wall, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 */
public class GearHopperAuto extends CommandGroup{
	
	public GearHopperAuto(){
		addSequential(new FollowPath("RightGear", true));
		addParallel(new PauseDrivetrain(0.5));
		addSequential(new RemoveGear());
		addSequential(new FollowPath("RightGearToHopper", true));
		addSequential(new EmptyFieldHopper()); //TODO: Finish this Command

	}
}
