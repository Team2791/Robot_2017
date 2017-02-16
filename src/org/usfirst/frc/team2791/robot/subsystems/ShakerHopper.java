package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerHopper extends Subsystem{
	private Talon hopperSpark;
	public void initDefaultCommand(){
		this.hopperSpark = new Talon(RobotMap.HOPPER_SPARK_PORT);
	}
	
	public void runHopper(){
		hopperSpark.set(0.3);
	}
	
	public void stopHopper(){
		hopperSpark.set(0.0);
	}
	
	public void stopMotor(){
		hopperSpark.disable();
	}
	
	public void setHopperOutput(double key){
		System.out.println("Hopper motor output: -"+key);
		hopperSpark.set(-key);
	}
	
	public void debug(){
		SmartDashboard.putNumber("hopperSpark PWM", hopperSpark.get());
	}
}
