package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.commands.safeties.StopHopper;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class corresponds to the fuel hopper. There is a miniCIM run off of a Spark. The system is composed of a double
 * elevator and two rotating feeder wheels. There are ramps on all sides of the circular feeds for fuel to load in.
 * 
 * @author team2791: See Robot.java for contact info
 */
public class ShakerHopper extends Subsystem{
	
	//Polulo IR sensors from Adafruit; they have a high and a low voltage and are triggered around 4 cm from the sensor.
	
	public boolean isShooting = false;
	
	private PWM servo;
	private Talon hopperSpark;

	private static double hopperSetpoint = 0.65;
		
	public ShakerHopper(){
		hopperSpark = new Talon(RobotMap.HOPPER_SPARK_PORT);
//		hopperSpark.setInverted(true);

		
		servo = new PWM(2);
	}
	
	public void initDefaultCommand(){
		setDefaultCommand(new StopHopper());
	}
	
	/**
	 * This method checks the elevator for balls.
	 * @return true = ball at top of elevator / false = no ball ready to shoot
	 */
	public boolean isBallAtTop() {
//		return (ballSensor1.getVoltage() < distanceSensorCutoffPoint ||
//				ballSensor2.getVoltage() < distanceSensorCutoffPoint);
		return false;
	}
	
	/**
	 * Runs hopper at full-speed
	 */
	public void runHopper() {

//		System.out.print("************Hopper FOR C:" + this.getCurrentUsage());
		hopperSpark.setSpeed(hopperSetpoint);
		servo.setRaw(255);
		 isShooting = true;
	}
	
	/**
	 * Runs hopper at half-speed
	 */
	public void slowHopper() {
//		System.out.print("Running hopper half speed");
		hopperSpark.setSpeed(hopperSetpoint/2.0);
		servo.setRaw(255);
		 isShooting = true;

	}
	
	/**
	 * Runs hopper backwards.
	 */
	public void runHopperBackwards() {
//		System.out.print("Hopper FOR C:" + this.getCurrentUsage());
		hopperSpark.setSpeed(-hopperSetpoint);
		servo.setRaw(255);
		 isShooting = true;

	}
	
	/**
	 * Sets the hopper VBus output
	 * @param speed between -1.0 and +1.0
	 * TODO: get rid of
	 */
	public void setHopperSpeed(double speed){
//		System.out.print("Running Hopper at " +speed);
		hopperSpark.setSpeed(speed);
		if(speed == 0.0)
			isShooting = false;
		else
			isShooting = true;
	} 
	
	public void stopHopper(){
//		System.out.print("Stopping Hopper");
		hopperSpark.setSpeed(0.0);
		servo.setRaw(0);
		 isShooting = false;

	}

	/**
	 * @return hopper miniCIM current usage
	 */
	public double getCurrentUsage() {
		return Robot.pdp.getCurrent(RobotMap.POWER_HOPPER_FLOOR);
	}
	
	/**
	 * Fuel hopper sfx debug utilities
	 */
	public void debug(){
		SmartDashboard.putNumber("Hopper Output", hopperSpark.get());
		SmartDashboard.putNumber("Hopper Current Usage", this.getCurrentUsage());
	}
}
