package org.usfirst.frc.team2791.robot.subsystems;

import java.io.IOException;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;
import org.usfirst.frc.team2791.robot.commands.RunHopper;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerHopper extends Subsystem{
	private AnalogInput ballSensor1;
	private AnalogInput ballSensor2;
	
	private Talon hopperSpark;

	private static double hopperSetpoint = 1.0;
	private final double distanceSensorCutoffPoint = 1.5;
	
	public ShakerHopper(){
		hopperSpark = new Talon(RobotMap.HOPPER_SPARK_PORT);
		hopperSpark.setInverted(true);
		hopperSpark.set(0.0);
		
		ballSensor1 = new AnalogInput(0);
		ballSensor2 = new AnalogInput(1);
	}
	
	public void initDefaultCommand(){
//		setDefaultCommand(new RunHopper());
	}
	
	/**
	 * This method checks the elevator for balls.
	 * @return returns True if a ball is ready to shoot.
	 */
	public void debug(){
		SmartDashboard.putBoolean("Dist 1", ballSensor1.getVoltage()< distanceSensorCutoffPoint);
		SmartDashboard.putBoolean("Dist 2", ballSensor2.getVoltage()< distanceSensorCutoffPoint);
	}
	public boolean isBallAtTop() {
		
		return ( (ballSensor1.getVoltage() < distanceSensorCutoffPoint) ||
				(ballSensor2.getVoltage() < distanceSensorCutoffPoint) );
	}
	
	public void runHopper() {
		hopperSpark.set(hopperSetpoint);
	}
	
	public void setHopperSpeed(double speed){
		hopperSpark.set(speed);
	}
	
	public void stopHopper(){
		stopMotor();
	}
	
	public void stopMotor(){
		hopperSpark.stopMotor();
	}
	public double getCurrentUsage() {
		return Robot.pdp.getCurrent(RobotMap.POWER_HOPPER_FLOOR);
	}
	
	//made for hopper testing to check for what speed works best
	/*public void setHopperOutput(double key){
		System.out.println("Hopper motor output: -"+key);
		hopperSpark.set(-key);
	}*/
}
