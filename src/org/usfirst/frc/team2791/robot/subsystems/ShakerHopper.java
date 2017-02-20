package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShakerHopper extends Subsystem{
	private AnalogInput ballSensor1 = new AnalogInput(0);
	private AnalogInput ballSensor2 = new AnalogInput(1);
	private Talon hopperSpark = new Talon(RobotMap.HOPPER_SPARK_PORT);

	private final double hopperSetpoint = -0.66;
	private final double distanceSensorCutoffPoint = 1.5;
	public void initDefaultCommand(){
	}
	
	public boolean moreBalls(){
//		System.out.println("Ball sensor 1: "+ballSensor1.getVoltage()+"\tBall sensor 2: "+ballSensor2.getVoltage());
		if(ballSensor1.getVoltage()<distanceSensorCutoffPoint || ballSensor2.getVoltage()<distanceSensorCutoffPoint)
			return false;
		return true;
	}
	public boolean isBallAtTop(){
		if(ballSensor1.getVoltage()<distanceSensorCutoffPoint || ballSensor2.getVoltage()<distanceSensorCutoffPoint)
			return true;
		return false;
	}
	public void runHopper(){
		hopperSpark.set(hopperSetpoint);
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
