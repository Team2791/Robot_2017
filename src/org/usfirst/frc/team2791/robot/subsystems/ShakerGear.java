package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerGear extends Subsystem{
	private Solenoid gearSolenoid;
	private static boolean state;
	
	private Talon gearSpark;
	public ShakerGear(){
		gearSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.GEAR_CHANNEL);
		gearSpark = new Talon(RobotMap.GEAR_SPARK_PORT);
	}
	public void initDefaultCommand(){
		gearSolenoid.set(false);
	}
	public void runGearIntake(){
		gearSpark.setSpeed(1.0);
	}
	public void stopGearIntake(){
		gearSpark.setSpeed(0.0);
	}
	public void changeGearSolenoidState(boolean state_){
		state=state_;
		gearSolenoid.set(state);
	}
	public boolean getState(){
		return state;
	}
	public void debug(){
		SmartDashboard.putBoolean("Gear state", gearSolenoid.get());
		SmartDashboard.putNumber("Gear Motor Output", gearSpark.get());
	}
}
