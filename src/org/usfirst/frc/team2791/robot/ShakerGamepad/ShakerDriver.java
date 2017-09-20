package org.usfirst.frc.team2791.robot.ShakerGamepad;

import org.usfirst.frc.team2791.robot.RobotMap;

/**
 * Robot driver object
 * @author team2791: See Robot.java for contact info
 */
public class ShakerDriver extends OverriddenGamepad {
	private static ShakerDriver driverJoystickInstance;
	// this is to account for any slack in the drive train

	public ShakerDriver() {
		super(RobotMap.JOYSTICK_DRIVER_PORT);
	}

	public static ShakerDriver getInstance() {
		if (driverJoystickInstance == null)
			driverJoystickInstance = new ShakerDriver();
		return driverJoystickInstance;
	}

	public double getGtaDriveLeft() {
		return super.getGtaDriveLeft();
	}

	public double getGtaDriveRight() {
		return super.getGtaDriveRight();

	}
}