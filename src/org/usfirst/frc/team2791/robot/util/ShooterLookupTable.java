package org.usfirst.frc.team2791.robot.util;

public class ShooterLookupTable {
	private double distanceFromBoilerTape[] = {0
		};
	
	private double shooterRPMforDistance[] = {CONSTANTS.SHOOTER_SET_POINT
		};
	
	public ShooterLookupTable(){}
	
	public double getRPMfromDistance(double distanceInInches){
		int highKey=1, lowKey=0;
		
		//finds which experimental points the measured point is between
		for(int i=0; i<distanceFromBoilerTape.length; i++){
			if(distanceInInches > distanceFromBoilerTape[i]){
				highKey = i;
				lowKey = i-1;
				break;
			}
		}
		
		double deltaD = distanceFromBoilerTape[highKey]-distanceFromBoilerTape[lowKey];//the difference between the two experimental distances
		double deltaRPM = shooterRPMforDistance[highKey]-shooterRPMforDistance[lowKey];//difference in RPMs for the two experimental distances
		double diffKey = distanceInInches - distanceFromBoilerTape[lowKey];//difference between the experimental distance and measured distance
		
		return deltaRPM * diffKey / deltaD; //tested calculation which works
	}
}
