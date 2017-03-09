package org.usfirst.frc.team2791.robot.commands.autos;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.commands.PauseDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start at Hopper Corner, Run to Right Gear, RemoveGear, Go to and Empty Hopper
 * For now, geometrically designed for BLUE SIDE
 */
public class GearHopperAuto extends CommandGroup{
	
	public GearHopperAuto(){
		addSequential(new FollowPath("BLUELeftGear", true));
		addSequential(new RemoveGear());
//		addSequential(new PauseDrivetrain(0.5));
		System.out.println("The auto got past RemoveGear");
	    Robot.drivetrain.setLeftRightMotorOutputs(1.0, 1.0);

	}
}
