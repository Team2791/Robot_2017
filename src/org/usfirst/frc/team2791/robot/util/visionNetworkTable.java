package org.usfirst.frc.team2791.robot.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class visionNetworkTable implements ITableListener {
	
	private NetworkTable visionTargetsTable;
	private AnalyzedContour[] foundContours = {};
	
	
	public visionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		visionTargetsTable.addTableListener(this);
		
	}
	
	public double getBoilerAngleError() {
		
		AnalyzedContour[] possibleTargets;
		
		synchronized (foundContours) {
			possibleTargets = foundContours;
		}
		
		double targetX;
		try {
			targetX = possibleTargets[0].centerX;
		} catch (Exception e) {
			targetX = 0;
		}
		
		return targetX;
	}
	
	@Override
	public void valueChanged(ITable source, String key, Object value,
			boolean isNew) {
//		System.out.println("New value: "+key+" = "+value);
		
		
		synchronized (foundContours) {
//			while(true) { // Try to get info until it works
				try {
					foundContours = getFoundContours();
//					break;
				} catch (IndexOutOfBoundsException e){
					System.out.println("Messed up reading network tables. Trying again?");
				}
//			}
		}
		
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
		
//		System.out.println("areas length = "+areas.length);
//		System.out.println("centerYs length = "+centerYs.length);
//		System.out.println("centerXs length = "+centerXs.length);
//		System.out.println("heights length = "+heights.length);
//		System.out.println("widths length = "+widths.length);
//		System.out.println("soliditys length = "+soliditys.length);
		
		for(int i=0; i < areas.length; ++i) {
			contourList[i] = new AnalyzedContour(areas[i], centerYs[i], centerXs[i], heights[i],
					widths[i], soliditys[i]); 
			
		}
		
		return contourList;
		
	}
	
	
	

}
