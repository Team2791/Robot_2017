package org.usfirst.frc.team2791.robot.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class visionNetworkTable {
	
	public static NetworkTable visionTargetsTable;
	
	public visionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		
	}
	
	public double getBoilerAngleError() {
		
		double targetX = getFoundContours()[0].centerX;
		

		return targetX;
	}
	
	private AnalyzedContour[] getFoundContours() {
		double[] defaultArray = {-2791};
		
		// Get info from network tables
		double[] areas = visionTargetsTable.getNumberArray("area", defaultArray);
		double[] centerYs = visionTargetsTable.getNumberArray("centerY", defaultArray);
		double[] centerXs = visionTargetsTable.getNumberArray("centerX", defaultArray);
		double[] heights = visionTargetsTable.getNumberArray("height", defaultArray);
		double[] widths = visionTargetsTable.getNumberArray("width", defaultArray);
		double[] soliditys = visionTargetsTable.getNumberArray("solidity", defaultArray);
		
		// set up an array to store our found targets
		AnalyzedContour[] contourList = new AnalyzedContour[areas.length];
		
		System.out.println("areas length = "+areas.length);
		System.out.println("centerYs length = "+centerYs.length);
		System.out.println("centerXs length = "+centerXs.length);
		System.out.println("heights length = "+heights.length);
		System.out.println("widths length = "+widths.length);
		System.out.println("soliditys length = "+soliditys.length);
		
		
		
		
		for(int i=0; i < areas.length; ++i) {
			contourList[i] = new AnalyzedContour(areas[i], centerYs[i], centerXs[i], heights[i],
					widths[i], soliditys[i]); 
			
		}
		
		return contourList;
		
	}
	
	
	

}
