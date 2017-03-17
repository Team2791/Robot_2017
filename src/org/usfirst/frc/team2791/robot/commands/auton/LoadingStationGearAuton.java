package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LoadingStationGearAuton extends CommandGroup {

    public LoadingStationGearAuton() {
    	
    	addSequential(new DriveStraightEncoderGyro(106/12.0, .7));
    	addSequential(new StationaryGyroTurn(-60.0, 1));
    	addSequential(new DriveStraightEncoderGyro(-6/12.0, .7));
    	addSequential(new RemoveGear());
    	addSequential(new DelayDrivetrain(1.0));
    	addSequential(new DriveStraightEncoderGyro(1, .7));


    }
}
