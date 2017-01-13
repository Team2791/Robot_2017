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
	
	// PWM PORTS
	public static final int DRIVE_TALON_LEFT_PORT_FRONT = 5;
	public static final int DRIVE_TALON_LEFT_PORT_BACK = 4;
	public static final int DRIVE_TALON_LEFT_PORT_C = 7;
	public static final int DRIVE_TALON_RIGHT_PORT_FRONT = 2;
	public static final int DRIVE_TALON_RIGHT_PORT_BACK = 3;
	public static final int DRIVE_TALON_RIGHT_PORT_C = 0;
		
	/* Note: Constants are from 2016 - will change as needed for 2017
	 
	// SHOOTER CONTANTS
	public static final DoubleSolenoid.Value SMALL_PISTON_HIGH_STATE = DoubleSolenoid.Value.kReverse;
	public static final DoubleSolenoid.Value SMALL_PISTON_LOW_STATE = DoubleSolenoid.Value.kForward;
	public static final DoubleSolenoid.Value LARGE_PISTON_HIGH_STATE = DoubleSolenoid.Value.kReverse;
	public static final DoubleSolenoid.Value LARGE_PISTON_LOW_STATE = DoubleSolenoid.Value.kForward;
	
	// INTAKE CONSTANTS
	// ANALOG
	public static final int BALL_DISTANCE_SENSOR_PORT = 0;
	public static final int CLAMPING_SERVO_PORT = 1;
	
	// PCM PORTS
	// First Pcm module
	public static final int INTAKE_PISTON = 7;
	public static final int LONG_PISTON_FORWARD = 2;
	public static final int LONG_PISTON_REVERSE = 1;
	public static final int SHORT_PISTON_FORWARD = 4;
	public static final int SHORT_PISTON_REVERSE = 5;
	public static final int FUN_BRIDGE_ARM_PORT = 0;
	// CAN
	public static final int PCM_MODULE = 20;
	public static final int SHOOTER_TALON_RIGHT_PORT = 10;
	public static final int SHOOTER_TALON_LEFT_PORT = 11;
	*/
}
