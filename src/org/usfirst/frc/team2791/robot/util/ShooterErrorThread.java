package org.usfirst.frc.team2791.robot.util;

import static org.usfirst.frc.team2791.robot.Robot.shooter;
public class ShooterErrorThread extends Thread{

	public void run() {

		if(shooter.getError() > 100){
			shooter.setPrimaryP(10); //p is 10
			shooter.primaryHasP = true;
 		}else{
 			shooter.primaryHasP = false;
 		}
			

		try {
			Thread.sleep(50);	
		}catch (InterruptedException e) {}
	}
}
