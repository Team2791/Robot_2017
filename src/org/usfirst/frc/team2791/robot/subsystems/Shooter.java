/**
* Shooter class for Shaker Robotics' 2017 robot 
*
* @author Lukas Velikov
* @version pre
*/
package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.Util;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {

    protected Encoder shooterEncoder = null;

    private CANTalon leftShooterTalon = null;
    private CANTalon rightShooterTalon = null;
    //private CANTalon powerShooterTalon = null; //Potential third Talon for extra shooter power

    protected boolean prepShot = false;
    protected boolean autoFire = false;

    public void initDefaultCommand() {

        this.leftShooterTalon = new CANTalon(RobotMap.LEFT_SHOOTER_TALON_PORT);
        this.rightShooterTalon = new CANTalon(RobotMap.RIGHT_SHOOTER_TALON_PORT);
        //this.powerShooterTalon = new CANTalon(RobotMap.CENTER_SHOOTER_TALON_PORT);

        this.shooterEncoder = new Encoder(RobotMap.SHOOTER_ENCODER_PORT_A, RobotMap.SHOOTER_ENCODER_PORT_B);

        shooterEncoder.reset();
        shooterEncoder.setDistancePerPulse(Util.tickToFeet(CONSTANTS.SHOOTER_ENCODER_TICKS, CONSTANTS.SHOOTER_WHEEL_DIAMETER));

        leftShooterTalon.configPeakOutputVoltage(+12.0f, 0);
        rightShooterTalon.configPeakOutputVoltage(+12.0f, 0);

        SmartDashboard.putNumber("Shooter P", CONSTANTS.SHOOTER_P);
        SmartDashboard.putNumber("Shooter I", CONSTANTS.SHOOTER_I);
        SmartDashboard.putNumber("Shooter D", CONSTANTS.SHOOTER_D);
        SmartDashboard.putNumber("Shooter Feed Forward", CONSTANTS.FEED_FORWARD);

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

    public void prepWallShot() {
        setShooterSpeeds(CONSTANTS.SHOOTER_SET_POINT, true);
    }
    
    public boolean shooterAtSpeed() {
        double total_error = Math.abs(leftShooterTalon.getError()) + Math.abs(rightShooterTalon.getError());
        return total_error < 200;
    }

    public void setShooterSpeeds(double targetSpeed, boolean withPID) {
        if (withPID) {
            //If PID is used then we have to switch CANTalons to velocity mode
            leftShooterTalon.changeControlMode(TalonControlMode.Speed);
            rightShooterTalon.changeControlMode(TalonControlMode.Speed);
            
            //Update the PID and FeedForward values
            leftShooterTalon.setP(SmartDashboard.getNumber("Shooter p"));
            leftShooterTalon.setI(SmartDashboard.getNumber("Shooter i"));
            leftShooterTalon.setD(SmartDashboard.getNumber("Shooter d"));
            rightShooterTalon.setP(SmartDashboard.getNumber("Shooter p"));
            rightShooterTalon.setI(SmartDashboard.getNumber("Shooter i"));
            rightShooterTalon.setD(SmartDashboard.getNumber("Shooter d"));
            leftShooterTalon.setF(SmartDashboard.getNumber("FeedForward"));
            rightShooterTalon.setF(SmartDashboard.getNumber("FeedForward"));
            
            //Set speeds (IN RPMS)
            leftShooterTalon.set(targetSpeed);
            rightShooterTalon.set(targetSpeed);

        } else if (!autoFire && !prepShot) {
            //If shooter is not autofiring or prepping the shot, use inputs given (including 0)
            leftShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
            rightShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
            leftShooterTalon.set(targetSpeed);
            rightShooterTalon.set(targetSpeed);
        }
    }

    public void updateSmartDash() {
        SmartDashboard.putBoolean("Does shooter have ball", hasBall());
        SmartDashboard.putBoolean("Is auto firing", autoFire);
        SmartDashboard.putBoolean("Is preparing shot", prepShot);
    }

    private boolean hasBall() {
		return false;
	}

	public void debug() {
    	  SmartDashboard.putNumber("LeftShooterSpeed", leftShooterTalon.getSpeed());
	      SmartDashboard.putNumber("RightShooterSpeed", rightShooterTalon.getSpeed());
	      SmartDashboard.putNumber("Left Shooter Error", leftShooterTalon.getClosedLoopError());
	      SmartDashboard.putNumber("Right Shooter Error", -rightShooterTalon.getClosedLoopError());
	      SmartDashboard.putString("Current shooter setpoint", Double.toString(getShooterHeight()));
	      SmartDashboard.putNumber("left output voltage", leftShooterTalon.getOutputVoltage());
	      SmartDashboard.putNumber("left speed", -leftShooterTalon.getEncVelocity());
	      SmartDashboard.putNumber("right output voltage", rightShooterTalon.getOutputVoltage());
	      SmartDashboard.putNumber("right speed", rightShooterTalon.getEncVelocity());
	      SmartDashboard.putNumber("Right error", rightShooterTalon.getError());
	      SmartDashboard.putNumber("Left error", leftShooterTalon.getError());
	      
//	      leftShooterTalon.set(SmartDashboard.getNumber("setpoint"));
//	      rightShooterTalon.set(SmartDashboard.getNumber("setpoint"));
//	      powerShooterTalon.set(SmartDashboard.getNumber("setpoint"));
    }

    private double getShooterHeight() {
		return 0;
	}

	public void reset() {
        stopMotors();
        leftShooterTalon.reset();
        rightShooterTalon.reset();
    }

    public void stopMotors() { //Set the motors to 0 to stop
        leftShooterTalon.set(0);
        rightShooterTalon.set(0);
    }

    public void disable() {
        stopMotors();
        prepShot = false;
        
        SmartDashboard.putNumber("right speed", rightShooterTalon.getSpeed());
        SmartDashboard.putNumber("left speed", leftShooterTalon.getSpeed());
    }
}
