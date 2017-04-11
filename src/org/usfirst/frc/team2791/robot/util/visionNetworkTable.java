package org.usfirst.frc.team2791.robot.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class visionNetworkTable implements ITableListener {
	
	private NetworkTable visionTargetsTable;
	private AnalyzedContour[] foundContours = {};

	public static double FOVX = 47;
	public static double FOVY = 36.2;
	public static double INCLINATION = 40;
	public static int SIZEX = 320;
	public static int SIZEY = 240;
	
	
	public visionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		visionTargetsTable.addTableListener(this);
		
	}
	
	public double getBoilerAngleError() {	
		double targetX = selectTarget().centerX;
		double ndcX = 2 * targetX / SIZEX - 1;
		double angle = Math.atan(Math.tan(Math.toRadians(FOVX / 2)) * ndcX);
		double x = Math.sin(Math.toRadians(angle));
		double z = Math.cos(Math.toRadians(angle));
		z *= Math.cos(Math.toRadians(INCLINATION));
		return Math.toDegrees(Math.atan(x/z));
	}

	private AnalyzedContour selectTarget() {
		AnalyzedContour[] possibleTargets;
		
		synchronized (foundContours) {
			possibleTargets = foundContours;
		}
		try {
			return possibleTargets[0];
		} catch (IndexOutOfBoundsException e) {
			return new AnalyzedContour(0,0,0,0,0,0);
		}
		
	}
	
	@Override
	public void valueChanged(ITable source, String key, Object value,
			boolean isNew) {
//		System.out.println("New value: "+key+" = "+value);
		
		synchronized (foundContours) {
			try {
				foundContours = getFoundContours();
			} catch (IndexOutOfBoundsException e){
				System.out.println("Messed up reading network tables. Trying again?");
			}
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
