package org.usfirst.frc.team2791.robot.util.vision;

import org.usfirst.frc.team2791.robot.util.Util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterLookupTable {

	private static double rpmOffset = -15.0;
	
	/**
	 *{double dist(inches), double rpm}
	 */
	private static double distanceByRPM[][] = {
			{36.25, 2900.0} ,
			{41.25, 2925.0} ,
			{46.25, 2950.0} ,
			{51.25, 2955.0} ,
			{56.25, 2970.0} ,
			{61.25, 2980.0} ,
			{66.25, 3000.0} ,
			{71.25, 3050.0}	
	};

	/**
	 *{double ndcY, double rpm}
	 */
	private static double normalYByRPM[][] = {
			{-0.74, 3700.0},
			{-0.54, 3500.0},
			{-0.18, 3175.0},
			{ 0.14, 3025.0},
			{ 0.61, 2890.0},
			{ 0.84, 2835.0}
	};


	public ShooterLookupTable(){
		normalYByRPM = Util.sortByFirstElement(normalYByRPM);
		normalYByRPM = Util.sortByFirstElement(distanceByRPM);
		
		SmartDashboard.putNumber("RPM Offset", rpmOffset);
	}


	public double getRPMFromNDCY(double ndcY){

		int highKey=1, lowKey=0;
		int arrLength = normalYByRPM.length;

		//finds which experimental points the measured point is between
		for(int i=0; i<arrLength; i++){
			if(ndcY < normalYByRPM[i][0]){
				lowKey = i;
				highKey = i+1;
				break;
			}
		}

		//taking care of ndcY values outside of the array
		if(ndcY > normalYByRPM[arrLength-1][0]) {
			return normalYByRPM[arrLength-1][1]; 
		} else if(ndcY < normalYByRPM[0][0]) {
			return normalYByRPM[0][1]; 
		}

		double deltaY = normalYByRPM[highKey][0]-normalYByRPM[lowKey][0];  // diff b/w: two experimental distances
		double deltaRPM =  normalYByRPM[highKey][1]-normalYByRPM[lowKey][1];  //diff b/w: RPMs for the two experimental distances
		double diffKey = ndcY - normalYByRPM[lowKey][0];  //diff b/w: experimental ndcY and measured ndcY	

		try {
			return diffKey * (deltaRPM / deltaY) + normalYByRPM[lowKey][1] ; 
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("lookup table out of bounds");
			return 3700;
		}
	}

	public double getRPMFromDistance(double distInches){

		int highKey=1, lowKey=0;
		int arrLength = distanceByRPM.length;

		//finds which experimental points the measured point is between
		for(int i=0; i<arrLength; i++){
			if(distInches > distanceByRPM[i][0]){
				highKey = i;
				lowKey = i-1;
				break;
			}
		}

		//taking care of distInches values outside of the array
		if(distInches > distanceByRPM[arrLength-1][0]) {
			return distanceByRPM[arrLength-1][1]; 
		} else if(distInches < distanceByRPM[0][0]) {
			return distanceByRPM[0][1]; 
		}

		double deltaY = distanceByRPM[highKey][0]-distanceByRPM[lowKey][0];  //the difference between the two experimental distances
		double deltaRPM =  distanceByRPM[highKey][1]-distanceByRPM[lowKey][1];  //difference in RPMs for the two experimental distances
		double diffKey = distInches - distanceByRPM[lowKey][0];  //difference between the experimental distInches and measured distInches

		System.out.println("Delta Distance: " + deltaY);
		System.out.println("Delta RPM: " + deltaRPM);
		System.out.println("Delta Key: " + diffKey);

		try {
			return (diffKey * (deltaRPM / deltaY) + distanceByRPM[lowKey][1]) + SmartDashboard.getNumber("RPM Offset", rpmOffset); 
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("lookup table out of bounds");
			return 3050;
		}
	}


}
