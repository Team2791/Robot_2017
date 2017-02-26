package org.usfirst.frc.team2791.robot.util;

//import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * This class holds all important ports and constants that are used throughout the code
 * This is rarely changed, only when ports are switched or pid's are re-tuned
 * @author unbun
 * @author Akhil Jacobs
 */
public class CONSTANTS { //for constants ONLY; ports are in RobotMap

	// JOYSTICK CONSTANTS
	public static final double DEADZONE = 0.08;
	
	public static double driveEncoderTicks = 256;
	
	public static final double WHEEL_DIAMETER_IN_IN = 4.0;
	public static final double WHEEL_DIAMETER_IN_FEET = WHEEL_DIAMETER_IN_IN/12.0;

	public static final int SHOOTER_ENCODER_TICKS = 128;
	public static final double SHOOTER_WHEEL_DIAMETER = 4.0;
	
	// 2016 Constants = can be changed as needed
	// PID VALUES
	// Shooter PID
	public static double SHOOTER_P = 3.0; //3.0
	public static double SHOOTER_I = 0.005;//0.005
	public static double SHOOTER_D = 100.0; //100.0
	
	public static final double SHOOTER_FEED_FORWARD = 0.30115; //0.30115
	
	public static final double SHOOTER_SET_POINT = 2825; //2825
	
	public static final int SHOOTER_I_ZONE = 150; //150
	
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
	
	// Path following constants
    public static double kPathFollowingLookahead = 24.0; // inches
    public static double kPathFollowingMaxVel = 120.0; // inches/sec
    public static double kPathFollowingMaxAccel = 80.0; // inches/sec^2
    public static double kLooperDt = 0.02;
    
    public static double kTrackScrubFactor = 0.5;

}