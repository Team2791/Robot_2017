package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BLeftGearAuton extends CommandGroup {

    public BLeftGearAuton() {
    	addSequential(new DriveStraightEncoderGyro(-92.5/12.0, .7));
    	addSequential(new StationaryGyroTurn(-55.0, 1));
    	addSequential(new DriveStraightEncoderGyro(-54.0/12.0, .7));
    	addSequential(new RemoveGear());
    	addSequential(new DelayDrivetrain(1.0));
    	addSequential(new DriveStraightEncoderGyro(3.0, .7));


    }
}
