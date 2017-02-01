package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShakerIntake extends Subsystem{ //code for intake and climber
	private Talon intakeSpark;
	
	private double climber_current;
	
	public void initDefaultCommand(){
		System.out.println("Initiating intake");
		this.intakeSpark = new Talon(RobotMap.INTAKE_TALON_PORT);
	}
	
	public void runIntake(){
		intakeSpark.setSpeed(1.0);
	}
	public double getClimberCurrent(){
		climber_current = Robot.pdp.getCurrent(1);//slot number of climber motor wires
		return climber_current;
	}
	public void stopMotor(){
		if(getClimberCurrent()>0.0){
			intakeSpark.stopMotor();
			System.out.println("Climbing ceased; remove me from this rope");
		}
	}
}
