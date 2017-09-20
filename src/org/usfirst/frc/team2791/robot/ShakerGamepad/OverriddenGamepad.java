package org.usfirst.frc.team2791.robot.ShakerGamepad;

import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.Util;

import edu.wpi.first.wpilibj.Joystick;

/**
 * We use Gamepad controllers. See Driver Station for button mapping.
 * 
 * @author team2791: See Robot.java for contact info
 */
public class OverriddenGamepad extends Joystick {
	public static double offset = 0;//to account for any slack in the system
	
    public OverriddenGamepad(int port) {
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

    public boolean getButtonBack() {
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
    
    /**
     * Uses left trigger for drive speed
     * @return output value for left drive motors (double)
     */
    public double getGtaDriveLeft() {
        // Does the math to get Gta Drive Type on left motor
        double leftAxis;
        if (getAxisLeftX() < 0)
            leftAxis = -Math.pow(getAxisLeftX(), 2) - offset;
        else
            leftAxis = Math.pow(getAxisLeftX(), 2) + offset;
        double combined = leftAxis + getAxisRT() - getAxisLT();
        if (combined > 1.0)
            return 1.0;
        else if (combined < -1.0)
            return -1.0;
        return combined;
    }

    /**
     * Uses right trigger for drive speed
     * @return output value for right drive motors (double)
     */
    public double getGtaDriveRight() {
        // Does the math to get Gta Drive Type on right motor
        double leftAxis;
        if (getAxisLeftX() < 0)
            leftAxis = -Math.pow(getAxisLeftX(), 2) - offset;
        else
            leftAxis = Math.pow(getAxisLeftX(), 2) + offset;
        double combined = -leftAxis + getAxisRT() - getAxisLT();
        if (combined > 1.0)
            return 1.0;
        else if (combined < -1.0)
            return -1.0;
        return combined;
    }
}