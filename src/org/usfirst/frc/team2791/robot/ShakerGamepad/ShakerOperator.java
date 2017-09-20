package org.usfirst.frc.team2791.robot.ShakerGamepad;

import org.usfirst.frc.team2791.robot.RobotMap;

/**
 * Robot operator object
 * @author team2791: See Robot.java for contact info
 */
public class ShakerOperator extends OverriddenGamepad {
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