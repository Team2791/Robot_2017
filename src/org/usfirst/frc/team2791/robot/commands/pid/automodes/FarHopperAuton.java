package org.usfirst.frc.team2791.robot.commands.pid.automodes;

import org.usfirst.frc.team2791.robot.commands.DriveDelay;
import org.usfirst.frc.team2791.robot.commands.HopperOn;
import org.usfirst.frc.team2791.robot.commands.IntakeOn;
import org.usfirst.frc.team2791.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2791.robot.commands.pid.DriveEncoderBangBang;
import org.usfirst.frc.team2791.robot.commands.pid.StationaryVisionTurn;
import org.usfirst.frc.team2791.robot.commands.pid.TurnGyroBangBang;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hopper Auto
 */
public class FarHopperAuton extends CommandGroup {

	double direction = 1.0;
	/**
	 * @param red  true if you are on the red side;  false if you are on the blue side
	 */
	public FarHopperAuton(String color) {
		
		if(color.equals("RED"))
			direction = -1.0;
		else
			direction = 1.0;
		
		addParallel(new SpinUpShooter(CONSTANTS.SHOOTER_AUTO_HOPPER_SET_POINT)); //this is going to have to be bigger
		addSequential(new DriveEncoderBangBang(-.8, 0.0, -5.5)); //distance is a complete guess lel
		
		/** This is sketch as heck because we're counting on the drift from the first drive
		to get us to the 2nd distance and just using the drive train to slow down and turn a bit.**/
		addSequential(new DriveEncoderBangBang(-0.3, 0.0, -3.75, 2.5)); //distance is a guess again
		addParallel (new IntakeOn());
		
		/**turn in general boiler direction**/
		addSequential(new TurnGyroBangBang(-0.7 *direction , 10 * direction, 1.0)); //direction is a complete guess lel
		
		addSequential(new DriveDelay(0.75)); 
		
		addSequential(new StationaryVisionTurn(.5, 1.0));
		addSequential(new HopperOn());
		
    }
}
