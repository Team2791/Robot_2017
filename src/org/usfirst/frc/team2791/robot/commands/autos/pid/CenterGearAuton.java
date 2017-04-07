package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.commands.GearMechUp;
import org.usfirst.frc.team2791.robot.commands.ScoreGearAutoReturn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hangs the Center Gear
 */
public class CenterGearAuton extends CommandGroup {

	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
	public CenterGearAuton(String red) {
    	addSequential(new DriveStraightEncoderGyro(-(109.5-36.0)/12, .7, 6));
    	addSequential(new ScoreGearAutoReturn());
    	addSequential(new DelayDrivetrain(1.0));
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 2));
    	addSequential(new GearMechUp());


    }
}
