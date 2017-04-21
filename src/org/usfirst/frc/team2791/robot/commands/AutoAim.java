package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.commands.autos.pid.DriveStraightVision;
import org.usfirst.frc.team2791.robot.commands.autos.pid.StationaryVisionTurn;
import org.usfirst.frc.team2791.robot.commands.autos.pid.DelayDrivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Combined command for vision based straightening and shooting
 */
public class AutoAim extends CommandGroup {

	public AutoAim() {
		addSequential(new DelayDrivetrain(0.5));
		addSequential(new StationaryVisionTurn(0.5,1.5));
		addSequential(new DriveStraightVision());
    }
}
