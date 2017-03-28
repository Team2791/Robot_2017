package org.usfirst.frc.team2791.robot.util;

import edu.wpi.first.wpilibj.Timer;

/**
 * This utility provides Basic PID based functions for any sort of control system.
 * We used it in our PID based auto commands.
 * 
 * @author team2791: See Robot.java for contact info
 * @see Robot
 */
public class BasicPID {
    // P, I and D constants
    protected double m_p;
    protected double m_i;
    protected double m_d;
    protected double m_setPoint = 0.0;
    protected double m_previousError = 0.0;
    protected double m_currentError = 0.0;
    protected double m_maxOutput = 1.0;
    protected double m_minOutput = -1.0;
    protected double m_integrator = 0.0;
    protected double m_previousTime = 0.0;
    protected double m_currentTime = 0.0;
    protected double m_output = 0.0;
    protected double m_izone = 0;
    protected boolean invert = false;

    public BasicPID(double p, double i, double d) {
        m_p = p;
        m_i = i;
        m_d = d;
    }

    public void changeGains(double p, double i, double d) {
        m_p = p;
        m_i = i;
        m_d = d;
    }

    public void setMinOutput(double min) {
        m_minOutput = min;
    }

    public void setMaxOutput(double max) {
        m_maxOutput = max;
    }

    // TODO: add get and set methods for P, I, D constants
    public double getSetPoint() {
        return m_setPoint;
    }

    public void setSetPoint(double setPoint) {
        m_setPoint = setPoint;
    }

    public void setInvertOutput(boolean invertOutput) {
        invert = invertOutput;
    }

    /**
     * Update with the current error and get the new output from the PID
     */
    public double updateAndGetOutput(double currentValue) {
        // updateSmartDash the current time and error
        m_currentTime = Timer.getFPGATimestamp();
        m_currentError = currentValue - m_setPoint;

        double output = getPPart() + getIPart() + getDPart();
        m_previousTime = m_currentTime;
        m_previousError = m_currentError;

        if (output > m_maxOutput)
            output = m_maxOutput;
        else if (output < m_minOutput)
            output = m_minOutput;
        if (invert)
            return m_output = -output;
        else
            return m_output = output;
    }

    public double getError() {
        return m_currentError;
    }

    /**
     * Get the last output sent by the PID
     *
     * @return the last output send by the PID
     */
    public double getOutput() {
        return m_output;
    }

    public void setIZone(double IZone) {
        m_izone = IZone;
    }

    /**
     * Reset all the internal PID values. Call this when it has been a long time
     * since the PID was last updated
     */
    public void reset() {
        m_previousError = 0.0;
        m_integrator = 0.0;
        m_previousTime = 0.0;
        m_output = 0.0;
    }

    private double getPPart() {
        return m_currentError * m_p;
    }

    private double getIPart() {
        // UPDATE LATER, for performnace can change the multiplication of m_i to
        // happen
        // when the new trapazoid is added to the integrator, then don't have to
        // do any
        // multiplication when doing compares, for now leaving it in so it's
        // clearer how
        // the PID works

        // if this is the first time the PID is call can't calc integral so
        // return 0
        if (0.0 == m_previousTime || m_i == 0.0)
            return 0.0;
        // use trapazoidal aproximation to calc the about to add to the
        // integrator

        m_integrator += (m_currentError + m_previousError) / 2 * (m_currentTime - m_previousTime);
        // only use integral when we're close to the setpoint
        if (m_izone != 0) {
            if (Math.abs(m_currentError) > m_izone)
                m_integrator = 0;
        }

        // saturate the integrator, don't let the output from the I part get
        // larger than
        // the max output
        if (m_integrator * m_i > m_maxOutput)
            m_integrator = m_maxOutput / m_i;
        if (m_integrator * m_i < m_minOutput)
            m_integrator = m_minOutput / m_i;

        return m_integrator * m_i;
    }

    private double getDPart() {
        // if this is the first time the PID is called no can't calc driv so
        // return 0
        if (m_previousTime == 0.0 || m_d == 0.0)
            return 0.0;

        // calculate the drive (change in X over change in Y), multiply by Kd and
        // return
        return m_d * ((m_currentError - m_previousError) / (m_currentTime - m_previousTime));
    }

}