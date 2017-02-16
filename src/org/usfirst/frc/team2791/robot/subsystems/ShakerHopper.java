package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShakerHopper extends Subsystem{
	private AnalogInput ballSensor1;
	private AnalogInput ballSensor2;
	
	private Talon hopperSpark;
	public void initDefaultCommand(){
		this.hopperSpark = new Talon(RobotMap.HOPPER_SPARK_PORT);
		
		this.ballSensor1 = new AnalogInput(0);
		this.ballSensor2 = new AnalogInput(1);
	}
	public boolean moreBalls(){
		System.out.println("Ball sensor 1: "+ballSensor1.getVoltage()+"\tBall sensor 2: "+ballSensor2.getVoltage());
		if(ballSensor1.getVoltage()<1.5 || ballSensor2.getVoltage()<1.5)
			return false;
		return true;
	}
	public boolean getDistanceState(){
		if(ballSensor1.getVoltage()<1.5)
			return true;
		return false;
	}
	public void runHopper(){
		hopperSpark.set(-1.0);
	}
	public void stopHopper(){
		hopperSpark.set(0.0);
	}
	public void stopMotor(){
		hopperSpark.disable();
	}
	
	//made for hopper testing to check for what speed works best
	/*public void setHopperOutput(double key){
		System.out.println("Hopper motor output: -"+key);
		hopperSpark.set(-key);
	}*/
}
