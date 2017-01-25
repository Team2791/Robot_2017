package org.usfirst.frc.team2791.robot.ShakerJoystick;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.Util;

public class OverriddenJoystick extends Joystick {
    public OverriddenJoystick(int port) {
        super(port);
    }

    public double getXVal() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getX(), 1.0);
    }

    public double getYVal() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getY(), 1.0);
    }

    public boolean getDpadUp() {
        return super.getPOV(0) == 0;
    }

    public boolean getDpadUpRight() {
        return super.getPOV(0) == 45;
    }

    public boolean getDpadRight() {
        return super.getPOV(0) == 90;
    }

    public boolean getDpadDownRight() {
        return super.getPOV(0) == 135;
    }

    public boolean getDpadDown() {
        return super.getPOV(0) == 180;
    }

    public boolean getDpadDownLeft() {
        return super.getPOV(0) == 225;
    }

    public boolean getDpadLeft() {
        return super.getPOV(0) == 270;
    }

    public boolean getDpadUpLeft() {
        return super.getPOV(0) == 315;
    }

    public boolean getButtonA() {
        return super.getRawButton(1);
    }

    public boolean getButtonB() {
        return super.getRawButton(2);
    }

    public boolean getButtonX() {
        return super.getRawButton(3);
    }

    public boolean getButtonY() {
        return super.getRawButton(4);
    }

    public boolean getButtonLB() {
        return super.getRawButton(5);
    }

    public boolean getButtonRB() {
        return super.getRawButton(6);
    }

    public boolean getButtonSel() {
        return super.getRawButton(7);
    }

    public boolean getButtonSt() {
        return super.getRawButton(8);
    }

    public boolean getButtonLS() {
        return super.getRawButton(9);
    }

    public boolean getButtonRS() {
        return super.getRawButton(10);
    }

    public double getAxisLeftX() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getRawAxis(0), 1.0);
    }

    public double getAxisLeftY() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getRawAxis(1), 1.0);
    }

    public double getAxisLT() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getRawAxis(2), 1.0);
    }

    public double getAxisRT() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getRawAxis(3), 1.0);
    }

    public double getAxisRightX() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getRawAxis(4), 1.0);
    }

    public double getAxisRightY() {
        return Util.deadzone(CONSTANTS.DEADZONE, super.getRawAxis(5), 1.0);
    }
}