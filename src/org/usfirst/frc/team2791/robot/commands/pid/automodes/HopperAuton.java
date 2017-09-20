package org.usfirst.frc.team2791.robot.commands.pid.automodes;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.commands.DrivetrainDelay;
import org.usfirst.frc.team2791.robot.commands.HopperOnOnceAtSpeed;
import org.usfirst.frc.team2791.robot.commands.IntakeOn;
import org.usfirst.frc.team2791.robot.commands.RunVisionShot;
import org.usfirst.frc.team2791.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2791.robot.commands.pid.DriveEncoderBangBang;
import org.usfirst.frc.team2791.robot.commands.pid.StationaryVisionTurn;
import org.usfirst.frc.team2791.robot.commands.pid.TurnGyroBangBang;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Hopper Auto
 */
public class HopperAuton extends CommandGroup {

	double direction = 1.0;
	

	public HopperAuton() {
		
		super("Hopper Auton");
		
		String color = Robot.teamColor.toString();
		
		if(color.equals("RED")) {
			System.out.println("Red hopper");
			direction = 1.0;
		} else {
			System.out.println("Blue hopper");
			direction = -1.0;
		}
	
		
		addParallel(new SpinUpShooter(3020)); //3050 was 3035
		addSequential(new DriveEncoderBangBang(-.8, 0.0, -3.5, 3));
		// This is sketch as heck because we're counting on the drift from the first drive
		// to get us to the 2nd	 distance and just using the drive train to slow down and turn a bit.
//		addSequential(new DriveEncoderBangBang(0.0, 0.075, -1));
		addSequential(new DriveEncoderBangBang(-0.3, 0.0, -3.75+0.5, 2)); //-3.5
		addParallel (new IntakeOn());
		
		addSequential(new TurnGyroBangBang(0.82*direction, (20+10)*direction)); //15 //adding 10 for IRI to make the kick more reliable

		addSequential(new TurnGyroBangBang(-0.82 *direction, -(33+10)*direction)); //20 - 28 //also 10 for IRI
		addSequential(new DriveEncoderBangBang(0.5, 0.0, 1.0, 2)); //1.5 was too much

		// wait to take a good image before using vision to turn
		addSequential(new DrivetrainDelay(0.75));
		
		addSequential(new StationaryVisionTurn(.5, 1.0));
//		addParallel(new SpinUpShooter(Robot.visionTable.getDistanceBasedRPM()));
//		addSequential(new HopperOnOnceAtSpeed());

		addSequential(new RunVisionShot());
    }
	
	
	
	
	
	
	
	
	
//	
//	/**
//	 * @param red  true if you are on the red side;  false if you are on the blue side
//	 */
//	public HopperAuton(String color) {
//		
//		if(color.equals("RED"))
//			direction = 1.0;
//		else
//			direction = -1.0;
//		
//		addParallel(new SpinUpShooter(CONSTANTS.SHOOTER_AUTO_HOPPER_SET_POINT));
//		
//		
//		addSequential(new DriveEncoderBangBang(-.8, 0.0, -91/12.0 + 59.0/12.0 -0.5 -1)); //measured 118in, left 30 in for the precision drive
//		// v from tech park 
////		addSequential(new DriveEncoderBangBang(-.8, 0.0, -88/12.0)); //measured 118in, left 30 in for the precision drive
//		
//		/** This is sketch as heck because we're counting on the drift from the first drive
//		to get us to the 2nd distance and just using the drive train to slow down and turn a bit.**/
//		addSequential(new DriveEncoderBangBang(-0.3, 0.0, -3.75 +1, 2.5)); //30in
//		addParallel (new RunIntake());
//		
//		/**kick the hopper to ensure the hopper is triggered
//		addSequential(new TurnGyroBangBang(-0.7 * direction, -15 * direction, 1.0));
//		point in the general direction of the boiler**/
//		addSequential(new TurnGyroBangBang(0.7 *direction , 15 * direction, 1.0));
//		addSequential(new TurnGyroBangBang(-0.7 *direction , 10 * direction, 1.0)); //? i think we should take out the kick maybe
//		
//		//drive forward to get moar ballz
//		addSequential(new DriveEncoderBangBang(0.5, 0.0, 1, 1.0)); //1
//
//		//delay drivetrain to ensure good picture for targeting
//		addSequential(new DelayDrivetrain(0.75));
//		
//		addSequential(new StationaryVisionTurn(.5, 1.0));
//		addSequential(new RunHopper());
//		
//    }
}
