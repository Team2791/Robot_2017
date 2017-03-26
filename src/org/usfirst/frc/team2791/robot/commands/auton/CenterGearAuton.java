package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.GearIntakeUp;
import org.usfirst.frc.team2791.robot.commands.ScoreGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hangs the Center Gear
 */
public class CenterGearAuton extends CommandGroup {

	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
	public CenterGearAuton(boolean red) {
    	addSequential(new DriveStraightEncoderGyro(-(111.25-36.0)/12, .7, 6)); //Q42, 112 in was too far
    	addSequential(new ScoreGear());
    	addSequential(new DelayDrivetrain(1.0));
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 2));
    	addSequential(new GearIntakeUp());


    }
}
