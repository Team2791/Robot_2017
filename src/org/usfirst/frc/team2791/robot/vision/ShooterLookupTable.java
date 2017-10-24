package org.usfirst.frc.team2791.robot.vision;

import org.usfirst.frc.team2791.robot.util.Util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterLookupTable {

	private static double rpmOffset = 12; //-40 //-15.0
	
	/**
	 *{double ndcY, double rpm}
	 */
	private static double normalYByRPM[][] = {
			/*
			{-0.800, 3800.0},
			{-0.755, 3600.0},
			{-0.622, 3440.0},
			{ -0.444, 3350.0},
			{ -0.155, 3100.0},
			{0.111, 3070.0},
			{0.433, 2925.0},
			{0.744, 2830.0},
			{0.833, 2810.0}
			*/
			
			{-0.635, 3300.0},
			{-0.405, 3220.0},
			{-0.310, 3084.0},
			{-0.230, 3025.0}, //Was 3019
			{-0.145, 3015.0},
			{-0.075, 3010.0}, // Was 2995
			{-0.050, 3005.0}, //Was 2978
			{-0.010, 2980.0}, //Was 2965
			{0.040, 2965.0}, //Was 2955
			{0.070, 2952.0},
			{0.105, 2950.0},
			{0.145, 2945.0},
			{0.190, 2925.0},
			{0.270, 2900.0},
			{0.350, 2850.0}
	};
	
//	{-0.74, 3700.0},
//	{-0.54, 3500.0},
//	{-0.18, 3175.0},
//	{ 0.14, 3025.0},
//	{ 0.61, 2890.0},
//	{ 0.84, 2835.0}


	public ShooterLookupTable(){
		normalYByRPM = Util.sortByFirstElement(normalYByRPM);
		
		SmartDashboard.putNumber("RPM Offset", rpmOffset);
	}


	public double getRPMFromNDCY(double ndcY){
		int arrLength = normalYByRPM.length;
		// these start high because if ndcY is very large
		// the if statement in the for loop will never trigger
		int highKey=arrLength-1, lowKey=arrLength-2;
		
		//finds which experimental points the measured point is between
		for(int i=0; i<arrLength-1; i++){
			// keep going until the high value is above ndcY
			if(normalYByRPM[i+1][0] > ndcY){
				lowKey = i;
				highKey = i+1;
				break;
			}
		}

		// Don't need this beacuse I changed for(int i=0; i<arrLength-1; i++){ to only go to arryLength -
		// not sure why it was there in the first place
//		//taking care of ndcY values outside of the array
//		if(ndcY > normalYByRPM[arrLength-1][0]) {
//			return normalYByRPM[arrLength-1][1]; 
//		} else if(ndcY < normalYByRPM[0][0]) {
//			return normalYByRPM[0][1]; 
//		}

		//diff b/w: RPMs for the two experimental distances / // diff b/w: two experimental distances
		double slope = (normalYByRPM[highKey][1]-normalYByRPM[lowKey][1]) /
				 		(normalYByRPM[highKey][0]-normalYByRPM[lowKey][0]); 
		double diffMeasuredYLowPoint = ndcY - normalYByRPM[lowKey][0];  //diff b/w: experimental ndcY and measured ndcY	

		SmartDashboard.putNumber("LookupTable: lowkey-y", normalYByRPM[lowKey][0]);
		SmartDashboard.putNumber("LookupTable: highkey-y", normalYByRPM[highKey][0]);
		SmartDashboard.putNumber("LookupTable: real-y - lowKey-y", diffMeasuredYLowPoint);
		SmartDashboard.putNumber("LookupTable: slope", slope);
		
		try {
			return (diffMeasuredYLowPoint * slope + normalYByRPM[lowKey][1]) + SmartDashboard.getNumber("RPM Offset", rpmOffset) ; 
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("lookup table out of bounds");
			return 3800;
		}
	}

}
