package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.Util;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Encoder;

public class ShakerShooter {

	protected Encoder leftShooterEncoder = null;
	protected Encoder rightShooterEncoder = null;	

	private CANTalon leftShooterTalon = null;
	private CANTalon rightShooterTalon = null;	

	public void initDefaultCommand(){

		this.leftShooterTalon = new CANTalon(RobotMap.LEFT_SHOOTER_TALON_PORT);
		this.rightShooterTalon = new CANTalon(RobotMap.RIGHT_SHOOTER_TALON_PORT);

		this.leftShooterEncoder = new Encoder(RobotMap.LEFT_SHOOTER_ENCODER_PORT_A, RobotMap.LEFT_SHOOTER_ENCODER_PORT_B);
		this.rightShooterEncoder = new Encoder(RobotMap.RIGHT_SHOOTER_ENCODER_PORT_A, RobotMap.RIGHT_SHOOTER_ENCODER_PORT_B);

		leftShooterEncoder.reset();
		rightShooterEncoder.reset();

		leftShooterEncoder.setDistancePerPulse(Util.tickToFeet(CONSTANTS.SHOOTER_ENCODER_TICKS, CONSTANTS.SHOOTER_WHEEL_DIAMETER));
		rightShooterEncoder.setDistancePerPulse(Util.tickToFeet(CONSTANTS.SHOOTER_ENCODER_TICKS, CONSTANTS.SHOOTER_WHEEL_DIAMETER));

		leftShooterTalon.configPeakOutputVoltage(+12.0f,0);
		rightShooterTalon.configPeakOutputVoltage(+12.0f,0);
		
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
	
	public void wallShot(){
		SmartDashboard.
	}
	
	}
}