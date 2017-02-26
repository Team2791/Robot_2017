package org.usfirst.frc.team2791.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {//to map ports; constants are in separate class
	//if all ports are not correctly throughout code (i.e. ports utilized more than once), startCompetition() error comes
	// JOYSTICK PORTS
	public static final int JOYSTICK_DRIVER_PORT = 0;
	public static final int JOYSTICK_OPERATOR_PORT = 1;
	
	// DIO
	public static final int LEFT_DRIVE_ENCODER_PORT_A = 9;
	public static final int LEFT_DRIVE_ENCODER_PORT_B = 8;
	public static final int RIGHT_DRIVE_ENCODER_PORT_A = 7;
	public static final int RIGHT_DRIVE_ENCODER_PORT_B = 6;
	
	// PWM PORTS
	public static final int DRIVE_SPARK_LEFT_PORT = 9;
	public static final int DRIVE_SPARK_RIGHT_PORT = 8;
	
	public static final int INTAKE_SPARK_PORT = 6;
	
	public static final int HOPPER_SPARK_PORT = 7;
		
//	public static final int DRIVE_TALON_LEFT_PORT_FRONT = 5;
//	public static final int DRIVE_TALON_LEFT_PORT_BACK = 4;
//	public static final int DRIVE_TALON_LEFT_PORT_C = 7;
//	public static final int DRIVE_TALON_RIGHT_PORT_FRONT = 2;
//	public static final int DRIVE_TALON_RIGHT_PORT_BACK = 3;
//	public static final int DRIVE_TALON_RIGHT_PORT_C = 0;
//	public static final int INTAKE_TALON_LEFT_PORT = 6;
//	public static final int INTAKE_TALON_RIGHT_PORT = 1;
//	public static final int BALL_AID_SERVO_PORT = 9;
	public static final int DRIVE_TALON_LEFT_PORT = 3;//9
	public static final int DRIVE_TALON_RIGHT_PORT = 5;//8
	// INTAKE CONSTANTS
	// ANALOG
	public static final int IR_SENSOR_1 = 0;
	public static final int IR_SENSOR_2 = 1;
	
	// CAN
	public static final int PCM_MODULE = 1;

	//	public static final int SHOOTER_TALON_RIGHT_PORT = 10;
//	public static final int SHOOTER_TALON_LEFT_PORT = 11;
	
	//PCM Ports
	public static final int INTAKE_CHANNEL = 7;
	public static final int WING_CHANNEL = 5;
	public static final int GEAR_CHANNEL = 6;
	
}
