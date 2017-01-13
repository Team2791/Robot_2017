package org.usfirst.frc.team2791.shakerJoystick;

import org.usfirst.frc.team2791.util.Constants;

public class Operator extends OverriddenJoystick {
    private static Operator operatorJoystickInstance;

    private Operator() {
        super(Constants.JOYSTICK_OPERATOR_PORT);
    }

    public static Operator getInstance() {
        if (operatorJoystickInstance == null)
            operatorJoystickInstance = new Operator();
        return operatorJoystickInstance;

    }
    // place any special controls here


}