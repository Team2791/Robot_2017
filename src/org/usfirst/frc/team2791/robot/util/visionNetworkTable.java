package org.usfirst.frc.team2791.robot.util;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class visionNetworkTable implements ITableListener {
	
	private NetworkTable visionTargetsTable;
	private AnalyzedContour[] foundContours = {};

	private double FOVX = 47;
	private  double FOVY = 36.2;
	private double INCLINATION = 0;
	private int SIZEX = 240;
	private int SIZEY = 180;
	
	private final double FOCAL_LENGTH = 4.4;
	private final double BOILER_CYLINDER_DIAMETER = 17.5;//inches
	
	private boolean freshImage = false;
	public double gyroOffset = 0;
	public double targetError = 0;
	
	public DelayedBoolean robotStill = new DelayedBoolean(.25);
	
	public visionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		visionTargetsTable.addTableListener(this);
		
		SmartDashboard.putNumber("Camera Horizontal Offset", CONSTANTS.CAMERA_HORIZONTAL_OFFSET);
		
	}
	
	public double getRealtimeBoilerAngleError() {
		return Robot.drivetrain.getGyroAngle() + gyroOffset;
	}
	
	public double getRealtimeDistanceToBoiler() throws Exception{
		//uses the relationship: distance = targetWidth * focal length / targetWidthInPixels
		return (BOILER_CYLINDER_DIAMETER * FOCAL_LENGTH) / selectTarget().width;
		
	}
	
	private double calculateTargetAngleError() throws Exception {
		double targetX = selectTarget().centerX;
		double ndcX = 2 * targetX / SIZEX - 1;
		double angle = Math.atan(Math.tan(Math.toRadians(FOVX / 2)) * ndcX);
		angle = Math.toDegrees(angle);
		double x = Math.sin(Math.toRadians(angle));
		double z = Math.cos(Math.toRadians(angle));
		z *= Math.cos(Math.toRadians(INCLINATION));
		return -Math.toDegrees(Math.atan(x/z)) - SmartDashboard.getNumber("Camera Horizontal Offset", CONSTANTS.CAMERA_HORIZONTAL_OFFSET);
	}

	/**
	 * This method will filter the contours and select the target we should aim at (either boiler ring). 
	 * @return
	 * @throws Exception 
	 */
	private AnalyzedContour selectTarget() throws Exception {
		AnalyzedContour[] possibleTargets;
		
		synchronized (foundContours) {
			possibleTargets = foundContours;
		}
		
		// If there are no targets return one right infront of the robot so it stops moving.
		// TODO tell the robot not to shoot.
		try {
			return possibleTargets[0];
		} catch (IndexOutOfBoundsException e) {
//			return new AnalyzedContour(0,0,0,0,0,0);
			throw new Exception("No Targets");
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
		
		// THIS IS A HACK
		// Ignore images unless we're not moving. This is to compensate for lag.
		
		if(!robotStill.update(Math.abs(Robot.drivetrain.gyro.getRate()) < 2)) {
			return;
		}
		
		try {
			// update the gyro offset with the latest error information
			targetError = calculateTargetAngleError();
			gyroOffset = targetError - Robot.drivetrain.getGyroAngle();	
			
			
		} catch(Exception e) {
			if(e.getMessage().equals("No Targets")) {
				System.out.println("Found no targets. Not changing gyro offet");
			} else {
				System.out.println("SOMETHING MESSED UP AND WE'RE NOT DEALING WITH IT");
			}
		}

		
	}
	
	private AnalyzedContour[] getFoundContours() {
		double[] defaultArray = {};
		
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
	
	// CALL THIS EVERY LOOP
	// THROW BACK TO NOT COMMAND BASED
	public void run() {
		robotStill.update(Math.abs(Robot.drivetrain.gyro.getRate()) < 2);
	}
	

}
