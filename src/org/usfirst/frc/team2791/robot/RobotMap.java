package org.usfirst.frc.team2791.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around. 
 * </br><b> There is a Google Sheet with all wiring and pneumatic related information.</b>
 * 
 * @author team2791: See Robot.java for contact info
 */
public class RobotMap {
	
	// JOYSTICK PORTS
	public static final int JOYSTICK_DRIVER_PORT = 0;
	public static final int JOYSTICK_OPERATOR_PORT = 1;

	// DIO
	public static final int LEFT_DRIVE_ENCODER_PORT_A = 9; //3 on Comp
	public static final int LEFT_DRIVE_ENCODER_PORT_B = 8; //2 on Comp
	
	public static final int RIGHT_DRIVE_ENCODER_PORT_A = 7; //1 on Comp
	public static final int RIGHT_DRIVE_ENCODER_PORT_B = 6; //0 on Comp
	
	public static final int GEAR_INTAKE_LIMIT_SWITCH_A = 0;//need to add on Comp
	
	// PWM PORTS
	public static final int DRIVE_SPARK_LEFT_PORT = 9;
	public static final int DRIVE_SPARK_RIGHT_PORT = 8;
	
	public static final int INTAKE_SPARK_PORT = 6;
	public static final int HOPPER_SPARK_PORT = 4; //7 on Comp
	public static final int GEAR_SPARK_PORT = 3; //Need to add on Comp
	
	// ANALOG
	public static final int IR_SENSOR_1 = 0;
	public static final int IR_SENSOR_2 = 1;
	
	// CAN
	public static final int PDP = 0;
	public static final int PCM_MODULE = 1;
	
	public static final int PRIMARY_SHOOTER_TALON_PORT = 10;
	public static final int FOLLOWER_SHOOTER_TALON_PORT = 11;
	public static final int SECONDARY_FOLLOWER_SHOOTER_TALON_PORT = 63;
	
	//PCM Ports--note: due to use of only single solenoids, ALL solenoids will default to false state while disabled
	public static final int INTAKE_CHANNEL = 7;
	public static final int WING_CHANNEL = 4;
	public static final int GEAR_CHANNEL = 6;
	public static final int SHOOTER_CHANNEL = 5;
	
	//PDP Ports
	public static final int POWER_RIGHT_DRIVE_A = 0;
	public static final int POWER_RIGHT_DRIVE_B = 1;
	public static final int POWER_RIGHT_DRIVE_C = 2;

	public static final int POWER_LEFT_DRIVE_A = 13;
	public static final int POWER_LEFT_DRIVE_B = 14;
	public static final int POWER_LEFT_DRIVE_C = 15;
	
	public static final int POWER_CLIMBER_INTAKE = 3;
	public static final int POWER_CLIMBER_INTAKE_2 = 10;
	
	public static final int POWER_HOPPER_FLOOR = 12;
	
	public static final int POWER_PRIMARY_SHOOTER = 4;
	public static final int POWER_FOLLOWER_SHOOTER = 11;
	
	public static final int POWER_GEAR_INTAKE = 9; //Need to add on comp
}
