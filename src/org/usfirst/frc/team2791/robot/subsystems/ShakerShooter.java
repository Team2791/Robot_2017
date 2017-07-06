/** Shooter class for Shaker Robotics' 2017 robot 
 *
 * @author Lukas Velikov
 * @version pre
 */
package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.DelayedBoolean;
import org.usfirst.frc.team2791.robot.util.Util;
import org.usfirst.frc.team2791.robot.util.vision.ShooterLookupTable;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class corresponds to the shooter system. There are two 775 pros
 * controlled by two Talon SRX speed controllers. A 128-count Greyhill encoder
 * on the shooter axle is wired into one of the Talon SRXs. That one is set to
 * be the master and the other is set to be a follower. PID was found
 * experimentally after starting from the velocity closed-loop walkthrough in
 * the CTRE software reference manual. The shooter has a piston-actuated hood
 * which is down by default.
 * 
 * @author team2791: See Robot.java for contact info
 */
public class ShakerShooter extends Subsystem {

	private final double ERROR_THRESHOLD = 40;// 40
	private final double SHOOTER_GOOD_TIME = 0.1;
	private DelayedBoolean shooterGoodDelayedBoolean = new DelayedBoolean(SHOOTER_GOOD_TIME);

	protected Encoder shooterEncoder = null;

	public CANTalon primaryShooterTalon = null; // has encoder input
	private CANTalon followerShooterTalonA = null;
	private CANTalon followerShooterTalonB = null;

	private Solenoid shooterSolenoid;

	protected boolean closeShot = false, visionShot = false;
	public boolean primaryHasP = false;

	public ShooterLookupTable lookUpTable = new ShooterLookupTable();

	public ShakerShooter() {

		shooterSolenoid = new Solenoid(RobotMap.PCM_MODULE, RobotMap.SHOOTER_CHANNEL);

		primaryShooterTalon = new CANTalon(RobotMap.PRIMARY_SHOOTER_TALON_PORT);
		primaryShooterTalon.setInverted(true);
//		primaryShooterTalon.setNominalClosedLoopVoltage(12.0);

		followerShooterTalonA = new CANTalon(RobotMap.FOLLOWER_SHOOTER_TALON_PORT_A);
//		followerShooterTalonA.setNominalClosedLoopVoltage(12.0);
		followerShooterTalonB = new CANTalon(RobotMap.FOLLOWER_SHOOTER_TALON_PORT_B); 
//		followerShooterTalonB.setNominalClosedLoopVoltage(12.0);


		primaryShooterTalon.configPeakOutputVoltage(0, -12.0f);
		followerShooterTalonA.configPeakOutputVoltage(0, -12.0f);
		followerShooterTalonB.configPeakOutputVoltage(0, -12.0f); 

		if (SmartDashboard.getNumber("Shooter P", -2791) == -2791) {
			SmartDashboard.putNumber("Shooter P", CONSTANTS.SHOOTER_P);
			SmartDashboard.putNumber("Shooter I", CONSTANTS.SHOOTER_I);
			SmartDashboard.putNumber("Shooter D", CONSTANTS.SHOOTER_D);
			SmartDashboard.putNumber("Shooter FeedForward", CONSTANTS.SHOOTER_FEED_FORWARD);
			SmartDashboard.putNumber("Shooter Setpoint", CONSTANTS.SHOOTER_SET_POINT);
			SmartDashboard.putNumber("Shooter I Zone", CONSTANTS.SHOOTER_I_ZONE);
			
			//not using these
			SmartDashboard.putNumber("Shooter Long P", CONSTANTS.SHOOTER_LONG_P);
			SmartDashboard.putNumber("Shooter Long I", CONSTANTS.SHOOTER_LONG_I);
			SmartDashboard.putNumber("Shooter Long D", CONSTANTS.SHOOTER_LONG_D);
			SmartDashboard.putNumber("Shooter Long FeedForward", CONSTANTS.SHOOTER_LONG_FEED_FORWARD);
			SmartDashboard.putNumber("Shooter Long Setpoint", CONSTANTS.SHOOTER_LONG_SET_POINT);

			SmartDashboard.putNumber("Shooter Sweeet Setpoint", CONSTANTS.SHOOTER_VISION_SWEET_SET_POINT);
			
			SmartDashboard.putNumber("Bang Bang Threshold", 1000);

			primaryShooterTalon.setP(SmartDashboard.getNumber("Shooter Vision P", CONSTANTS.SHOOTER_VISION_P));
			primaryShooterTalon.setI(SmartDashboard.getNumber("Shooter Vision I", CONSTANTS.SHOOTER_VISION_I));
			primaryShooterTalon.setD(SmartDashboard.getNumber("Shooter Vision D", CONSTANTS.SHOOTER_VISION_D));
			primaryShooterTalon.setF(
					SmartDashboard.getNumber("Shooter Vision FeedForward", CONSTANTS.SHOOTER_VISION_FEED_FORWARD));
		}

		primaryShooterTalon.setIZone(CONSTANTS.SHOOTER_I_ZONE);

		primaryShooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		primaryShooterTalon.configEncoderCodesPerRev(CONSTANTS.SHOOTER_ENCODER_TICKS);
		primaryShooterTalon.changeControlMode(TalonControlMode.Speed); 

		followerShooterTalonA.changeControlMode(TalonControlMode.Follower);
		followerShooterTalonA.set(RobotMap.PRIMARY_SHOOTER_TALON_PORT);

		followerShooterTalonB.changeControlMode(TalonControlMode.Follower); 
		followerShooterTalonB.set(RobotMap.PRIMARY_SHOOTER_TALON_PORT); 

		primaryShooterTalon.enableControl();
		followerShooterTalonA.enableControl();
		followerShooterTalonB.enableControl(); 

		primaryShooterTalon.enable();
		followerShooterTalonA.enable();
		followerShooterTalonB.enable(); 

		primaryShooterTalon.configNominalOutputVoltage(0, 0);
		followerShooterTalonA.configNominalOutputVoltage(0, 0);
		followerShooterTalonB.configNominalOutputVoltage(0, 0);

	}

	public void initDefaultCommand() {}

	/**
	 * Initiates a shot with a speed based on the distance from the boiler and the Look Up Table. Also puts the hood up. </br>
	 * If vision throws an error, the shooter speed will not be set
	 * @return if vision has successfully set the Shooter Speeds
	 */
	public void prepVisionShot(){
		closeShot = false;
		visionShot = true;
		
		setShooterSpeedsPID(Robot.visionTable.getDistanceBasedRPM());
	}
	
	
	/**
	 * Initiates a shot with a customized speed and puts the hood up
	 * @param speed the desired speed in RPM
	 */
	public void prepCustomShot(double speed) {
		closeShot = false;
		visionShot = false;
		setShooterSpeedsPID(speed);
	}

	/**
	 * Initiates a wall shot
	 */
	public void prepWallShot() {
		closeShot = true;
		visionShot = false;
		setShooterSpeedsPID(SmartDashboard.getNumber("Shooter Setpoint", 0));
	}

	/**
	 * Initiates a long shot
	 */
	public void prepLongShot() {
		closeShot = false;
		visionShot = false;
		setShooterSpeedsPID(SmartDashboard.getNumber("Shooter Long Setpoint", 0));
	}

	/**
	 * Initiates an auto shot for the Center Gear than shoot auto
	 */
	public void prepAutoCenterShot() {
		closeShot = false;
		visionShot = false;
		setShooterSpeedsPID(SmartDashboard.getNumber("Shooter Auto Center Setpoint", CONSTANTS.SHOOTER_LONG_SET_POINT));
	}

	/**
	 * @return true = shooter is within accepted error range of target speed /
	 *         false = shooter speed outside error range
	 */
	public boolean atSpeed() {
		return shooterGoodDelayedBoolean.update(Math.abs(primaryShooterTalon.getError()) < ERROR_THRESHOLD);
	}

	/**
	 * @param errorThreshold the max error that is acceptable
	 * @return true = shooter is within accepted error range of target speed /
	 *         false = shooter speed outside error range
	 */
	public boolean atSpeed(double errorThreshold) {
		return shooterGoodDelayedBoolean.update(Math.abs(primaryShooterTalon.getError()) < errorThreshold);
	}
	
	/**
	 * Uses PID to start shooter motors and keep shooter speed at target RPM
	 * 
	 * @param targetSpeed
	 *            target RPM of shooter wheels (positive number)
	 */
	public void setShooterSpeedsPID(double targetSpeed) {

		primaryShooterTalon.changeControlMode(TalonControlMode.Speed);

		updatePIDValues();

		primaryShooterTalon.set(targetSpeed);

		debug();
	}

	public void updatePIDValues() {
		//		if (getError() > 100) {
		//			setPrimaryPID(10.0, (SmartDashboard.getNumber("Shooter I", 0)), (SmartDashboard.getNumber("Shooter D", 0)),
		//					(SmartDashboard.getNumber("Shooter FeedForward", 0)));
		//		} else {
		//			if (visionShot) {
		//				setPrimaryPID((SmartDashboard.getNumber("Shooter Vision P", 0)),
		//						(SmartDashboard.getNumber("Shooter Vision I", 0)),
		//						(SmartDashboard.getNumber("Shooter Vision D", 0)),
		//						(SmartDashboard.getNumber("Shooter Vision FeedForward", 0)));
		//			} else if (!closeShot) {
		//				setPrimaryPID((SmartDashboard.getNumber("Shooter Long P", 0)),
		//						(SmartDashboard.getNumber("Shooter Long I", 0)),
		//						(SmartDashboard.getNumber("Shooter Long D", 0)),
		//						(SmartDashboard.getNumber("Shooter Long FeedForward", 0)));
		//			} else {

		setPrimaryPID((SmartDashboard.getNumber("Shooter P", 0)), 
				(SmartDashboard.getNumber("Shooter I", 0)),
				(SmartDashboard.getNumber("Shooter D", 0)),
				(SmartDashboard.getNumber("Shooter FeedForward", 0)));

	}

	/**
	 * Set PID for the primary master talon
	 * 
	 * @param m_p
	 *            desired p
	 * @param m_i
	 *            desired i
	 * @param m_d
	 *            desired d
	 * @param m_ff
	 *            desired feedforward
	 */
	public void setPrimaryPID(double m_p, double m_i, double m_d, double m_ff) {
//		if (!primaryHasP)
//			System.out.println("****PID**** Shooter P is:" + m_p);
		primaryShooterTalon.setP(m_p);

		primaryShooterTalon.setI(m_i);
		primaryShooterTalon.setD(m_d);
		primaryShooterTalon.setF(m_ff);
	}

	/**
	 * Sets shooter output vbus for both 775pros; if unacceptable value entered,
	 * then full speed in corresponding direction is given
	 * 
	 * @param vbus
	 *            -1.0 to +1.0 accepted
	 */
	public void setShooterSpeedVBus(double vbus) {
		primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
		primaryShooterTalon.set(Util.limit(vbus, 1.0));
	}

	/**
	 * @param down
	 *            true = hood up / false = hood down
	 */
	public void setShooterSolenoidState(boolean down) {
		shooterSolenoid.set(down);
	}

	/**
	 * @return true = hood up(far shot) / false = hood down(close shot)
	 */
	private boolean getShooterHeight() {
		return shooterSolenoid.get();
	}

	/**
	 * Resets the two Talon SRXs; stops the shooter motors
	 */
	public void reset() {
		stopMotors();
		primaryShooterTalon.reset();
		followerShooterTalonA.reset();
		followerShooterTalonB.reset();
	}

	public void stopMotors() { // Set the motors to 0 to stop
		primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
		primaryShooterTalon.set(0);
	}

	/**
	 * @return total shooter current usage
	 */
	public double getCurrentUsage() {
		return primaryShooterTalon.getOutputCurrent() + followerShooterTalonA.getOutputCurrent()
		+ followerShooterTalonB.getOutputCurrent();
	}

	/**
	 * Used for trigger-based manual shooting
	 * 
	 * @param Vbus
	 *            -1.0 to +1.0
	 */
	public void setVBusWithTrigger(double Vbus) {
		primaryShooterTalon.changeControlMode(TalonControlMode.PercentVbus);

		SmartDashboard.putNumber("Shooter percent Vbus", Vbus);

		primaryShooterTalon.set(Vbus);

	}

	/**
	 * @return primary shooter Talon SRX error from setpoint
	 */
	public double getError() {
		return primaryShooterTalon.getError();
	}

	/**
	 * shooter sfx debugging outputs
	 */
	public void debug() {

		SmartDashboard.putNumber("Primary Talon Encoder Distance", primaryShooterTalon.getPosition());
		SmartDashboard.putNumber("Primary Talon Speed", primaryShooterTalon.getSpeed());
		SmartDashboard.putNumber("Primary Talon Error (Setpoint)", primaryShooterTalon.getError());
		SmartDashboard.putNumber("Primary Talon Closed Loop Error (Sensor value)",
				primaryShooterTalon.getClosedLoopError());

		SmartDashboard.putNumber("Shooter primary output voltage", primaryShooterTalon.getOutputVoltage());
		SmartDashboard.putNumber("Shooter follower output voltage", followerShooterTalonA.getOutputVoltage());

		SmartDashboard.putNumber("Shooter primary output %vbus",
				primaryShooterTalon.getOutputVoltage() / primaryShooterTalon.getBusVoltage());

		SmartDashboard.putNumber("Shooter total current output", getCurrentUsage());
		SmartDashboard.putString("Shooter primary output current vs follower output current",
				primaryShooterTalon.getOutputCurrent() + ":" + followerShooterTalonA.getOutputCurrent());
		SmartDashboard.putString("Shooter primary output vs follower output current",
				primaryShooterTalon.getOutputCurrent() + ":" + followerShooterTalonA.getOutputCurrent());
	}
}
