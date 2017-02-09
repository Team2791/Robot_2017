package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerIntake extends Subsystem{ //code for intake and climber
	public Talon intakeSpark;
	
	private double climber_current;
	
	private Solenoid intakeSolenoid;
//	private Solenoid wingSolenoid;
	
	public void initDefaultCommand(){

		this.intakeSpark = new Talon(RobotMap.INTAKE_TALON_PORT);
		
		intakeSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.INTAKE_CHANNEL);
//		wingSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.WING_CHANNEL);
//		wingSolenoid.set(false);
		intakeSolenoid.set(false);//all Pistons should be closed (true) at beginning
		System.out.println("Initiating intake");
	}
	
	public void wingDeployment(){
//		wingSolenoid.set(true);
	}
	public void moveIntakeOut(boolean yes){
		intakeSolenoid.set(yes);
		System.out.print("The intake moved");
	}
	public void motorOnIntake(){
		intakeSpark.setSpeed(-0.66);
	}
	public void motorOffIntake(){
		intakeSpark.setSpeed(0.0);
	}
	public void motorOnClimber(){//should be positive
		intakeSpark.setSpeed(-0.05);
	}
	public double getClimberCurrent(){
		climber_current = Robot.pdp.getCurrent(1);//slot number of climber motor wires
		return climber_current;
	}
	public boolean stopMotor(){
		if(getClimberCurrent()>5.0){
			intakeSpark.stopMotor();
			System.out.println("Climbing ceased; remove me from this rope");
			return true;
		}
		return false;
	}
	public void debug(){
		SmartDashboard.putNumber("Intake/Climber current usage", getClimberCurrent());
	}
}

