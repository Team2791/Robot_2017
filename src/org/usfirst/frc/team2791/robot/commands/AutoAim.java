package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.autos.pid.DriveStraightVision;
import org.usfirst.frc.team2791.robot.commands.autos.pid.StationaryVisionTurn;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Vision based straightening and shooting for sweetspot shooting
 */
public class AutoAim extends CommandGroup {

	public AutoAim() {
		addSequential(new DelayDrivetrain(0.75));
		addSequential(new StationaryVisionTurn(0.5,1.5)); 
		addSequential(new DriveStraightVision(CONSTANTS.DRIVE_VISION_SWEET_SPOT_FEET, 1.0));
    }
}
