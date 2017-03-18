package org.usfirst.frc.team2791.robot.util;

//import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * This class holds all important ports and constants that are used throughout the code
 * This is rarely changed, only when ports are switched or pid's are re-tuned
 * 
 */
public class CONSTANTS { //for constants ONLY; ports are in RobotMap

	// JOYSTICK CONSTANTS
	public static final double DEADZONE = 0.08;
	
	public static double driveEncoderTicks = 256;
	
	public static final double WHEEL_DIAMETER_IN_IN = 4.0;
	public static final double WHEEL_DIAMETER_IN_FEET = WHEEL_DIAMETER_IN_IN/12.0;

	public static final int SHOOTER_ENCODER_TICKS = 128;
	public static final double SHOOTER_WHEEL_DIAMETER = 4.0;
	
	// PID VALUES
	// Shooter PID
	public static double SHOOTER_P = 1.5; //1.5
	public static double SHOOTER_I = 0.000;//0.000
	public static double SHOOTER_D = 30.0; //30
	
	public static final double SHOOTER_FEED_FORWARD = 0.24; //0.24
	
	public static final double SHOOTER_SET_POINT = 2800; //2800
	public static final double SHOOTER_LONG_SET_POINT = 3650; //3650
	
	public static final int SHOOTER_I_ZONE = 40; //40

	public static final double SHOOTER_LONG_P = 1.5;//1.5
	public static final double SHOOTER_LONG_I = 0.0000;//0.0000
	public static final double SHOOTER_LONG_D = 30.0;//30.0
	public static final double SHOOTER_LONG_FEED_FORWARD = 0.25;//0.25
	
	//Drive PID
	public static double DRIVE_ANGLE_P = 0.025;
	public static double DRIVE_ANGLE_I = 0.1;
	public static double DRIVE_ANGLE_D = 0.001;
	public static double DRIVE_DISTANCE_P = 0.4;
	public static double DRIVE_DISTANCE_I = 0.2;
	public static double DRIVE_DISTANCE_D = 0.03;
	
	public static double STATIONARY_ANGLE_P = 0.025;
	public static double STATIONARY_ANGLE_I = 0.15; //0.1 was too little -TVR Q48
	public static double STATIONARY_ANGLE_D = 0.001;
	
	// Path following constants
    public static double kPathFollowingLookahead = 24.0; // inches
    public static double kPathFollowingMaxVel = 120.0; // inches/sec
    public static double kPathFollowingMaxAccel = 80.0; // inches/sec^2
    public static double kLooperDt = 0.02;
    
    public static double kTrackScrubFactor = 0.5;

}