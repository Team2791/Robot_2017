package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.commands.RunHopper;
import org.usfirst.frc.team2791.robot.commands.RunIntake;
import org.usfirst.frc.team2791.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2791.robot.commands.TurnGyroBangBang;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hangs the Center Gear
 */
public class CenterGearAutonShooting extends CommandGroup {
	double direction = 1.0;
	/**
	 * @param color the team that you are on ("RED" or "BLUE")
	 */
	public CenterGearAutonShooting(String color) {
		super("Center Gear & Shoot");
				
		if(color.equals("RED"))
			direction = -1.0;
		else
			direction = 1.0;
		
		addParallel(new SpinUpShooter(CONSTANTS.SHOOTER_AUTO_CENTER_SET_POINT));
		
		// 110 was short
    	addSequential(new DriveStraightEncoderGyro(-(112.5-36.0)/12, .7, 2.5, 3.0)); //108 worked
    	addSequential(new AutonGearScore());
    	addParallel(new RunIntake());
    	
    	addSequential(new DriveStraightEncoderGyro(2.0, .7, 1.0, 3.0));//distance was 3.0
    	
		addSequential(new TurnGyroBangBang(0.45 * direction , 62.0 * direction)); //65.0
		addSequential(new DriveEncoderBangBang(0.7, 0.0, 1.5, 1.0)); //1.5, 1.0 was just a bit short
				
		addSequential(new DelayDrivetrain(1.25));
		addSequential(new StationaryVisionTurn(0.5,1.5));//0.4,1.0
		addSequential(new RunHopper());
    }
}
