package org.usfirst.frc.team2791.robot.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class visionNetworkTable implements ITableListener {
	
	private NetworkTable visionTargetsTable;
	private AnalyzedContour[] foundContours = {};

	private double FOVX = 47;
	private  double FOVY = 36.2;
	private double INCLINATION = 0;
	private int SIZEX = 320;
	private int SIZEY = 240;
	
	private boolean freshImage = false;
	
	
	
	public visionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		visionTargetsTable.addTableListener(this);
		
	}
	
	public double getBoilerAngleError() {
		double targetX = selectTarget().centerX;
		double ndcX = 2 * targetX / SIZEX - 1;
		double angle = Math.atan(Math.tan(Math.toRadians(FOVX / 2)) * ndcX);
		angle = Math.toDegrees(angle);
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
		
		for(int i=0; i < areas.length; ++i) {
			contourList[i] = new AnalyzedContour(areas[i], centerYs[i], centerXs[i], heights[i],
					widths[i], soliditys[i]);
		}
		
		return contourList;
		
	}
	
	
	

}
