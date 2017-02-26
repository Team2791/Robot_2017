package org.usfirst.frc.team2791.robot.ShakerJoystick;

import org.usfirst.frc.team2791.robot.RobotMap;

public class ShakerOperator extends OverriddenJoystick {
    private static ShakerOperator operatorJoystickInstance;
    
    public ShakerOperator() {
        super(RobotMap.JOYSTICK_OPERATOR_PORT);
    }

    public static ShakerOperator getInstance() {
        if (operatorJoystickInstance == null)
            operatorJoystickInstance = new ShakerOperator();
        return operatorJoystickInstance;

    }
    public double getGtaDriveLeft() {
        return super.getGtaDriveLeft();
    }

    public double getGtaDriveRight() {
        return super.getGtaDriveRight();
    }
}