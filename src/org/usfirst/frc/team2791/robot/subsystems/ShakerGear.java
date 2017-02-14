package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerGear extends Subsystem{
	private Solenoid gearSolenoid;
	public void initDefaultCommand(){
		gearSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.GEAR_CHANNEL);
		gearSolenoid.set(false);
	}
	
	public void changeGearSolenoidState(boolean state){
		gearSolenoid.set(state);
	}
	
	public void debug(){
		SmartDashboard.putBoolean("gear solenoid state", gearSolenoid.get());
	}
}
