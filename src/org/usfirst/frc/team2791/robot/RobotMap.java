package org.usfirst.frc.team2791.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {//to map ports; constants are in separate class
		
	// JOYSTICK PORTS
	public static final int JOYSTICK_DRIVER_PORT = 0;
	public static final int JOYSTICK_OPERATOR_PORT = 1;
	
	// DIO
	public static final int LEFT_DRIVE_ENCODER_PORT_A = 1;
	public static final int LEFT_DRIVE_ENCODER_PORT_B = 0;
	public static final int RIGHT_DRIVE_ENCOODER_PORT_A = 9;
	public static final int RIGHT_DRIVE_ENCODER_PORT_B = 8;

	public static final int SHOOTER_ENCODER_PORT_A = 0;
	public static final int SHOOTER_ENCODER_PORT_B = 0;
	// PWM PORTS
	public static final int DRIVE_TALON_LEFT_PORT_FRONT = 5;//Spark speed controllers
	public static final int DRIVE_TALON_RIGHT_PORT_FRONT = 2;//each drive side is split from one PWM port into 3 connections for each speed controller
	
	/* Note: Constants are from 2016 - will change as needed for 2017
	 
	// SHOOTER CONTANTS
	public static final DoubleSolenoid.Value SMALL_PISTON_HIGH_STATE = DoubleSolenoid.Value.kReverse;
	public static final DoubleSolenoid.Value SMALL_PISTON_LOW_STATE = DoubleSolenoid.Value.kForward;
	public static final DoubleSolenoid.Value LARGE_PISTON_HIGH_STATE = DoubleSolenoid.Value.kReverse;
	public static final DoubleSolenoid.Value LARGE_PISTON_LOW_STATE = DoubleSolenoid.Value.kForward;
	
	// INTAKE CONSTANTS
	// ANALOG
	public static final int BALL_DISTANCE_SENSOR_PORT = 0;
	public static final int CLAMPING_SERVO_PORT = 1;*/
	
	// PCM PORTS
	// First Pcm module
	public static final int INTAKE_PISTON = 0;
	public static final int FUN_BRIDGE_ARM_PORT = 0;
	public static final int CLIMBER_WING_DEPLOY_PORT = 0;
	public static final int GEAR_DROP_PORT = 0;
	public static final int SHOOTER_POSITION_PORT = 0;

	// CAN
	public static final int PCM_MODULE = 20;

	public static final int LEFT_SHOOTER_TALON_PORT = 0;//Talon SRX
	public static final int RIGHT_SHOOTER_TALON_PORT = 0;

}
