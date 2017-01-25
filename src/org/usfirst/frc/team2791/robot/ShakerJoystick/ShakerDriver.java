package org.usfirst.frc.team2791.shakerJoystick;

import org.usfirst.frc.team2791.util.Constants;

public class Driver extends OverriddenJoystick {
    private static Driver driverJoystickInstance;
    // this is to account for any slack in the drive train
    private double offset = 0;

    private Driver() {
        super(Constants.JOYSTICK_DRIVER_PORT);
    }

    public static Driver getInstance() {
        if (driverJoystickInstance == null)
            driverJoystickInstance = new Driver();
        return driverJoystickInstance;
    }

    public double getGtaDriveLeft() {
        // Does the math to get Gta Drive Type on left motor
        double leftAxis;
        if (super.getAxisLeftX() < 0)
            leftAxis = -Math.pow(super.getAxisLeftX(), 2) - offset;
        else
            leftAxis = Math.pow(super.getAxisLeftX(), 2) + offset;
        double combined = leftAxis + super.getAxisRT() - super.getAxisLT();
        if (combined > 1.0)
            return 1.0;
        else if (combined < -1.0)
            return -1.0;
        return combined;

    }

    public double getGtaDriveRight() {
        // Does the math to get Gta Drive Type on right motor
        double leftAxis;
        if (super.getAxisLeftX() < 0)
            leftAxis = -Math.pow(super.getAxisLeftX(), 2) - offset;
        else
            leftAxis = Math.pow(super.getAxisLeftX(), 2) + offset;
        double combined = -leftAxis + super.getAxisRT() - super.getAxisLT();
        if (combined > 1.0)
            return 1.0;
        else if (combined < -1.0)
            return -1.0;
        return combined;
    }

    // place driver button layout here
}