package org.usfirst.frc.team2791.robot.util;

/**
 * This is a class with several useful methods that are invoked throughout our code
 * 
 * @author team2791
 */
public class Util {
    //A class with a bunch of useful tools(methods)
    public static String newline = System.lineSeparator();

    private Util() {
    }

    /**
     * Used to make sure that speed controllers and other systems do not receive numbers they cannot handle (i.e. 2.3 on a PWM)
     * @param val given value
     * @param limit the bound of the system
     * @return the value if it is within the limits; -1 if negative value given, +1 if positive number given
     */
    public static double limit(double val, double limit) {
        return Math.abs(val) < limit ? val : limit * (val < 0 ? -1 : 1);
    }

    /**
     * 
     * @param min
     * @param val
     * @param max
     * @return
     */
    public static double deadzone(double min, double val, double max) {
        double absVal = Math.abs(val);
        double absMin = Math.abs(min);
        double absMax = Math.abs(max);

        if (absMin <= absVal && absVal <= absMax) {
            return val;
        } else if (absVal <= absMin) {
            return 0.0;
        } else {
            return val < 0 ? -absMax : absMax;
        }
    }

    public static String repeatString(String s, int repetitions) {
        return new String(new char[repetitions]).replace("\0", s);
    }

    public static String truncateLastTerm(String message, String term) {
        int lastIndex = getLastIndex(message, term);

        if (lastIndex == -1) {
            return message;
        }

        return message.substring(0, lastIndex);
    }

    public static int getLastIndex(String message, String term) {
        int index = message.indexOf(term);
        while (index >= 0) {
            index = message.indexOf(term, index + 1);
        }

        return index;
    }

    /**
     * @param encoderTicks count on encoder
     * @param wheelDiameter_inFeet
     * @return number of feet traveled based on encoder ticks read
     */
    public static double tickToFeet(double encoderTicks, double wheelDiameter_inFeet) {
        return (wheelDiameter_inFeet * Math.PI / encoderTicks);
    }

    /**
     * Ramps output voltage based on target and current voltages to prevent brownouts from excessive loads on a system
     * @param previousOutput last Vbus output
     * @param currentDesiredOutput target Vbus output
     * @param timeDiff currentTime - lastRampUpdateTime
     * @param VBUS_RAMP_RATE rate at which to ramp the Vbus (in %vbus per second)
     * @return currentDesiredOutput for the motors
     */
    public static double limitWithRampRate(double previousOutput, double currentDesiredOutput, double timeDiff, double VBUS_RAMP_RATE) {
		double maxLimitedOutput = Math.abs(previousOutput) + timeDiff * VBUS_RAMP_RATE;
		
		if(Math.abs(currentDesiredOutput) > maxLimitedOutput) {
			return Math.copySign(maxLimitedOutput, currentDesiredOutput);
		} else {
			return currentDesiredOutput;
		}
	}
    
  //FIRST puns ftw
    /**Sorts 2D arrays by the first elements*/
	public static double[][] sortByFirstElement(double[][] arr) { 
		double[][] result = arr;
		
		int n = result.length;  
		double tempY = 0;  
		double tempRPM = 0;
		for(int i=0; i < n; i++){  
			for(int j=1; j < (n-i); j++){  
				if(result[j-1][0] > result[j][0]){  //swap elements  
					
					tempY = result[j-1][0];
					tempRPM = result[j-1][1];

					result[j-1][0] = result[j][0];  
					result[j][0] = tempY;  

					result[j-1][1] = result[j][1];
					result[j][1] = tempRPM;
				}  

			}  
		}
		
		return result;

	}
    
    public enum UnitLength {
        FEET(1.0 / 12.0, "ft"), INCHES(1.0, "in");
        private double unitsPerInch;
        private String abbreviation;

        UnitLength(double unitsPerInch, String abbreviation) {
            this.unitsPerInch = unitsPerInch;
            this.abbreviation = abbreviation;
        }

        public double getUnitsPerInch() {
            return unitsPerInch;
        }

        @Override
        public String toString() {
            return abbreviation;
        }
    }
    
	
    
    
}