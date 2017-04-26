package org.usfirst.frc.team2791.robot.commands.autos.pid;

import org.usfirst.frc.team2791.robot.commands.DriveEncoderBangBang;
import org.usfirst.frc.team2791.robot.commands.RunHopper;
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
				
		if(color.equals("RED"))
			direction = -1.0;
		else
			direction = 1.0;
		
		addParallel(new SpinUpShooter(CONSTANTS.SHOOTER_AUTO_CENTER_SET_POINT));
		
    	addSequential(new DriveStraightEncoderGyro(-(108-36.0)/12, .7, 6)); //110.5 worked
    	addSequential(new AutonGearScore());
    	addSequential(new DriveStraightEncoderGyro(2.0, .7, 1.0));//distance was 3.0
    	
		addSequential(new TurnGyroBangBang(0.4 * direction , 65 * direction));
		addSequential(new DriveEncoderBangBang(0.5, 0.0, 5, 1.0)); //1
		
		addSequential(new DelayDrivetrain(0.75));
		addSequential(new StationaryVisionTurn(0.5,1.5));//0.4,1.0
		addSequential(new RunHopper());
    }
}
