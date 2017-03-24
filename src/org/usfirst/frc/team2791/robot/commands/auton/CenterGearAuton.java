package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.GearMechDown;
import org.usfirst.frc.team2791.robot.commands.GearMechUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterGearAuton extends CommandGroup {

	public CenterGearAuton(boolean red) {
    	addSequential(new DriveStraightEncoderGyro(-(111.25-36.0)/12, .7, 6)); //Q42, 112 in was too far
    	addSequential(new GearMechDown());
    	addSequential(new DelayDrivetrain(1.0));
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 2));
    	addSequential(new GearMechUp());


    }
}
