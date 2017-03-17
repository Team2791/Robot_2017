package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.ResetGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterGearAuton extends CommandGroup {

	public CenterGearAuton(boolean red) {
    	addSequential(new DriveStraightEncoderGyro(-(106.5-36)/12, .7, 10)); 
    	addSequential(new RemoveGear());
    	addSequential(new DelayDrivetrain(1.0));
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 4));
    	addSequential(new ResetGear());


    }
}
