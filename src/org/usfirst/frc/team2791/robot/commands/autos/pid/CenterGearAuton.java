package org.usfirst.frc.team2791.robot.commands.autos.pid;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hangs the Center Gear
 */
public class CenterGearAuton extends CommandGroup {

	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
	public CenterGearAuton(String red) {
    	addSequential(new DriveStraightEncoderGyro(-(108-36.0)/12, .7, 6)); //110.5 worked
    	addSequential(new AutonGearScore());
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 2));
    }
}
