package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShakerGear extends Subsystem{
	private Solenoid gearSolenoid;
	public ShakerGear(){
		gearSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.GEAR_CHANNEL);
	}
	public void initDefaultCommand(){//puts the gear holder in the up position when teleop or auton init'ed
		gearSolenoid.set(false);
	}

	public void changeGearSolenoidState(boolean state){//true is down; false is up
		gearSolenoid.set(state);
	}
}