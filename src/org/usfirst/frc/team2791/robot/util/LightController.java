package org.usfirst.frc.team2791.robot.util;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LightController{

	private static DigitalOutput pin1,pin2,pin3;
	private static boolean active1, active2, active3;


	public LightController(){
		pin1 = new DigitalOutput(RobotMap.LIGHTS_PIN_A);
		pin2 = new DigitalOutput(RobotMap.LIGHTS_PIN_B);
		pin3 = new DigitalOutput(RobotMap.LIGHTS_PIN_C);


	}

	/**
	 *is constantly called in a periodic method
	 */
	public void run(){

		if(Robot.gamePeriod == Robot.GamePeriod.DISABLED){
			demoSequence();
		}else if(Robot.gamePeriod == Robot.GamePeriod.TELEOP){
			if(Robot.gearMechanism.isDown())
				gearIntakeSequence();
			else if(Robot.intake.isClimbing)
				climbingSequence();
			else if(Robot.hopper.isShooting)
				shootingSequence();
			else if (Robot.teamColor == Robot.TeamColor.BLUE)
				teleopSequenceBlue();
			else if (Robot.teamColor == Robot.TeamColor.RED)
				teleopSequenceRed();

		}else if(Robot.gamePeriod == Robot.GamePeriod.AUTONOMOUS){
			if (Robot.teamColor == Robot.TeamColor.BLUE)
				autoSequenceBlue();
			else if(Robot.teamColor == Robot.TeamColor.RED)
				autoSequenceRed();	

		}


		pin1.set(active1);
		pin2.set(active2);
		pin3.set(active3);
		
		SmartDashboard.putBoolean("Light Pin 1", active1);
		SmartDashboard.putBoolean("Light Pin 2", active2);
		SmartDashboard.putBoolean("Light Pin 3", active3);

	}

	public void autoSequenceBlue(){
		active1 = true;
		active2 = false;
		active3 = false;
	}

	public void gearIntakeSequence(){ //autoSequenceRed
		active1 = true;
		active2 = false;
		active3 = true;
	}

	public void shootingSequence(){
		active1 = true;
		active2 = true;
		active3 = false;
	}

	public void climbingSequence(){
		active1 = true;
		active2 = true;
		active3 = true;
	}

	public void demoSequence(){
		active1 = false;
		active2 = false;
		active3 = false;
	}

	public void teleopSequenceRed(){
		active1 = false;
		active2 = false;
		active3 = true;
	}

	public void teleopSequenceBlue(){
		active1 = false;
		active2 = true;
		active3 = false;
	}

	public void autoSequenceRed(){ //gearIntakeSequence
		active1 = false;
		active2 = true;
		active3 = true;
		//		
		//		active2 = true;
		//		active3 = true;
	}

	public void teleop(){
		active2 = true;
		active3 = false;
	}

	public void auto(){
		active2 = false;
		active3 = true;
	}
}