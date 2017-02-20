/** Shooter class for Shaker Robotics' 2017 robot 
*
* @author Lukas Velikov
* @version pre
*/
package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerShooter extends Subsystem {
	private final double ERROR_THRESHOLD = 200;

    protected Encoder shooterEncoder = null;

    private CANTalon primaryShooterTalon = null;
    private CANTalon followerShooterTalonA = null;
    //private CANTalon powerShooterTalon = null; //Potential third Talon for extra shooter power

    private Solenoid shooterSolenoid;

    protected boolean autoFire = false;

    public void initDefaultCommand() {
    	shooterSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.SHOOTER_CHANNEL);
    	shooterSolenoid.set(false);//default state
    	
        primaryShooterTalon = new CANTalon(RobotMap.LEFT_SHOOTER_TALON_PORT);
        followerShooterTalonA = new CANTalon(RobotMap.RIGHT_SHOOTER_TALON_PORT);
        primaryShooterTalon.configPeakOutputVoltage(+12.0f, 0);
        followerShooterTalonA.configPeakOutputVoltage(+12.0f, 0);
        
        if(SmartDashboard.getNumber("Shooter P", -2791) == -2791){
	        SmartDashboard.putNumber("Shooter P", CONSTANTS.SHOOTER_P);
	        SmartDashboard.putNumber("Shooter I", CONSTANTS.SHOOTER_I);
	        SmartDashboard.putNumber("Shooter D", CONSTANTS.SHOOTER_D);
	        SmartDashboard.putNumber("Shooter Feed Forward", CONSTANTS.SHOOTER_FEED_FORWARD);
	        SmartDashboard.putNumber("Shooter Setpoint", 0);
        }

        primaryShooterTalon.setIZone(CONSTANTS.SHOOTER_I_ZONE);
        primaryShooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        primaryShooterTalon.configEncoderCodesPerRev(CONSTANTS.SHOOTER_ENCODER_TICKS);
        primaryShooterTalon.changeControlMode(TalonControlMode.Speed);

        followerShooterTalonA.changeControlMode(TalonControlMode.Follower);
        followerShooterTalonA.set(RobotMap.LEFT_SHOOTER_TALON_PORT);

        // NOT sure what this does
        primaryShooterTalon.enableControl();
        followerShooterTalonA.enableControl();
        
        primaryShooterTalon.enable();
        followerShooterTalonA.enable();
        
//        primaryShooterTalon.setP(CONSTANTS.SHOOTER_P);
//        primaryShooterTalon.setI(CONSTANTS.SHOOTER_I);
//        primaryShooterTalon.setD(CONSTANTS.SHOOTER_D);
        
        primaryShooterTalon.configNominalOutputVoltage(0, 0);
        followerShooterTalonA.configNominalOutputVoltage(0, 0);
    }
    public void setShooterSolenoidState(boolean key){
    	shooterSolenoid.set(key);
    }
    public void prepWallShot() {
        setShooterSpeedsPID(CONSTANTS.SHOOTER_SET_POINT);
    }
    
    public boolean atSpeed() {
        return Math.abs(primaryShooterTalon.getError()) < ERROR_THRESHOLD;
    }

	public void setShooterSpeedsPID(double targetSpeed) {
        //If PID is used then we have to switch CANTalons to velocity mode
        primaryShooterTalon.changeControlMode(TalonControlMode.Speed);
        
        //Update the PID and FeedForward values
        
        primaryShooterTalon.setP(SmartDashboard.getNumber("Shooter P", 0));
        primaryShooterTalon.setI(SmartDashboard.getNumber("Shooter I", 0));
        primaryShooterTalon.setD(SmartDashboard.getNumber("Shooter D", 0));
        primaryShooterTalon.setF(SmartDashboard.getNumber("Shooter FeedForward", 0));

//            //Set speeds (IN RPMS)
//            primaryShooterTalon.setP(CONSTANTS.SHOOTER_P);
//            primaryShooterTalon.setI(CONSTANTS.SHOOTER_I);
//            primaryShooterTalon.setD(CONSTANTS.SHOOTER_D);
//            primaryShooterTalon.setF(CONSTANTS.SHOOTER_FEED_FORWARD);
        
        primaryShooterTalon.set(SmartDashboard.getNumber("Shooter Setpoint", 0));
        System.out.println("Coming up to speed and my error is "+primaryShooterTalon.getError());
        debug();
    }
	
	public void setShooterSpeedVBus(double vbus) {
        //If shooter is not autofiring or prepping the shot, use inputs given (including 0)
        primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
        primaryShooterTalon.set(vbus);
	}

    public void updateSmartDash() {
        SmartDashboard.putBoolean("Is auto firing", autoFire);
    }

	public void debug() {
		
		SmartDashboard.putNumber("Primary Talon Speed",primaryShooterTalon.getSpeed());
		SmartDashboard.putNumber("Primary Talon Error (Setpoint)", primaryShooterTalon.getError());
		SmartDashboard.putNumber("Primary Talon Closed Loop Error (Sensor value)", primaryShooterTalon.getClosedLoopError());
    	 
		
		/*
    	   * SmartDashboard.putNumber("LeftShooterSpeed", primaryShooterTalon.getSpeed());
    	   
	      SmartDashboard.putNumber("RightShooterSpeed", followerShooterTalonA.getSpeed());
	      SmartDashboard.putNumber("Left Shooter Error", primaryShooterTalon.getClosedLoopError());
	      SmartDashboard.putNumber("Right Shooter Error", -followerShooterTalonA.getClosedLoopError());
	      SmartDashboard.putString("Current shooter setpoint", getShooterHeight());
	      SmartDashboard.putNumber("left output voltage", primaryShooterTalon.getOutputVoltage());
	      SmartDashboard.putNumber("left speed", -primaryShooterTalon.getEncVelocity());
	      SmartDashboard.putNumber("right output voltage", followerShooterTalonA.getOutputVoltage());
	      SmartDashboard.putNumber("right speed", followerShooterTalonA.getEncVelocity());
	      SmartDashboard.putNumber("Right error", followerShooterTalonA.getError());
	      SmartDashboard.putNumber("Left error", primaryShooterTalon.getError());
	      */
//	      leftShooterTalon.set(SmartDashboard.getNumber("setpoint"));
//	      rightShooterTalon.set(SmartDashboard.getNumber("setpoint"));
//	      powerShooterTalon.set(SmartDashboard.getNumber("setpoint"));
    }

    private String getShooterHeight() {
		if(shooterSolenoid.get())
			return "far";
    	return "close";
	}

	public void reset() {
        stopMotors();
        primaryShooterTalon.reset();
        followerShooterTalonA.reset();
    }

    public void stopMotors() { //Set the motors to 0 to stop
    	primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);//percent v bus
        followerShooterTalonA.changeControlMode(TalonControlMode.PercentVbus);
    	
    	primaryShooterTalon.set(0);
        followerShooterTalonA.set(0);
    }

    public void disable() {
    	primaryShooterTalon.disableControl();
        followerShooterTalonA.disableControl();
        
//        SmartDashboard.putNumber("right speed", followerShooterTalonA.getSpeed());
//        SmartDashboard.putNumber("left speed", primaryShooterTalon.getSpeed());
    }
    public double getCurrentUsage(){
    	return primaryShooterTalon.getOutputCurrent()+followerShooterTalonA.getOutputCurrent();
    }
	public void setTrigger(double combinedLeft) {
		// TODO Auto-generated method stub
		primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);//percent v bus
        followerShooterTalonA.changeControlMode(TalonControlMode.PercentVbus);
		
        primaryShooterTalon.set(combinedLeft);
        followerShooterTalonA.set(combinedLeft);
	}
	public double getError() {
		// TODO Auto-generated method stub
		return primaryShooterTalon.getError();
	}
}
