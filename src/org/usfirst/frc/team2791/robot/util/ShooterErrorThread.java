package org.usfirst.frc.team2791.robot.util;

import static org.usfirst.frc.team2791.robot.Robot.shooter;
public class ShooterErrorThread extends Thread{

	public void run() {

		if(shooter.getError() > 100){
			shooter.primaryShooterTalon.setP(10.0); //p is 10
			shooter.primaryHasP = true;
 		}else{
 			shooter.primaryHasP = false;
 		}
			

		try {
			Thread.sleep(1000/200);	
		}catch (InterruptedException e) {}
	}
}
