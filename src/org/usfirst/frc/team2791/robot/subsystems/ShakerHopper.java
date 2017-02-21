package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.commands.RunHopper;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerHopper extends Subsystem{
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
		setDefaultCommand(new RunHopper());
	}
	
	/**
	 * This method checks the elevator for balls.
	 * @return returns True if a ball is ready to shoot.
	 */
	public boolean isBallAtTop() {
		return (ballSensor1.getVoltage() < distanceSensorCutoffPoint ||
				ballSensor2.getVoltage() < distanceSensorCutoffPoint);
	}
	
	public void runHopper() {
		hopperSpark.set(SmartDashboard.getNumber("Hopper VBus",hopperSetpoint));
	}
	
	public void setHopperSpeed(double speed){
		hopperSpark.set(speed);
	}
	
	public void stopHopper(){
		hopperSpark.set(0.0);
	}
	
	public void stopMotor(){
		hopperSpark.disable();
	}
	public double getCurrentUsage() {
		return Robot.pdp.getCurrent(RobotMap.POWER_HOPPER_FLOOR);
	}
	
	//made for hopper testing to check for what speed works best
	/*public void setHopperOutput(double key){
		System.out.println("Hopper motor output: -"+key);
		hopperSpark.set(-key);
	}*/
}
