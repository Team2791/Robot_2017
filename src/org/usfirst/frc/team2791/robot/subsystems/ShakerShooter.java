/** Shooter class for Shaker Robotics' 2017 robot 
*
* @author Lukas Velikov
* @version pre
*/
package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.commands.ShootWithJoystick;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.DelayedBoolean;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerShooter extends Subsystem {
	
	private final double ERROR_THRESHOLD = 40;//25
	private final double SHOOTER_GOOD_TIME = 0.1;
	private DelayedBoolean shooterGoodDelayedBoolean = new DelayedBoolean(SHOOTER_GOOD_TIME);

    protected Encoder shooterEncoder = null;

    private CANTalon primaryShooterTalon = null;
    private CANTalon followerShooterTalonA = null;
    //private CANTalon powerShooterTalon = null; //Potential third Talon for extra shooter power

    private Solenoid shooterSolenoid;

    protected boolean autoFire = false;
    protected boolean longShot = false;
        
    public ShakerShooter() {
    	shooterSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.SHOOTER_CHANNEL);
    	shooterSolenoid.set(false);//default state
    	
        primaryShooterTalon = new CANTalon(RobotMap.PRIMARY_SHOOTER_TALON_PORT);
        primaryShooterTalon.setInverted(true);
        followerShooterTalonA = new CANTalon(RobotMap.FOLLOWER_SHOOTER_TALON_PORT);
        primaryShooterTalon.configPeakOutputVoltage(0, -12.0f);
        followerShooterTalonA.configPeakOutputVoltage(0, -12.0f);
        
        if(SmartDashboard.getNumber("Shooter P", -2791) == -2791){
	        SmartDashboard.putNumber("Shooter P", CONSTANTS.SHOOTER_P);
	        SmartDashboard.putNumber("Shooter I", CONSTANTS.SHOOTER_I);
	        SmartDashboard.putNumber("Shooter D", CONSTANTS.SHOOTER_D);
	        SmartDashboard.putNumber("Shooter FeedForward", CONSTANTS.SHOOTER_FEED_FORWARD);
	        SmartDashboard.putNumber("Shooter Setpoint", CONSTANTS.SHOOTER_SET_POINT);
	        
	        SmartDashboard.putNumber("Shooter Long P", CONSTANTS.SHOOTER_LONG_P);
	        SmartDashboard.putNumber("Shooter Long I", CONSTANTS.SHOOTER_LONG_I);
	        SmartDashboard.putNumber("Shooter Long D", CONSTANTS.SHOOTER_LONG_D);
	        SmartDashboard.putNumber("Shooter Long FeedForward", CONSTANTS.SHOOTER_LONG_FEED_FORWARD);
	        SmartDashboard.putNumber("Shooter Long Setpoint", CONSTANTS.SHOOTER_LONG_SET_POINT);
        }

        primaryShooterTalon.setIZone(CONSTANTS.SHOOTER_I_ZONE);
        primaryShooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        primaryShooterTalon.configEncoderCodesPerRev(CONSTANTS.SHOOTER_ENCODER_TICKS);
        primaryShooterTalon.changeControlMode(TalonControlMode.Speed);

        followerShooterTalonA.changeControlMode(TalonControlMode.Follower);
        followerShooterTalonA.set(RobotMap.PRIMARY_SHOOTER_TALON_PORT);

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
    
    public void initDefaultCommand(){
    	
    }
    
    public void setShooterSolenoidState(boolean key){
    	shooterSolenoid.set(key);
    }
    
    public void prepWallShot() {
    	longShot = false;
        setShooterSpeedsPID(SmartDashboard.getNumber("Shooter Setpoint", 0));
    }
    public void prepLongShot() {
    	longShot = true;
    	setShooterSpeedsPID(SmartDashboard.getNumber("Shooter Long Setpoint", 0));
	}

    public boolean atSpeed() {
        return shooterGoodDelayedBoolean.update(
        		Math.abs(primaryShooterTalon.getError()) < ERROR_THRESHOLD);
    }

	public void setShooterSpeedsPID(double targetSpeed) {
        //If PID is used then we have to switch CANTalons to velocity mode
        primaryShooterTalon.changeControlMode(TalonControlMode.Speed);
        
        //Update the PID and FeedForward values
        
        if(longShot){
        	primaryShooterTalon.setP(SmartDashboard.getNumber("Shooter Long P", 0));
	        primaryShooterTalon.setI(SmartDashboard.getNumber("Shooter Long I", 0));
	        primaryShooterTalon.setD(SmartDashboard.getNumber("Shooter Long D", 0));
	        primaryShooterTalon.setF(SmartDashboard.getNumber("Shooter Long FeedForward", 0));
        }else{
	        primaryShooterTalon.setP(SmartDashboard.getNumber("Shooter P", 0));
	        primaryShooterTalon.setI(SmartDashboard.getNumber("Shooter I", 0));
	        primaryShooterTalon.setD(SmartDashboard.getNumber("Shooter D", 0));
	        primaryShooterTalon.setF(SmartDashboard.getNumber("Shooter FeedForward", 0));
        }

//            //Set speeds (IN RPMS)
//            primaryShooterTalon.setP(CONSTANTS.SHOOTER_P);
//            primaryShooterTalon.setI(CONSTANTS.SHOOTER_I);
//            primaryShooterTalon.setD(CONSTANTS.SHOOTER_D);
//            primaryShooterTalon.setF(CONSTANTS.SHOOTER_FEED_FORWARD);
        
        primaryShooterTalon.set(targetSpeed);
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
    	primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
    	primaryShooterTalon.set(0);
    }

    public void disable() {
    	primaryShooterTalon.disableControl();
        
//        SmartDashboard.putNumber("right speed", followerShooterTalonA.getSpeed());
//        SmartDashboard.putNumber("left speed", primaryShooterTalon.getSpeed());
    }
    public double getCurrentUsage(){
    	return primaryShooterTalon.getOutputCurrent()+followerShooterTalonA.getOutputCurrent();
    }
	@SuppressWarnings("deprecation")
	public void setVBusWithTrigger(double Vbus) {
		// TODO Auto-generated method stub
		primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);//percent v bus
        followerShooterTalonA.changeControlMode(TalonControlMode.Follower);
        followerShooterTalonA.set(RobotMap.PRIMARY_SHOOTER_TALON_PORT);
		SmartDashboard.putNumber("Shooter percent Vbus",Vbus);
        primaryShooterTalon.set(Vbus);
       
	}
	public double getError() {
		// TODO Auto-generated method stub
		return primaryShooterTalon.getError();
	}
}
