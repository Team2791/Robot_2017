package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShakerHopper extends Subsystem{
	private Talon hopperSpark;
	public void initDefaultCommand(){
		this.hopperSpark = new Talon(RobotMap.HOPPER_TALON_PORT);
	}
	
	public void runHopper(){
		hopperSpark.set(1.0);
	}
	public void stopHopper(){
		hopperSpark.set(0.0);
	}
	public void stopMotor(){
		hopperSpark.disable();
	}
}
