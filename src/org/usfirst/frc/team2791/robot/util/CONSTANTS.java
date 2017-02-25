package org.usfirst.frc.team2791.robot.util;

//import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Created by Gaurab on 1/12/2017.
 * Adapted from Akhil (2016).
 * This class holds all important ports and constants that are used throughout the code
 * This is rarely changed, only when ports are switched or pid's are re-tuned
 */
public class CONSTANTS { //for constants ONLY; ports are in RobotMap

	// JOYSTICK CONSTANTS
	public static final double DEADZONE = 0.08;
	
	public static double driveEncoderTicks = 128;
	
	public static final double WHEEL_DIAMETER = 4.0;

	public static final int SHOOTER_ENCODER_TICKS = 128;
	public static final double SHOOTER_WHEEL_DIAMETER = 4.0;
	
	// 2016 Constants = can be changed as needed
	// PID VALUES
	// Shooter PID
	public static double SHOOTER_P = 3.96; //3.96
	public static double SHOOTER_I = 0.00396;//0.00396
	public static double SHOOTER_D = 39.6; //39.6
	
	public static final double SHOOTER_FEED_FORWARD = 0.2842; // 0.2842; calculated by doing 1093/max velocity
	
	public static final double SHOOTER_SET_POINT = 2855; //2855
	
	public static final int SHOOTER_I_ZONE = 90; //90
	
	//Drive PID
	public static double DRIVE_ANGLE_P = 0.05;
	public static double DRIVE_ANGLE_I = 0;
	public static double DRIVE_ANGLE_D = 0.000;
	public static double STATIONARY_ANGLE_P = 0.15;
	public static double STATIONARY_ANGLE_I = 1.2;
	public static double STATIONARY_ANGLE_D = 0.0;
	public static double DRIVE_DISTANCE_P = 1.3;
	public static double DRIVE_DISTANCE_I = 0.01;
	public static double DRIVE_DISTANCE_D = 0.2;
}