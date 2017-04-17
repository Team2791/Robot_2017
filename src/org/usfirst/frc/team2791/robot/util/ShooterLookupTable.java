package org.usfirst.frc.team2791.robot.util;

public class ShooterLookupTable {
	private double distanceFromBoilerTape[] = {
			36.25, 41.25, 46.25, 51.25, 56.25,
			61.25, 66.25, 71.25
		};//inches from boiler wall
	
	private double shooterRPMforDistance[] = {
			2900, 2925, 2950, 2955, 2970,
			2980, 3000, 3050
		};//RPM
	
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
