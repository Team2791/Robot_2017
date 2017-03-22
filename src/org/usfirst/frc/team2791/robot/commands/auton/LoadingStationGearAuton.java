package org.usfirst.frc.team2791.robot.commands.auton;

import org.usfirst.frc.team2791.robot.commands.DelayDrivetrain;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.ResetGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LoadingStationGearAuton extends CommandGroup {

	public LoadingStationGearAuton(boolean red) {

		if(red){
			// was 127.5 qm52
			// was 123.5 qm57, hit right outside gear between teeth
			addSequential(new DriveStraightEncoderGyro(-(119.5-36)/12.0, .7, 5));//was 129.5 at TVR Q48
			addSequential(new StationaryGyroTurn(60.0, 1));
		}else{
			// 128.5, qm52 - too much
			// decrease to 120.5 -- see qm57
			addSequential(new DriveStraightEncoderGyro(-(120.5-33-4)/12.0, .7, 5)); //130.5 was too much TVR Q48
			addSequential(new StationaryGyroTurn(-60.0, 1));
		}
		
		// last 3in from qm 57
		double dist = 88.5/Math.cos(Math.PI/6) - 36 - /* offset */ 3-3;

		// prev timeout: 6 qm52
		//				 4 qm57
		addSequential(new DriveStraightEncoderGyro(-dist/12.0, .7, 2.5));
		addSequential(new RemoveGear());
		addSequential(new DelayDrivetrain(1.0));
		addSequential(new DriveStraightEncoderGyro(1.0, .7, 4));
		addSequential(new ResetGear());
//		if(red) {
//			
//		} else {
//			addSequential(new StationaryGyroTurn(60.0, 1));
//			addSequential(new DriveStraightEncoderGyro(15, 1, 5));
//		}
	}
}
