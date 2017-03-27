package org.usfirst.frc.team2791.robot.util;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author team2791: See Robot.java for contact info
 * @see Robot
 */
public class DelayedBoolean {

	private double trueStartTime = 0;
	private boolean counting = false;
	private double delay;
	
	public DelayedBoolean(double delay) {
		this.delay = delay;
	}
	
	public boolean update(boolean input) {
		if(input) {
			if(counting) {
				return Timer.getFPGATimestamp() - trueStartTime > delay;
			} else {
				trueStartTime = Timer.getFPGATimestamp();
				counting = true;
				// This will only return true if the delay is 0
				return Timer.getFPGATimestamp() - trueStartTime > delay;
			}
		} else {
			counting = false;
			return false;
		}
	}

}
