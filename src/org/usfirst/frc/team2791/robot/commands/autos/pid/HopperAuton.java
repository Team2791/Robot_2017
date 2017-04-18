package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.commands.DriveEncoderBangBang;
import org.usfirst.frc.team2791.robot.commands.RunHopper;
import org.usfirst.frc.team2791.robot.commands.RunIntake;
import org.usfirst.frc.team2791.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2791.robot.commands.TurnGyroBangBang;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hangs the Center Gear
 */
public class HopperAuton extends CommandGroup {

	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
	public HopperAuton() {
		addParallel(new SpinUpShooter(CONSTANTS.SHOOTER_AUTO_SET_POINT)); // 3035 //3050
		addSequential(new DriveEncoderBangBang(-.8, 0.0, -3.5));
		// This is sketch as heck because we're counting on the drift from the first drive
		// to get us to the 2nd distance and just using the drive train to slow down and turn a bit.
//		addSequential(new DriveEncoderBangBang(0.0, 0.075, -1));
		addSequential(new DriveEncoderBangBang(-0.3, 0.0, -3.5, 2.5)); //-3.75
		addParallel (new RunIntake());
		
		//kick the hopper to ensure the hopper is triggered
		addSequential(new TurnGyroBangBang(-0.7, -15, 2.0));
		addSequential(new TurnGyroBangBang(0.7, 20, 2.0));
		
		//drive forward to get moar ballz
		addSequential(new DriveEncoderBangBang(0.5, 0.0, 1, 1.0)); //1

		//delay drivetrain to ensure good picture for targeting
		addSequential(new DelayDrivetrain(0.75));
		
		addSequential(new StationaryVisionTurn(.5, 1.0));
		addSequential(new RunHopper());
		
    }
}
