package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	private final static double GEAR_IR_DISTANCE = 1200; //idk, need to find
	/**
	 * A SHARP IR Sensor positioned to detect when a gear is in the intake
	 */
	private AnalogInput gearDetector;
	
	public ShakerGear(){
		gearSolenoid = new Solenoid(RobotMap.PCM_MODULE,RobotMap.GEAR_CHANNEL);
		gearSpark = new Talon(RobotMap.GEAR_SPARK_PORT);
		
		gearDetector = new AnalogInput(RobotMap.GEAR_INTAKE_IR_SENSOR);
		
	}
	
	public void initDefaultCommand(){
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
	public void setGearIntakeDown(boolean state_){
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
	 * The operator needs the ability to override the switches, so if the switches are disables, this will return false
	 * </br>Summary state of limit switches in the intake. true = gear inside / false = no gear in intake 
	 * @return true = gear inside / false = no gear in intake
	 */
	public boolean hasGear(){
		return (gearDetector.getValue() > GEAR_IR_DISTANCE); 
		//		return limitSwitch.get();
		
	}
	
	public double getCurrentUsage(){
		return Robot.pdp.getCurrent(RobotMap.POWER_GEAR_INTAKE);
	}
	
	public void debug(){
		
		SmartDashboard.putBoolean("Gear Intake Gear Status", hasGear()); //Green when a gear is in the intake
		
		SmartDashboard.putBoolean("Gear Intake Down or Up", gearSolenoid.get()); //Green when intake is down
		
		SmartDashboard.putNumber("Gear Motor Output", gearSpark.get());
		SmartDashboard.putNumber("Gear intake Current Usage", getCurrentUsage());
		
		SmartDashboard.putNumber("IR Voltage", gearDetector.getValue());
	}

	public void changeGearSolenoidState(boolean state_) {
		
		gearSolenoid.set(state_);
		
	}
}
