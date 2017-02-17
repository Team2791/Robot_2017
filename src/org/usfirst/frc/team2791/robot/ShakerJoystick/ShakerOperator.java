package org.usfirst.frc.team2791.robot.ShakerJoystick;

import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

public class ShakerOperator extends OverriddenJoystick {
    private static ShakerOperator operatorJoystickInstance;
    public static double offset = 0;
    
    public ShakerOperator() {
        super(RobotMap.JOYSTICK_OPERATOR_PORT);
    }

    public static ShakerOperator getInstance() {
        if (operatorJoystickInstance == null)
            operatorJoystickInstance = new ShakerOperator();
        return operatorJoystickInstance;

    }
    // place any special controls here
}