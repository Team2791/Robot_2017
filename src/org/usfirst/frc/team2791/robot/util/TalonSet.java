package org.usfirst.frc.team2791.robot.util;

/**
 * @author Created by Akhil Jacob on 4/10/2016.
 * This class daisy chains speed controllers so you can put them into roboDrive as one set this allows you
 * to use multiple CIMs easily
 */

import edu.wpi.first.wpilibj.SpeedController;

public class TalonSet implements SpeedController {
    private SpeedController[] speedControllers;// = new SpeedController[3];
    private double sign = 1;//This value sets the output to positive or negative

    public TalonSet(SpeedController A, SpeedController B, SpeedController C) {
    	speedControllers = new SpeedController[3];
        speedControllers[0] = A;
        speedControllers[1] = B;
        speedControllers[2] = C;
        this.set(0.0);
    }


    public double get() {
        return 0;
    }

    public void set(double speed, byte syncGroup) {
//    	System.out.println("Being Set with the sync group option with speed "+speed);
//        for (SpeedController speedController : this.speedControllers) {
//            speedController.set(sign * speed, syncGroup);
//        }
    	set(speed);
    }

    public void set(double speed) {
//    	System.out.println("Setting Speed to "+ speed);
        for (SpeedController speedController : this.speedControllers) {
//        	System.out.println("  setting another speed conrtoller");
            speedController.set(sign * speed);
        }
    }

    public boolean getInverted() {
        return sign == -1;
    }

    public void setInverted(boolean isInverted) {
        if (isInverted)
            sign = -1;
        else
            sign = 1;
    }

    public void disable() {
        for (SpeedController speedController : this.speedControllers) {
            speedController.disable();
        }
    }

    public void pidWrite(double output) {
        this.set(output);
    }
    public void stopMotor(){
    	for(int i=0; i<3; i++){
    		//speedControllers[i].stopMotor();
    	}
    	}
    }
