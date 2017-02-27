package org.usfirst.frc.team2791.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team2791.robot.commands.EmptyFieldHopper;
import org.usfirst.frc.team2791.robot.commands.FollowPath;
import org.usfirst.frc.team2791.robot.commands.PauseDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.trajectory.AutoPaths;
public class GearHopperFarShotAuto extends CommandGroup {
	
	public GearHopperFarShotAuto(){
		addSequential(new FollowPath(AutoPaths.get("WallToRightGear"))); //TODO: Make this path
		addParallel(new PauseDrivetrain(10.0));
		addSequential(new RemoveGear());
		addSequential(new FollowPath(AutoPaths.get("RightGearToRightHopper")));  //TODO: Make this path
		addSequential(new EmptyFieldHopper()); //TODO: Finish this Command
	}

}
