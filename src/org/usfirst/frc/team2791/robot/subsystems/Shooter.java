package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.Util;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
//import com.ctre.CANTalon; 
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {

    protected Encoder shooterEncoder = null;

    private CANTalon leftShooterTalon = null;
    private CANTalon rightShooterTalon = null;
    private CANTalon powerShooterTalon = null; //Potential third Talon for extra shooter power

    protected boolean prepShot = false;
    protected boolean autoFire = false;

    public void initDefaultCommand() {

        this.leftShooterTalon = new CANTalon(RobotMap.LEFT_SHOOTER_TALON_PORT);
        this.rightShooterTalon = new CANTalon(RobotMap.RIGHT_SHOOTER_TALON_PORT);

        this.shooterEncoder = new Encoder(RobotMap.SHOOTER_ENCODER_PORT_A, RobotMap.SHOOTER_ENCODER_PORT_B);

        shooterEncoder.reset();
        shooterEncoder.setDistancePerPulse(Util.tickToFeet(CONSTANTS.SHOOTER_ENCODER_TICKS, CONSTANTS.SHOOTER_WHEEL_DIAMETER));

        
        leftShooterTalon.configPeakOutputVoltage(+12.0f, 0);
        rightShooterTalon.configPeakOutputVoltage(+12.0f, 0);

        SmartDashboard.putNumber("Shooter p", CONSTANTS.SHOOTER_P);
        SmartDashboard.putNumber("Shooter I", CONSTANTS.SHOOTER_I);
        SmartDashboard.putNumber("Shooter D", CONSTANTS.SHOOTER_D);
        SmartDashboard.putNumber("Shooter feed forward", CONSTANTS.FEED_FORWARD);

        leftShooterTalon.setIZone(CONSTANTS.SHOOTER_I_ZONE);
        rightShooterTalon.setIZone(CONSTANTS.SHOOTER_I_ZONE);

        leftShooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        rightShooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);

        leftShooterTalon.configEncoderCodesPerRev(CONSTANTS.SHOOTER_ENCODER_TICKS);
        rightShooterTalon.configEncoderCodesPerRev(CONSTANTS.SHOOTER_ENCODER_TICKS);

        leftShooterTalon.changeControlMode(TalonControlMode.Speed);
        rightShooterTalon.changeControlMode(TalonControlMode.Speed);

        leftShooterTalon.enableControl();
        rightShooterTalon.enableControl();

        leftShooterTalon.enable();
        rightShooterTalon.enable();

        leftShooterTalon.configNominalOutputVoltage(0, 0);
        rightShooterTalon.configNominalOutputVoltage(0, 0);
    }

    public void prepWallShot(double setPoint) {
        setShooterSpeeds(setPoint, true);
    }

    public boolean shooterAtSpeed() {
        double total_error = Math.abs(leftShooterTalon.getError()) + Math.abs(rightShooterTalon.getError());
        return total_error < 200;
    }

    public void setShooterSpeeds(double targetSpeed, boolean withPID) {
        if (withPID) {
            // if pid should be used then we have to switch the talons to
            // velocity mode
            leftShooterTalon.changeControlMode(TalonControlMode.Speed);
            rightShooterTalon.changeControlMode(TalonControlMode.Speed);
            // update the pid and feedforward values
            leftShooterTalon.setP(SmartDashboard.getNumber("Shooter p"));
            leftShooterTalon.setI(SmartDashboard.getNumber("Shooter i"));
            leftShooterTalon.setD(SmartDashboard.getNumber("Shooter d"));
            rightShooterTalon.setP(SmartDashboard.getNumber("Shooter p"));
            rightShooterTalon.setI(SmartDashboard.getNumber("Shooter i"));
            rightShooterTalon.setD(SmartDashboard.getNumber("Shooter d"));
            leftShooterTalon.setF(SmartDashboard.getNumber("FeedForward"));
            rightShooterTalon.setF(SmartDashboard.getNumber("FeedForward"));
            // set the speeds (THEY ARE IN RPMS)
            leftShooterTalon.set(targetSpeed);
            rightShooterTalon.set(targetSpeed);

        } else if (!autoFire && !prepShot) {
            // if shooters is not autofiring or prepping the shot then use
            // inputs given, including 0
            leftShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
            rightShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
            leftShooterTalon.set(targetSpeed);
            rightShooterTalon.set(targetSpeed);
        }
    }

    public void updateSmartDash() {
        // update the smartdashbaord with values
//        SmartDashboard.putBoolean("Does shooter have ball", hasBall());
//        SmartDashboard.putBoolean("Is auto firing", autoFire);
//        SmartDashboard.putBoolean("Is preparing shot", prepShot);
    }

    public void debug() {
    	  //2017 Debug dashboard
    	  SmartDashboard.putNumber("LeftShooterSpeed", leftShooterTalon.get());
	      SmartDashboard.putNumber("RightShooterSpeed", rightShooterTalon.get());
	      SmartDashboard.putNumber("Left Shooter Error", leftShooterTalon.getClosedLoopError());
	      SmartDashboard.putNumber("Right Shooter Error", -rightShooterTalon.getClosedLoopError());
	      //Note: Make a getShooterHeight(); method in ShakerShooter
//	      SmartDashboard.putString("Current shooter setpoint", getShooterHeight().toString());
	      SmartDashboard.putNumber("left output voltage", leftShooterTalon.getOutputVoltage());
	      SmartDashboard.putNumber("left speed", -leftShooterTalon.getEncVelocity());
	      SmartDashboard.putNumber("right output voltage", rightShooterTalon.getOutputVoltage());
	      SmartDashboard.putNumber("right speed", rightShooterTalon.getEncVelocity());
	      SmartDashboard.putNumber("Right error", rightShooterTalon.getError());
	      SmartDashboard.putNumber("Left error", leftShooterTalon.getError());
	      
	      //2016 Debug dashboard
//        SmartDashboard.putNumber("LeftShooterSpeed", leftShooterTalon.getEncVelocity());
//        SmartDashboard.putNumber("RightShooterSpeed", rightShooterTalon.getEncVelocity());
//        SmartDashboard.putNumber("Left Shooter Error", leftShooterTalon.getClosedLoopError());
//        SmartDashboard.putNumber("Right Shooter Error", -rightShooterTalon.getClosedLoopError());
//        SmartDashboard.putString("Current shooter setpoint", getShooterHeight().toString());
//        SmartDashboard.putNumber("left output voltage", leftShooterTalon.getOutputVoltage());
//        SmartDashboard.putNumber("left speed", -leftShooterTalon.getEncVelocity());
//        SmartDashboard.putNumber("right output voltage", rightShooterTalon.getOutputVoltage());
//        SmartDashboard.putNumber("right speed", rightShooterTalon.getEncVelocity());
//        SmartDashboard.putNumber("Right error", rightShooterTalon.getError());
//        SmartDashboard.putNumber("Left error", leftShooterTalon.getError());
    }

    public void reset() {
        // stop the motors
        stopMotors();
        // reset the PID on the Talons
        leftShooterTalon.reset();
        rightShooterTalon.reset();
    }

    public void stopMotors() {
        // set the motors to 0 to stop
        leftShooterTalon.set(0);
        rightShooterTalon.set(0);
    }

    public void disable() {
        // disable code will stop motors
        stopMotors();
        prepShot = false;
        SmartDashboard.putNumber("right speed", rightShooterTalon.getSpeed());
        SmartDashboard.putNumber("left speed", leftShooterTalon.getSpeed());
    }

}