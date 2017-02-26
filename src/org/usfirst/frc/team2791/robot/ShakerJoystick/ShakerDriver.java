package org.usfirst.frc.team2791.robot.ShakerJoystick;

import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

public class ShakerDriver extends OverriddenJoystick {
    private static ShakerDriver driverJoystickInstance;
    // this is to account for any slack in the drive train
    private static double offset = 0;

    public ShakerDriver() {
        super(RobotMap.JOYSTICK_DRIVER_PORT);
    }

    public static ShakerDriver getInstance() {
        if (driverJoystickInstance == null)
            driverJoystickInstance = new ShakerDriver();
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