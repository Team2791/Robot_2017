package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2791.robot.commands.RunGearMotor;

/**
 * This class corresponds to the gear subsystem which uses pistons to actuate the scoring mechanism 
 * and a bag motor on a Spark to run the roller intake. There are limit switches in the intake to automatically
 * raise the intake once a gear is pulled in.
 * 
 * @author team2791: See Robot.java for contact info
 */
public class ShakerGear extends Subsystem{
	
	private Solenoid gearSolenoid;
	private static boolean state;
	
	private Talon gearSpark;
	private DigitalInput limitSwitch;
	private static boolean switchesEnabled;
	
	public ShakerGear(){
		gearSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.GEAR_CHANNEL);
		gearSpark = new Talon(RobotMap.GEAR_SPARK_PORT);
		
		limitSwitch = new DigitalInput(RobotMap.GEAR_INTAKE_LIMIT_SWITCH_A);
		enableLimitSwitches();
	}
	
	public void initDefaultCommand(){
		setDefaultCommand(new RunGearMotor());
	}
	
	public void runGearIntake(){
		gearSpark.setSpeed(1.0);
	}
	
	public void stopGearIntake(){
		gearSpark.setSpeed(0.0);
	}
	
	/**
	 * 
	 * @param state_ true = down / false = up
	 */
	public void changeGearSolenoidState(boolean state_){
		state = state_;
		gearSolenoid.set(state);
	}
	
	/**
	 * @return State of gear actuation solenoid. true = down / false = up
	 */
	public boolean isDown(){
		return state;
	}
	
	/**
	 * @return Summary state of limit switches in the intake. true = gear inside / false = no gear in intake </p>The operator needs the ability to override the switches, so if the switches are disables, this will return false
	 */
	public boolean getLimitSwitchState(){

		if(areSwitchesEnabled())
			return limitSwitch.get();
		else
			return false;
	}
	
	/**
	 * Disables the limit switches.
	 */
	public void disableLimitSwitches(){
		switchesEnabled = false;
	}
	
	/**
	 * Enables the limit switches
	 */
	public void enableLimitSwitches(){
		switchesEnabled = true;
	}
	
	/**
	 * @return if the limit switches are enabled
	 */
	public boolean areSwitchesEnabled(){
		return switchesEnabled;
	}
	
	public double getCurrentUsage(){
		return Robot.pdp.getCurrent(RobotMap.POWER_GEAR_INTAKE);
	}
	
	public void debug(){
		SmartDashboard.putBoolean("Gear Intake Status", getLimitSwitchState());
		SmartDashboard.putBoolean("Gear Intake Switches Enabled", areSwitchesEnabled());
		
		SmartDashboard.putBoolean("Gear state", gearSolenoid.get());
		
		SmartDashboard.putNumber("Gear Motor Output", gearSpark.get());
		SmartDashboard.putNumber("Gear intake Current Usage", getCurrentUsage());
	}
}
