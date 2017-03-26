package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.commands.HopperOn;
import edu.wpi.first.wpilibj.AnalogInput;
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
	private AnalogInput ballSensor1;
	private AnalogInput ballSensor2;
	
	private Talon hopperSpark;

	private static double hopperSetpoint = -0.66;
	private final double distanceSensorCutoffPoint = 1.5;
	
	public ShakerHopper(){
		hopperSpark = new Talon(RobotMap.HOPPER_SPARK_PORT);
		
		ballSensor1 = new AnalogInput(0);
		ballSensor2 = new AnalogInput(1);
	}
	
	public void initDefaultCommand(){
		stopHopper();
	}
	
	/**
	 * This method checks the elevator for balls.
	 * @return true = ball at top of elevator / false = no ball ready to shoot
	 */
	public boolean isBallAtTop() {
		return (ballSensor1.getVoltage() < distanceSensorCutoffPoint ||
				ballSensor2.getVoltage() < distanceSensorCutoffPoint);
	}
	
	/**
	 * Runs hopper at full-speed
	 */
	public void runHopper() {
		System.out.print("runHopper accessor");
		hopperSpark.setSpeed(1.0);
	}
	
	/**
	 * Runs hopper at half-speed
	 */
	public void slowHopper() {
		System.out.print("slowHopper accessor");
		hopperSpark.setSpeed(0.5);
	}
	
	/**
	 * Sets the hopper VBus output
	 * @param speed between -1.0 and +1.0
	 */
	public void setHopperSpeed(double speed){
		System.out.print("setHopperSpeed accessor");
		hopperSpark.setSpeed(speed);
	}
	
	public void stopHopper(){
		System.out.print("stopHopper accessor");
		hopperSpark.setSpeed(0.0);
	}

	/**
	 * @return hopper miniCIM current usage
	 */
	public double getCurrentUsage() {
		return Robot.pdp.getCurrent(RobotMap.POWER_HOPPER_FLOOR);
	}
	
}
