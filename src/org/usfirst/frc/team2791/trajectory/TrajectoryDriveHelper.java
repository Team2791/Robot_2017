package org.usfirst.frc.team2791.trajectory;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;;
/**
 * TrajectoryDriveController.java
 * This controller drives the robot along a specified trajectory.
 * @author Tom Bottiglieri
 */
public class TrajectoryDriveHelper{ //eventually implements Loopale 

	private boolean reversed = false;
	

	Trajectory trajectory;
	TrajectoryFollower followerLeft;
	TrajectoryFollower followerRight;
	double direction;
	double heading;
	double kTurn = 0; //-3.0/80.0;

	public TrajectoryDriveHelper(boolean reversed_) {
		reversed = reversed_;
		followerLeft = new TrajectoryFollower("left", reversed);
		followerRight = new TrajectoryFollower("right", reversed);
		init();
	}
	
	public boolean onTarget() {
		return followerLeft.isFinishedTrajectory(); 
	}

	private void init() {
		followerLeft.configure(1.5, 0, 0, 1.0/15.0, 1.0/34.0);
		followerRight.configure(1.5, 0, 0, 1.0/15.0, 1.0/34.0);

	}

	public void loadProfile(Trajectory leftProfile, Trajectory rightProfile, double direction, double heading) {
		reset();
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
			Robot.drivetrain.setLeftRightMotorOutputs((speedLeft + turn), (speedRight -turn));
			//Robot.drivetrain.setLeftRightMotorOutputs(speedLeft + turn, speedRight - turn);
		}
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

		double turn = kTurn * angleDiff;

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