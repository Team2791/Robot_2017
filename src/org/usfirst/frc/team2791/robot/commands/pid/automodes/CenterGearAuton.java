package org.usfirst.frc.team2791.robot.commands.pid.automodes;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.commands.AutonGearScore;
import org.usfirst.frc.team2791.robot.commands.pid.DriveStraightEncoderGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hangs the Center Gear
 */
public class CenterGearAuton extends CommandGroup {

	public CenterGearAuton() {
		super("Center Gear");
		String color = Robot.teamColor.toString();
		
    	addSequential(new DriveStraightEncoderGyro(-(108-36.0)/12, .7, 6)); //110.5 worked
    	addSequential(new AutonGearScore());
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 2));
    }
}
