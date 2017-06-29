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
	public static final int SIZEX = 240;
	public static final int SIZEY = 180;

	public static final double INCLINATION = 30;
	private final double CAMERA_HEIGHT = 23.0;
	private final double FOCAL_LENGTH = 261.81; //in mm; from https://us.sourcesecurity.com/technical-details/cctv/image-capture/ip-cameras/axis-communications-axis-m1011.html

	private double BOILER_TARGET_REAL_WIDTH_INCHES = 15.0; //using boiler cylinder's diameter
	private final double BOILER_TOP_TARGET_HEIGHT = 87.5; //86.0

	private double cylinderTargetOffset; // because we are reflecting light off of a cylinder, the edges of the target are not always seen, this offset accounts for that

	public final ShooterLookupTable lookupTable = new ShooterLookupTable();
	public double distToBoiler = 0;
	public double rpm = 0;

	public double MIN_SPEED_TO_CALC_DISTANCE = .5; //might not need this w/ center based targeting

	public double gyroOffset = 0;
	public double targetAngleError = 0;

	public DelayedBoolean robotNotTurning = new DelayedBoolean(.2);

	public VisionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		visionTargetsTable.addTableListener(this);

		SmartDashboard.putNumber("Camera Horizontal Offset", CONSTANTS.CAMERA_HORIZONTAL_OFFSET);
		SmartDashboard.putNumber("Observed Target Width in Inches", BOILER_TARGET_REAL_WIDTH_INCHES - cylinderTargetOffset);
		SmartDashboard.putNumber("Min Speed to Calc Distance", MIN_SPEED_TO_CALC_DISTANCE);
		SmartDashboard.putNumber("Cylinder Offset", cylinderTargetOffset);

	}

	public void setVisionOffset(double offset){
		this.gyroOffset = offset;
	}

	public double getRealtimeBoilerAngleError() {
		return Robot.drivetrain.getGyroAngle() + gyroOffset;
	}


	public double getRealtimeBoilerDistanceError(double target){
		return distToBoiler - target;
	}

	public double getRealtimeDistanceToBoiler(){
		return distToBoiler;
	}

	public double getDistanceBasedRPM(){
		return rpm;
	}

	/**
	 * Based on WPI Vision Processing Papers: http://po.st/visionpapers
	 * More accurate when it's closer to the target and the width of the contour is better defined.
	 * To be honest, there is a very large chance that inconsistent lighting will mess this up
	 */
	private double calculateTargetDistanceWithWidth() throws Exception{
		AnalyzedContour contour = selectTarget();
		double targetWidthInches = BOILER_TARGET_REAL_WIDTH_INCHES - cylinderTargetOffset;
		double hyp = (targetWidthInches  * SIZEX) 
				/  (2 *  contour.width * Math.tan(Math.toRadians(FOVX  / 2)));
		return Math.sqrt(Math.pow(hyp,2) - (4096));
	}

	private double calculateTargetDistance(double targetY) throws Exception {

		double bottomOfImageAngle = INCLINATION - FOVY/2.0;
		double heightFromCamera = BOILER_TOP_TARGET_HEIGHT - CAMERA_HEIGHT;

		//*****Trig Formula (didn't work, gave unique distances*****//
		// double targetAngleInImage = (targetY / (float) SIZEY) * FOVY;
		// double theta = targetAngleInImage + bottomOfImageAngle;
		
		//*****Trig Formula Modified (needs testing)*****//
		double ndcY =  (2 * targetY) / (SIZEY - 1); 
		double targetToCenterAngle = Math.toDegrees(Math.atan(Math.tan(Math.toRadians(FOVY / 2)) * ndcY));
		double targetAngleInImage = targetToCenterAngle + (FOVY /2);
		double theta = bottomOfImageAngle + targetAngleInImage;

		//******Daisy's Formula (I don't think this will work for us, due to the different negations*****//
		//double ndcY =  -((2 * targetY ) / SIZEY  - 1);
		//double theta = ndcY * FOVY / 2 + INCLINATION;

		return heightFromCamera / Math.tan(theta * Math.PI / 180);

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
	 * @throws Exception "No Targets" if there are no found targets
	 */
	private AnalyzedContour selectTarget() throws Exception {
		AnalyzedContour[] possibleTargets;

		synchronized (foundContours) {
			possibleTargets = foundContours;
		}

		// If there are no targets return one right in front of the robot so it stops moving.
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
	/**
	 * Where vision variables are updated
	 */
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
		// Ignore images unless we're not turning. This is to compensate for lag.
		if(robotNotTurning.update(Math.abs(Robot.drivetrain.gyro.getRate()) < 5)){
			try {
				targetAngleError = calculateTargetAngleError(selectTarget().centerX);
				gyroOffset = targetAngleError - Robot.drivetrain.getGyroAngle();

			} catch (Exception e) {
				if(e.getMessage().equals("No Targets")) {
					System.out.println("Found no targets. Not changing variables");
				} else {
					System.out.println("vision angle calculation messed up");
				}	
			}
		}

		// THIS IS A HACK
		// Ignore images unless moving fast enough. This is to compensate for lag
		MIN_SPEED_TO_CALC_DISTANCE = SmartDashboard.getNumber("Min Speed to Calc Distance", MIN_SPEED_TO_CALC_DISTANCE);

		if( Math.abs(Robot.drivetrain.getAverageVelocity()) > MIN_SPEED_TO_CALC_DISTANCE){
			try {
				distToBoiler = calculateTargetDistance(selectTarget().centerY);
				rpm = lookupTable.getRPMfromDistance(distToBoiler);
			} catch (Exception e) {
				if(e.getMessage().equals("No Targets")) {
					System.out.println("Found no targets. Not changing variables");
				} else {
					System.out.println("vision distance calculation messed up");
				}
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
		robotNotTurning.update(Math.abs(Robot.drivetrain.gyro.getRate()) < 2);
	}


	public void debug(){
		cylinderTargetOffset = SmartDashboard.getNumber("Cylinder Offset", cylinderTargetOffset);
		SmartDashboard.putNumber("Observed Target Width in Inches", BOILER_TARGET_REAL_WIDTH_INCHES - cylinderTargetOffset);

		AnalyzedContour myContour;
		try {
			myContour = selectTarget();
			SmartDashboard.putNumber("Vision/centerX", myContour.centerX);
			SmartDashboard.putNumber("Vision/centerY", myContour.centerY);
			SmartDashboard.putNumber("Vision/height", myContour.height);
			SmartDashboard.putNumber("Vision/width", myContour.width);	
			SmartDashboard.putNumber("Vision/solidity", myContour.solidity);
			SmartDashboard.putNumber("Vision/area", myContour.area);
		} catch (Exception e) {
			if(!e.getMessage().equals("No Targets"))
				System.err.println("Vision Debug Method failed");
		}
	}
}
