package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.commands.autos.EmptyFieldHopper;
import org.usfirst.frc.team2791.robot.commands.FollowPath;
import org.usfirst.frc.team2791.robot.commands.PauseDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearHopperAuto extends CommandGroup{
	
	public GearHopperAuto(){
		addSequential(new FollowPath("RightGear"));
		addParallel(new PauseDrivetrain(10.0));
		addSequential(new RemoveGear());
		addSequential(new FollowPath("RightGearToHopper"));
		addSequential(new EmptyFieldHopper()); //TODO: Finish this Command

	}
}
