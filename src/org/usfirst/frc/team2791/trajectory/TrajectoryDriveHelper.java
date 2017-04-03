package org.usfirst.frc.team2791.trajectory;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;;
/**
 * TrajectoryDriveController.java
 * This controller drives the robot along a specified trajectory. 
 * Uses inputs from {@link TrajectoryFollower Trajectory Followers} for each side of the robot.
 * @see TrajectoryFollower
 * @author Tom Bottiglieri
 * @author Unnas Hussain 
 */
public class TrajectoryDriveHelper{ //eventually implements Loopale 

	private boolean reversed = false;
	

	Trajectory trajectory;
	TrajectoryFollower followerLeft;
	TrajectoryFollower followerRight;
	double direction = 1; //reversing constant
	double heading;
	double kTurn; //-3.0/80.0;

	public TrajectoryDriveHelper(boolean reversed_) {
		
		reversed = reversed_;
		kTurn = SmartDashboard.getNumber("kTurn", 0.0375);

		followerLeft = new TrajectoryFollower("left");
		followerRight = new TrajectoryFollower("right");
		init();
	}
	
	public TrajectoryDriveHelper() {
		reversed = false;
		kTurn = SmartDashboard.getNumber("kTurn", 0.0375);

		followerLeft = new TrajectoryFollower("left");
		followerRight = new TrajectoryFollower("right");
		init();
	}
	
	public boolean onTarget() {
		return followerLeft.isFinishedTrajectory(); 
	}

	private void init() {
		
		followerLeft.configure(3.0, 0, 0, .09, .033);
		followerRight.configure(3.0, 0, 0, .09, .033);

	}

	public void loadProfile(Trajectory leftProfile, Trajectory rightProfile, double direction, double heading) {
		reset();

//		if(reversed){
//			this.direction = direction;
//		}
//		else{
//			direction = Math.abs(direction);
//		}
		
		followerLeft.setTrajectory(leftProfile);
		followerRight.setTrajectory(rightProfile);
		
		this.direction = direction;
		this.heading = heading;
	}

	public void loadProfileNoReset(Trajectory leftProfile, Trajectory rightProfile) {
		followerLeft.setTrajectory(leftProfile);
		followerRight.setTrajectory(rightProfile);
	}

	public void reset() {
		followerLeft.reset();
		followerRight.reset();
		Robot.drivetrain.resetEncoders();
	}

	public int getFollowerCurrentSegment() {
		return followerLeft.getCurrentSegment();
	}

	public int getNumSegments() {
		return followerLeft.getNumSegments();
	}

	//where the motors actually get values
	public void update() {
		if (onTarget()) {
			Robot.drivetrain.setLeftRightMotorOutputs(0.0, 0.0);
			disable();
		} 
		else  {
			double[] outputs = getOutputs();
			//double turn=0;
			double speedLeft = outputs[0];
			double speedRight = outputs[1];
			double turn = outputs[2];
			Robot.drivetrain.setLeftRightMotorOutputs(speedLeft + turn, speedRight - turn);
			//Robot.drivetrain.setLeftRightMotorOutputs(speedLeft + turn, speedRight - turn);
		}
		debug();
	}

	public void setTrajectory(Trajectory t) {
		this.trajectory = t;
	}

	public double getGoal() {
		return 0;
	}

	public double[] getOutputs(){
		double distanceL = direction * Robot.drivetrain.getLeftDistance();
		double distanceR = direction * Robot.drivetrain.getRightDistance();

		double speedLeft = direction * followerLeft.calculate(distanceL);
		double speedRight = direction * followerRight.calculate(distanceR);

		double goalHeading = followerLeft.getHeading();
		double observedHeading = Robot.drivetrain.getGyroAngleInRadians();

		double angleDiffRads = ChezyMath.getDifferenceInAngleRadians(observedHeading, goalHeading);
		double angleDiff = Math.toDegrees(angleDiffRads);
		
		SmartDashboard.putNumber("Traj AngleDiff", angleDiff);

		double turn = kTurn * angleDiff;
		
		if(Robot.drivetrain.gyroDisabled);{
			turn = 0;
		}

		double[] outArr = new double[3];
		outArr[0] = speedLeft;
		outArr[1] = speedRight;
		outArr[2] = turn;

		return outArr;

	}

	/*
	 * From the originally extended Controller
	 */
	protected boolean enabled = true;

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	public boolean enabled() {
		return enabled;
	}
	
	private void debug(){
		SmartDashboard.putNumber("kTurn", kTurn);
	}
}