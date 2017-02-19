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

public class ShakerShooter extends Subsystem{

    protected Encoder shooterEncoder = null;

    private CANTalon primaryShooterTalon = null;
    private CANTalon followerShooterTalonA = null;
    //private CANTalon powerShooterTalon = null; //Potential third Talon for extra shooter power

    private Solenoid shooterSolenoid;
    
    protected boolean prepShot = false;
    protected boolean autoFire = false;

    public void initDefaultCommand() {
    	shooterSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.SHOOTER_CHANNEL);
    	shooterSolenoid.set(false);//default state
    	
        primaryShooterTalon = new CANTalon(RobotMap.LEFT_SHOOTER_TALON_PORT);
        followerShooterTalonA = new CANTalon(RobotMap.RIGHT_SHOOTER_TALON_PORT);
        //this.powerShooterTalon = new CANTalon(RobotMap.CENTER_SHOOTER_TALON_PORT);

//        this.shooterEncoder = new Encoder(RobotMap.SHOOTER_ENCODER_PORT_A, RobotMap.SHOOTER_ENCODER_PORT_B);

//        shooterEncoder.reset();
//        shooterEncoder.setDistancePerPulse(Util.tickToFeet(CONSTANTS.SHOOTER_ENCODER_TICKS, CONSTANTS.SHOOTER_WHEEL_DIAMETER));

        primaryShooterTalon.configPeakOutputVoltage(+12.0f, 0);
        followerShooterTalonA.configPeakOutputVoltage(+12.0f, 0);

//        SmartDashboard.putNumber("Shooter P", CONSTANTS.SHOOTER_P);
//        SmartDashboard.putNumber("Shooter I", CONSTANTS.SHOOTER_I);
//        SmartDashboard.putNumber("Shooter D", CONSTANTS.SHOOTER_D);
//        SmartDashboard.putNumber("Shooter Feed Forward", CONSTANTS.FEED_FORWARD);

        primaryShooterTalon.setIZone(CONSTANTS.SHOOTER_I_ZONE);
        followerShooterTalonA.setIZone(CONSTANTS.SHOOTER_I_ZONE);
        
        primaryShooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        followerShooterTalonA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        
        primaryShooterTalon.configEncoderCodesPerRev(CONSTANTS.SHOOTER_ENCODER_TICKS);
        followerShooterTalonA.configEncoderCodesPerRev(CONSTANTS.SHOOTER_ENCODER_TICKS);
        
        primaryShooterTalon.changeControlMode(TalonControlMode.Speed);//thread.sleep for half a second
        followerShooterTalonA.changeControlMode(TalonControlMode.Speed);//should be follower
               
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
    	prepShot = true;
        setShooterSpeeds(CONSTANTS.SHOOTER_SET_POINT, true);
    }
    
    public boolean shooterAtSpeed() {
        double total_error = Math.abs(primaryShooterTalon.getError()) + Math.abs(followerShooterTalonA.getError());
        return total_error < 200;
    }

    @SuppressWarnings("deprecation")
	public void setShooterSpeeds(double targetSpeed, boolean withPID) {
        if (withPID) {
            //If PID is used then we have to switch CANTalons to velocity mode
            primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);//percent v bus
            followerShooterTalonA.changeControlMode(TalonControlMode.PercentVbus);
            
            //Update the PID and FeedForward values
            
//            primaryShooterTalon.setP(SmartDashboard.getNumber("Shooter p"));
//            primaryShooterTalon.setI(SmartDashboard.getNumber("Shooter i"));
//            primaryShooterTalon.setD(SmartDashboard.getNumber("Shooter d"));
//            followerShooterTalonA.setP(SmartDashboard.getNumber("Shooter p"));
//            followerShooterTalonA.setI(SmartDashboard.getNumber("Shooter i"));
//            followerShooterTalonA.setD(SmartDashboard.getNumber("Shooter d"));
//            primaryShooterTalon.setF(SmartDashboard.getNumber("FeedForward"));
//            followerShooterTalonA.setF(SmartDashboard.getNumber("FeedForward"));
            
            
            //Set speeds (IN RPMS)
            primaryShooterTalon.setP(CONSTANTS.SHOOTER_P);
            primaryShooterTalon.setI(CONSTANTS.SHOOTER_I);
            primaryShooterTalon.setD(CONSTANTS.SHOOTER_D);
            primaryShooterTalon.setF(CONSTANTS.SHOOTER_FEED_FORWARD);
            
            followerShooterTalonA.setP(CONSTANTS.SHOOTER_P);
            followerShooterTalonA.setI(CONSTANTS.SHOOTER_I);
            followerShooterTalonA.setD(CONSTANTS.SHOOTER_D);
            followerShooterTalonA.setF(CONSTANTS.SHOOTER_FEED_FORWARD);
            
            primaryShooterTalon.set(CONSTANTS.SHOOTER_SET_POINT);
            followerShooterTalonA.set(CONSTANTS.SHOOTER_SET_POINT);
            
//            primaryShooterTalon.setF(CONSTANTS.SHOOTER_FEED_FORWARD);
//            primaryShooterTalon.setSetpoint(targetSpeed);
//            followerShooterTalonA.set(primaryShooterTalon.getDeviceID());
            
        } else if (!autoFire && !prepShot) {
            //If shooter is not autofiring or prepping the shot, use inputs given (including 0)
            primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
            followerShooterTalonA.changeControlMode(TalonControlMode.PercentVbus);
            primaryShooterTalon.set(targetSpeed);
            followerShooterTalonA.set(targetSpeed);
        }
        System.out.println("Coming up to speed and my error is "+primaryShooterTalon.getError());
    }

    public void updateSmartDash() {
        SmartDashboard.putBoolean("Is auto firing", autoFire);
        SmartDashboard.putBoolean("Is preparing shot", prepShot);
    }

	public void debug() {
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
        followerShooterTalonA.disableControl();;
        prepShot = false;
        
//        SmartDashboard.putNumber("right speed", followerShooterTalonA.getSpeed());
//        SmartDashboard.putNumber("left speed", primaryShooterTalon.getSpeed());
    }
    public double getCurrentUsage(){
    	return primaryShooterTalon.getOutputCurrent();
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
