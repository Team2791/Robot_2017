package org.usfirst.frc.team2791.robot.util.vision;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.util.DelayedBoolean;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class VisionNetworkTable implements ITableListener {

	private NetworkTable visionTargetsTable;
	private AnalyzedContour[] foundContours = {};

	public double distToBoiler = 0;
	public double rpm = 0;
	 
	public double MIN_SPEED_TO_CALC_DISTANCE = 0; //might not need this w/ center based targeting

	public double gyroOffset = 0;
	public double targetAngleError = 0;

	public DelayedBoolean robotNotTurning = new DelayedBoolean(.2);
	
	public final ShooterLookupTable lookupTable = new ShooterLookupTable();
	public VisionMath visionMath = new VisionMath();

	public VisionNetworkTable() {
		visionTargetsTable = NetworkTable.getTable("GRIP/myContoursReport");
		visionTargetsTable.addTableListener(this);

		SmartDashboard.putNumber("Min Speed to Calc Distance", MIN_SPEED_TO_CALC_DISTANCE);
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
//				System.out.println("Messed up reading network tables. Trying again?");
			}
		}


		// THIS IS A HACK
		// Ignore images unless we're not turning. This is to compensate for lag.
		if(robotNotTurning.update(Math.abs(Robot.drivetrain.gyro.getRate()) < 5)){
			try {
				targetAngleError = visionMath.calculateTargetAngleError(selectTarget().centerX);
				gyroOffset = targetAngleError - Robot.drivetrain.getGyroAngle();

			} catch (Exception e) {
				if(e.getMessage().equals("No Targets")) {
					System.out.println("Found no targets. Not changing variables");
				} else {
					System.out.println("vision angle calculation messed up");
				}	
			}
		}

		// THIS IS A HACK pt. 2
		// Ignore images unless moving fast enough. This is to compensate for lag
//		MIN_SPEED_TO_CALC_DISTANCE = SmartDashboard.getNumber("Min Speed to Calc Distance", MIN_SPEED_TO_CALC_DISTANCE);
//
//		if( Math.abs(Robot.drivetrain.getAverageVelocity()) > MIN_SPEED_TO_CALC_DISTANCE){
			try {
				distToBoiler = visionMath.calculateTargetDistance(selectTarget().centerY);
				rpm = lookupTable.getRPMFromNDCY(visionMath.getNormalizedY(selectTarget().centerY));
			} catch (Exception e) {
				if(e.getMessage().equals("No Targets")) {
//					System.out.println("Found no targets. Not changing variables");
				} else {
//					System.out.println("vision distance calculation messed up");
				}
			}

//		}
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
		visionMath.debug();
		
		AnalyzedContour myContour;
		try {
			myContour = selectTarget();
			SmartDashboard.putNumber("Vision/centerX", myContour.centerX);
			SmartDashboard.putNumber("Vision/centerY", myContour.centerY);
			SmartDashboard.putNumber("Vision/height", myContour.height);
			SmartDashboard.putNumber("Vision/width", myContour.width);	
			SmartDashboard.putNumber("Vision/solidity", myContour.solidity);
			SmartDashboard.putNumber("Vision/area", myContour.area);
			SmartDashboard.putNumber("Vision/Normalized centerY", visionMath.getNormalizedY(myContour.centerY));
		} catch (Exception e) {
			if(!e.getMessage().equals("No Targets"))
				System.err.println("Vision Debug Method failed");
		}
	}
	
	public class AnalyzedContour {
	    
	    public double area, centerY, centerX, height, width, solidity;

	    public AnalyzedContour(double area, double centerY, double centerX, double height, double width, double solidity) {
	        this.area = area;
	        // TODO make this not a hack. lol like we'll ever get around to that.

	        this.centerY = visionMath.SIZEY - centerY;
	        this.centerX = centerX;
	        this.height = height;
	        this.width = width;
	        this.solidity = solidity;
	    }
	   
	}
}


