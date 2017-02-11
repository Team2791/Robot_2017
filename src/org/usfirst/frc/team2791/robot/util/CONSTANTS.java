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

	// 2016 Constants = can be changed as needed
	// PID VALUES
	// Shooter PID
	public static double SHOOTER_P = 0.4;
	public static double SHOOTER_I = 0.001;
	public static double SHOOTER_D = 0;
	public static double DRIVE_ANGLE_P = 0.05;
	public static double DRIVE_ANGLE_I = 0;
	public static double DRIVE_ANGLE_D = 0.000;
	public static double STATIONARY_ANGLE_P = 0.15;
	public static double STATIONARY_ANGLE_I = 1.2;
	public static double STATIONARY_ANGLE_D = 0.0;
	public static double DRIVE_DISTANCE_P = 1.3;
	public static double DRIVE_DISTANCE_I = 0.01;
	public static double DRIVE_DISTANCE_D = 0.2;
	
	// Path following constants
    public static double kPathFollowingLookahead = 24.0; // inches
    public static double kPathFollowingMaxVel = 120.0; // inches/sec
    public static double kPathFollowingMaxAccel = 80.0; // inches/sec^2
    public static double kLooperDt = 0.01;
    
    public static double kTrackScrubFactor = 0.5;

}