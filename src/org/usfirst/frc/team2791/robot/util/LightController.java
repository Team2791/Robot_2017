package org.usfirst.frc.team2791.robot.util;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;

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

		if(Robot.intake.isClimbing)
			climbingSequence();
		else if(Robot.hopper.isShooting)
			shootingSequence();
		else if(Robot.gearMechanism.isDown())
			gearIntakeSequence();
		else if(Robot.gamePeriod == Robot.GamePeriod.DISABLED)
			demoSequence();
		else if(Robot.gamePeriod == Robot.GamePeriod.TELEOP){
			if (Robot.teamColor == Robot.TeamColor.BLUE)
				teleopSequenceBlue();
			else 
				teleopSequenceRed();
		}else if(Robot.gamePeriod == Robot.GamePeriod.AUTONOMOUS){
			if (Robot.teamColor == Robot.TeamColor.BLUE)
				autoSequenceBlue();
			else 
				autoSequenceRed();
		}

		pin1.set(active1);
		pin2.set(active2);
		pin3.set(active3);
	}

	public void autoSequenceBlue(){
		active1 = true;
		active2 = false;
		active3 = false;
	}

	public void gearIntakeSequence(){
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

	public void autoSequenceRed(){
		active1 = false;
		active2 = true;
		active3 = true;
	}
}