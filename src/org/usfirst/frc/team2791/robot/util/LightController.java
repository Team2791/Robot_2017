package org.usfirst.frc.team2791.robot.util;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;

public class LightController{
	
    private DigitalOutput pin1,pin2,pin3;
    private boolean active1, active2, active3;
    
 
    public LightController(){
        pin1 = new DigitalOutput(RobotMap.LIGHTS_PIN_A);
        pin2 = new DigitalOutput(RobotMap.LIGHTS_PIN_B);
        pin3 = new DigitalOutput(RobotMap.LIGHTS_PIN_C);
    }
    
    /**
    *is constantly called in a periodic method
    */
    public void run(){
        
        if(Robot.gamePeriod == Robot.GamePeriod.TELEOP){
        	if(Robot.teamColor == Robot.TeamColor.BLUE)
        		teleopSequenceBlue();
        	if(Robot.teamColor == Robot.TeamColor.RED)
        		teleopSequenceRed();
        	
        } else if (Robot.gamePeriod == Robot.GamePeriod.AUTONOMOUS){
			if(Robot.teamColor == Robot.TeamColor.BLUE)
        		autoSequenceBlue();
        	if(Robot.teamColor == Robot.TeamColor.RED)
        		autoSequenceRed();
        	
        } else if(Robot.gamePeriod == Robot.GamePeriod.DISABLED){
        	demoSequence();
        }

        pin1.set(active1);
        pin2.set(active2);
        pin3.set(active3);
    }
    
    private void autoSequenceBlue(){
        active1 = true;
        active2 = false;
        active3 = false;
    }

    private void gearIntakeSequence(){
    	active1 = true;
        active2 = false;
        active3 = true;
    }

    private void shootingSequence(){
    	active1 = true;
        active2 = true;
        active3 = false;
    }
    

    private void climbingSequence(){
    	active1 = true;
        active2 = true;
        active3 = true;
    }
    
    private void demoSequence(){
    	active1 = false;
        active2 = false;
        active3 = false;
    }

    private void teleopSequenceRed(){
    	active1 = false;
        active2 = false;
        active3 = true;
    }
    
    private void teleopSequenceBlue(){
    	active1 = false;
        active2 = true;
        active3 = false;
    }

    private void autoSequenceRed(){
    	active1 = false;
        active2 = true;
        active3 = true;
    }
}