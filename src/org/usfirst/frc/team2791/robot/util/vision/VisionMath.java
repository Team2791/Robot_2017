package org.usfirst.frc.team2791.robot.util.vision;

import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A Class to hold all mathematical calculations involving vision and image processing
 * @author Unnas
 *
 */
public class VisionMath {

	public static final double FOVX = 47;
	public static final double FOVY = 36.2;
	public final int SIZEX = 240;
	public final int SIZEY = 180;
	
	public static final double INCLINATION = 30;
	private final double CAMERA_HEIGHT = 23.0;
	
	public final double FOCAL_LENGTH = 261.81; //in mm; from https://us.sourcesecurity.com/technical-details/cctv/image-capture/ip-cameras/axis-communications-axis-m1011.html

	private double BOILER_TARGET_REAL_WIDTH_INCHES = 15.0; //using boiler cylinder's diameter
	private final double BOILER_TOP_TARGET_HEIGHT = 87.5; //86.0

	private double ndcY;
	private double cylinderTargetOffset; // because we are reflecting light off of a cylinder, the edges of the target are not always seen, this offset accounts for that

	public VisionMath(){
		SmartDashboard.putNumber("Camera Horizontal Offset", CONSTANTS.CAMERA_HORIZONTAL_OFFSET);
		SmartDashboard.putNumber("Observed Target Width in Inches", BOILER_TARGET_REAL_WIDTH_INCHES - cylinderTargetOffset);
		SmartDashboard.putNumber("Cylinder Offset", cylinderTargetOffset);
	}

	/**
	 * Based on WPI Vision Processing Papers: http://po.st/visionpapers. </br>
	 * This is accurate when using rectangular or 2-dimensional targets.
	 * The 3D curve of STEAMWorks's target makes it so that it is very hard to 
	 * know the width of the target that the camera can actually see. This is why
	 * The cylinderOffset is required, but is also a bit hard to find.
	 * 
	 * @param contourWidth the width of the contour found (in inches)
	 * @return the distance the contour is away from the camera (in inches)
	 */
	public double calculateTargetDistanceWithWidth(double targetWidth) throws Exception{

		double distNoOffset = (BOILER_TARGET_REAL_WIDTH_INCHES  * SIZEX) 
				/  (2 *  targetWidth * Math.tan(Math.toRadians(FOVX  / 2)));
		
		//This seems like a bad way of doing this
		if(distNoOffset > 140)
			cylinderTargetOffset = 0.0;
		else if(distNoOffset > 115)
			cylinderTargetOffset = 0.5;
		else if(distNoOffset > 95)
			cylinderTargetOffset = 1.1;
		else if(distNoOffset < 95)
			cylinderTargetOffset = 1.6;


		double targetWidthInches = BOILER_TARGET_REAL_WIDTH_INCHES - cylinderTargetOffset;
		double hyp = (targetWidthInches  * SIZEX) 
				/  (2 *  targetWidth * Math.tan(Math.toRadians(FOVX  / 2)));


		return Math.sqrt(Math.abs(Math.pow(hyp,2) - (4096)));
	}

	/**
	 * 
	 * @param targetY the y-coordinate of the center of the contour in the image (in pixels)
	 * @return the distance the contour is away from the camera (in inches)
	 * @throws Exception
	 */
	public double calculateTargetDistance(double targetY) throws Exception {

		double bottomOfImageAngle = INCLINATION - FOVY/2.0;
		double heightFromCamera = BOILER_TOP_TARGET_HEIGHT - CAMERA_HEIGHT;

//		*****Trig Formula (didn't work, but gave unique distances)*****//
//		 double targetAngleInImage = (targetY / (float) SIZEY) * FOVY;
//		 double theta = targetAngleInImage + bottomOfImageAngle;

//		//*****Trig Formula Modified (needs testing)*****//
		ndcY =  (2 * targetY) / (SIZEY); 
//		double targetToCenterAngle = Math.toDegrees(Math.atan(Math.tan(Math.toRadians(FOVY / 2)) * ndcY));
//		double targetAngleInImage = targetToCenterAngle + (FOVY /2);
		double targetAngleInImage = ndcY * FOVY/2.0; // MaxL: maybe this makes sense, maybe not
		double theta = bottomOfImageAngle + targetAngleInImage;

		//******Daisy's Formula (I don't think this will work for us, due to the different negations*****//
		//double ndcY =  -((2 * targetY ) / SIZEY  - 1);
		//double theta = ndcY * FOVY / 2 + INCLINATION;

//		return  calculateTargetDistanceWithWidth(targetY);
		return heightFromCamera / Math.tan(theta * Math.PI / 180);

	}

	public double getNormalizedY(double pointY){
		return (pointY - SIZEY/2.0) / (SIZEY/2.0);
	}
	/**
	 * 
	 * @param centerX  the x-coordinate of the center of the contour in the image (in pixels)
	 * @return the angle (in degrees) the camera is turned away from the center of the target
	 * @throws Exception
	 */
	public double calculateTargetAngleError(double centerX) throws Exception {
		double targetX = centerX;
		double ndcX = 2 * targetX / SIZEX - 1;
		double angle = Math.atan(Math.tan(Math.toRadians(FOVX / 2)) * ndcX);
		angle = Math.toDegrees(angle);
		double x = Math.sin(Math.toRadians(angle));
		double z = Math.cos(Math.toRadians(angle));
		z *= Math.cos(Math.toRadians(INCLINATION));
		return -Math.toDegrees(Math.atan(x/z)) - SmartDashboard.getNumber("Camera Horizontal Offset", CONSTANTS.CAMERA_HORIZONTAL_OFFSET);
	}
	
	public void debug(){
		cylinderTargetOffset = SmartDashboard.getNumber("Cylinder Offset", cylinderTargetOffset);
		SmartDashboard.putNumber("Observed Target Width in Inches", BOILER_TARGET_REAL_WIDTH_INCHES - cylinderTargetOffset);

	}
}
