package org.usfirst.frc.team2791.robot.util;


import org.usfirst.frc.team2791.robot.ShakerGamepad.OverriddenGamepad;

/**
 * This allows you to easily adapt Gamepad/Xbox controllers to our specialized tank drive
 */
public class GTADrive {
	
	/**
	 * @param joystick the controller that is controlling the drivetrain (typically Driver)
	 * @return the output value for the left motors, based the amount the trigger is pressed or if the bumpers are pressed
	 */
	public static double getLeftValue(OverriddenGamepad joystick){
		double combinedLeft = 0;
		
		if(joystick.getButtonRB()){ //if the Right Bumpers are calling GTA
			combinedLeft=0.35+joystick.getAxisLeftX()/3; //nudge the drivetrain forward
			
		} else if(joystick.getButtonLB()){//if the Left Bumpes are calling GTA
			combinedLeft=-1*(0.35+joystick.getAxisLeftX()/3);//nudge the drivetrain Backwards
			
		} else{//otherwise, the Triggers must be calling GTA, and only LT calls this method
		 	combinedLeft=joystick.getGtaDriveLeft();//return an output based on the amount LT is pressed
		}
		
		return combinedLeft;
	}
	
	/**
	 * @param joystick the controller that is controlling the drivetrain (typically Driver)
	 * @return the output value for the right motors, based the amount the trigger is pressed or if the bumpers are pressed
	 */
	public static double getRightValue(OverriddenGamepad joystick){
		double combinedRight = 0;
		
		if(joystick.getButtonRB()){//if the Right Bumpers are calling GTA
			combinedRight=0.35-(double) (joystick.getAxisLeftX())/3.0;//nudge the drivetrain forward
			
	    } else if(joystick.getButtonLB()){//if the Left Bumpes are calling GTA
			combinedRight=-1*(0.35-joystick.getAxisLeftX()/3);//nudge the drivetrain Backwards
			
	    } else{//otherwise, the Triggers must be calling GTA, and only LT calls this method
		 	combinedRight=joystick.getGtaDriveRight();
	    }
		
		return combinedRight;//return an output based on the amount LT is pressed
	}
}
