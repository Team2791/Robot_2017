package org.usfirst.frc.team2791.robot.util;

/**
 * This class holds all important constants that are used in the code.
 * Our main utility in here is for PID tuning constants which we have found experimentally.
 * 
 * @author team2791: See Robot.java for contact info
 * @see Robot
 */
public class CONSTANTS { //for constants ONLY; ports are in RobotMap

	// JOYSTICK CONSTANTS
	public static final double DEADZONE = 0.08;
	
	public static double driveEncoderTicks = 256;//Greyhill
	
	public static final double WHEEL_DIAMETER_IN_IN = 3.9;//Drive wheels - Colson
	public static final double WHEEL_DIAMETER_IN_FEET = WHEEL_DIAMETER_IN_IN/12.0;

	public static final int SHOOTER_ENCODER_TICKS = 128;//Greyhill
	public static final double SHOOTER_WHEEL_DIAMETER = 4.0;//Colson (in inches)
	
	/*******************************PID VALUES****************************************/
	
	// Shooter PID
	public static final double CAMERA_HORIZONTAL_OFFSET = 3.1;
	
	public static final double SHOOTER_SET_POINT = 2850; //2765 was a bit too low
	public static final double SHOOTER_LONG_SET_POINT = 3025.0; //3650
	public static final double SHOOTER_AUTO_HOPPER_SET_POINT = 3025;//3255 //3355 //3035
	public static final double SHOOTER_AUTO_CENTER_SET_POINT = 3375; //3385 was too much QF2
	public static final double SHOOTER_VISION_SWEET_SET_POINT = 3025.0; //hopefully will be the same as the long point :^)
	
	public static final int SHOOTER_I_ZONE = 75; //40
	
	public static final double SHOOTER_P = 2.7; //1.5
	public static final double SHOOTER_I = 0.002;//0.000
	public static final double SHOOTER_D = 150.0; //30
	public static final double SHOOTER_FEED_FORWARD = 0.22; //0.24
	
	public static final double SHOOTER_LONG_P = 2.05;//3.5 //2.05
	public static final double SHOOTER_LONG_I = 0.004;//0.0000 //0.004
	public static final double SHOOTER_LONG_D = 50.0;//50.0 //50.0
	public static final double SHOOTER_LONG_FEED_FORWARD = 0.228;//0.25 then 0.231 then 0.228
	
	public static final double SHOOTER_VISION_P = 1.5;//3.5
	public static final double SHOOTER_VISION_I = 0.0;//0.0000
	public static final double SHOOTER_VISION_D = 17.0;//50.0
	public static final double SHOOTER_VISION_FEED_FORWARD = 0.22;//0.25 then 0.231 then 0.228
	
	//Drive PID
	public static double DRIVE_ANGLE_P = 0.025;
	public static double DRIVE_ANGLE_I = 0.02;
	public static double DRIVE_ANGLE_D = 0.003;
	public static double DRIVE_DISTANCE_P = 0.4;
	public static double DRIVE_DISTANCE_I = 0.2;
	public static double DRIVE_DISTANCE_D = 0.03;
	
	public static double STATIONARY_ANGLE_P = 0.04;
	public static double STATIONARY_ANGLE_I = 0.02; //0.1 was too little -TVR Q48
	public static double STATIONARY_ANGLE_D = 0.004;
	
	public static double shootingDriveP = 0.025;
	
	// Path following constants
    public static double kPathFollowingLookahead = 24.0; // inches
    public static double kPathFollowingMaxVel = 120.0; // inches/sec
    public static double kPathFollowingMaxAccel = 80.0; // inches/sec^2
    public static double kLooperDt = 0.02;
    
    public static double kTrackScrubFactor = 0.5;

}