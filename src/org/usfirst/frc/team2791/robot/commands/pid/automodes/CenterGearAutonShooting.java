package org.usfirst.frc.team2791.robot.commands.pid.automodes;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.commands.AimAndShoot;
import org.usfirst.frc.team2791.robot.commands.AutonGearScore;
import org.usfirst.frc.team2791.robot.commands.DrivetrainDelay;
import org.usfirst.frc.team2791.robot.commands.HopperOn;
import org.usfirst.frc.team2791.robot.commands.IntakeOn;
import org.usfirst.frc.team2791.robot.commands.RunVisionShot;
import org.usfirst.frc.team2791.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2791.robot.commands.pid.DriveEncoderBangBang;
import org.usfirst.frc.team2791.robot.commands.pid.DriveStraightEncoderGyro;
import org.usfirst.frc.team2791.robot.commands.pid.StationaryVisionTurn;
import org.usfirst.frc.team2791.robot.commands.pid.TurnGyroBangBang;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hangs the Center Gear
 */
public class CenterGearAutonShooting extends CommandGroup {
	double direction = 1.0;

	public CenterGearAutonShooting() {
		super("Center Gear & Shoot");
				
		String color = Robot.teamColor.toString();
		
		if(color.equals("RED"))
			direction = -1.0;
		else
			direction = 1.0;
		
		addParallel(new SpinUpShooter(CONSTANTS.SHOOTER_AUTO_CENTER_SET_POINT));
		
		// 110 was short
    	addSequential(new DriveStraightEncoderGyro(-78.0/12.0, .60, 2.5, 2.0/12.0)); //108 worked
    	addSequential(new AutonGearScore());
//    	addParallel(new IntakeOn());
    	
    	addSequential(new DriveStraightEncoderGyro(3.0, .7, 1.0, 2.0/12.0));//distance was 3.0
    	
		addSequential(new TurnGyroBangBang(0.65 * direction , 62.0 * direction)); //65.0
		addSequential(new DriveEncoderBangBang(0.7, 0.0, 4.5, 1.0)); //1.5, 1.0 was just a bit short
				
//		addSequential(new DrivetrainDelay(0.75));
//		addSequential(new StationaryVisionTurn(0.5,1.5));//0.4,1.0
//		addSequential(new HopperOn());
		
		addSequential(new AimAndShoot());

    }
}
