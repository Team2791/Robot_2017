package org.usfirst.frc.team2791.robot.util;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class VisionNetworkTable implements ITableListener {
	
	private NetworkTable visionTargetsTable;
	private AnalyzedContour[] foundContours = {};

	public static final double FOVX = 47;
	public static final double FOVY = 36.2;
	public static final double INCLINATION = 30;
	public static final int SIZEX = 240;
	public static final int SIZEY = 180;
	
	private final double FOCAL_LENGTH = 261.81; //in mm; from https://us.sourcesecurity.com/technical-details/cctv/image-capture/ip-cameras/axis-communications-axis-m1011.html
	private final double BOILER_CYLINDER_DIAMETER = 12.0;//inches //17.5
	
	private final double BOILER_TOP_TARGET_HEIGHT = 86.0;
	private final double CAMERA_HEIGHT = 23.0;
	
	
	private boolean freshImage = false;
	public double gyroOffset = 0;
	public double targetError = 0;
	
	public DelayedBoolean robotStill = new DelayedBoolean(.2);
	
	public VisionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		visionTargetsTable.addTableListener(this);
		
		SmartDashboard.putNumber("Camera Horizontal Offset", CONSTANTS.CAMERA_HORIZONTAL_OFFSET);
		
	}
	
	public void setVisionOffset(double offset){
		this.gyroOffset = offset;
	}
	
	public double getRealtimeBoilerAngleError() {
		return Robot.drivetrain.getGyroAngle() + gyroOffset;
	}
	
	public double getRealtimeDistanceToBoiler(){
		try{
			return calculateTargetDistance();
		} catch(Exception e){
//			System.out.println("Can't find distance");
		}
		return 0;
	}
	
	private double calculateTargetDistance() throws Exception {
		/*//uses the relationship: distance = targetWidth * focal length / targetWidthInPixels
		return (BOILER_CYLINDER_DIAMETER * FOCAL_LENGTH) / selectTarget().width;*/
		
		AnalyzedContour contour = selectTarget();
//		double left = contour.centerX - contour.width / 2;
//		double right = contour.centerX + contour.width / 2;
//		
//		double angle = Math.abs(calculateTargetAngleError(right) - calculateTargetAngleError(left));
//		return Math.tan(Math.toRadians(angle / 2)) * BOILER_CYLINDER_DIAMETER / 2;
//		return selectTarget().height;
		
//		double FOVSize = (BOILER_CYLINDER_DIAMETER / contour.width) * SIZEX/2.0;
//		return Math.cos(Math.toRadians(INCLINATION)) * (FOVSize / Math.tan(Math.toRadians(FOVX/2.0)));
	
		double bottomOfImageAngle = INCLINATION - FOVY/2.0;
		double targetAngleInImage = (contour.centerY / (float) SIZEY) * FOVY;
		double heightFromCamera = BOILER_TOP_TARGET_HEIGHT - CAMERA_HEIGHT;
		System.out.println("Target ccamera angle " + (bottomOfImageAngle + targetAngleInImage));
		
		return heightFromCamera / Math.tan(Math.toRadians(bottomOfImageAngle + targetAngleInImage));
	}
	
	private double calculateTargetAngleError(double centerX) throws Exception {
		double targetX = centerX;
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
			// select the target with the largest centerY value. This target is at the top of image.
			int selectedTargetIndex = 0;
			double selectedHeight = possibleTargets[0].centerY;
			for(int i=1; i < possibleTargets.length; i++){
				if(possibleTargets[i].centerY > selectedHeight){
					selectedTargetIndex = i;
					selectedHeight = possibleTargets[i].centerY;
				}
			}
			return possibleTargets[selectedTargetIndex];
			
		} catch (IndexOutOfBoundsException e) {
//			return new AnalyzedContour(0,0,0,0,0,0);
			throw new Exception("No Targets");
		}
		
	}
	
	@Override
	public void valueChanged(ITable source, String key, Object value,
			boolean isNew) {
		System.out.println("New value: "+key+" = "+value);
		
		synchronized (foundContours) {
			try {
				foundContours = getFoundContours();
			} catch (IndexOutOfBoundsException e){
				System.out.println("Messed up reading network tables. Trying again?");
			}
		}
		
		// THIS IS A HACK
		// Ignore images unless we're not moving. This is to compensate for lag.
		
		if(!robotStill.update(Math.abs(Robot.drivetrain.gyro.getRate()) < 5)) {
			return;
		}
		
		try {
			// update the gyro offset with the latest error information
			targetError = calculateTargetAngleError(selectTarget().centerX);
			gyroOffset = targetError - Robot.drivetrain.getGyroAngle();	
			
			
		} catch(Exception e) {
			if(e.getMessage().equals("No Targets")) {
				System.out.println("Found no targets. Not changing gyro offet");
			} else {
				System.out.println("SOME1THING MESSED UP AND WE'RE NOT DEALING WITH IT");
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
