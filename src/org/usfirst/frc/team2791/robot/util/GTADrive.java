package org.usfirst.frc.team2791.robot.util;
/**
 * @author Created by Gaurab Banerjee on 2/20/2017
 * This allows you to easily adapt your controller to a tank drive
 */
import org.usfirst.frc.team2791.robot.ShakerJoystick.OverriddenJoystick;


public class GTADrive {
	public static double getLeftValue(OverriddenJoystick joystick){
		double combinedLeft = 0;
		
		if(joystick.getButtonRB()){
			combinedLeft=0.35+joystick.getAxisLeftX()/3;
		} else if(joystick.getButtonLB()){
			combinedLeft=-1*(0.35+joystick.getAxisLeftX()/3);
		} else{
		 	combinedLeft=joystick.getGtaDriveLeft();
		}
		
		return combinedLeft;
	}
	public static double getRightValue(OverriddenJoystick joystick){
		double combinedRight = 0;
		
		if(joystick.getButtonRB()){
			combinedRight=0.35-(double) (joystick.getAxisLeftX())/3.0;
	    } else if(joystick.getButtonLB()){
			combinedRight=-1*(0.35-joystick.getAxisLeftX()/3);
	    } else{
		 	combinedRight=joystick.getGtaDriveRight();
	    }
		
		return combinedRight;
	}
}
