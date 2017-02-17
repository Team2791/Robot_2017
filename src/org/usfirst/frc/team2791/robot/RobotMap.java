package org.usfirst.frc.team2791.robot;

import edu.wpi.first.wpilibj.DigitalSource;

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
		 
	// INTAKE CONSTANTS
	// ANALOG
	public static final int IR_SENSOR_1 = 0;
	public static final int IR_SENSOR_2 = 1;
	
	// CAN
	public static final int PCM_MODULE = 20;
	
	public static final int LEFT_SHOOTER_TALON_PORT = 11;
	public static final int RIGHT_SHOOTER_TALON_PORT = 10;
	public static final int CENTER_SHOOTER_TALON_PORT = 0;
	
	public static final int SHOOTER_ENCODER_PORT_A = 0;
	public static final int SHOOTER_ENCODER_PORT_B = 0;
	

	//	public static final int SHOOTER_TALON_RIGHT_PORT = 10;
//	public static final int SHOOTER_TALON_LEFT_PORT = 11;
	
	//PCM Ports--note: due to use of single solenoids, solenoids will default to false state while disabled
	public static final int INTAKE_CHANNEL = 7;
	public static final int WING_CHANNEL = 5;
	public static final int GEAR_CHANNEL = 6;
	public static final int SHOOTER_CHANNEL = 4;
	
	//PDP Ports
}
