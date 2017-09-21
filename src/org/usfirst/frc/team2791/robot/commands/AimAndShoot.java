package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.commands.pid.StationaryVisionTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Centers the shooter to the target and shoots w/ a vision-determined RPM
 */
public class AimAndShoot extends CommandGroup {

    public AimAndShoot() {
    	setInterruptible(true);
		addSequential(new DrivetrainDelay(0.75));
		addSequential(new StationaryVisionTurn(0.5,1.5)); 
		addSequential(new RunVisionShot());
    }
}
