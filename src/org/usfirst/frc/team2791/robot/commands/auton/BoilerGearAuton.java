package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.ResetGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by team2791 on 3/15/2016.
 */
public class BoilerGearAuton extends CommandGroup {

	public BoilerGearAuton(boolean red) {

		addSequential(new DriveStraightEncoderGyro(-(128-36)/12, .7, 10));
		
		if(red){
			addSequential(new StationaryGyroTurn((-60.0/12),.7));
		}else{
			addSequential(new StationaryGyroTurn((60.0/12),.7));
		}
		double dist = Math.cos(Math.PI/6)/90.5 - 36;
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 10));
		addSequential(new RemoveGear());
		addSequential(new DelayDrivetrain(1.0));
		addSequential(new DriveStraightEncoderGyro(3.0, .7, 4));
		addSequential(new ResetGear());


	}
}