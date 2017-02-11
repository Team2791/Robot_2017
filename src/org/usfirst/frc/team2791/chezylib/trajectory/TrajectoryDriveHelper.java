package org.usfirst.frc.team2791.chezylib.trajectory;

import org.usfirst.frc.team2791.chezylib.trajectory.io.ChezyMath;
import org.usfirst.frc.team2791.robot.Robot;;
/**
 * TrajectoryDriveController.java
 * This controller drives the robot along a specified trajectory.
 * This is the class that actually sends motor outputs according to a path
 * @author Tom Bottiglieri
 */
public class TrajectoryDriveHelper{ //eventually implements Loopale 

  public TrajectoryDriveHelper() {
	System.out.println("Initialized Drive Helper");
    init();
  }
  
  Trajectory trajectory;
  TrajectoryFollower followerLeft = new TrajectoryFollower("left");
  TrajectoryFollower followerRight = new TrajectoryFollower("right");
  double direction;
  double heading;
  double kTurn = -3.0/80.0;

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
    	 Robot.drivetrain.shakerDrive(0.0, 0.0);
    	 disable();
      } 
     else  {
    	
          double speedLeft = getOutputs("left");
          double speedRight = getOutputs("right");
          double turn =getOutputs("turn");
          Robot.drivetrain.shakerDrive(speedLeft + turn, speedRight - turn);
    }
  }

  public void setTrajectory(Trajectory t) {
    this.trajectory = t;
  }

  public double getGoal() {
    return 0;
  }
  
  public double getOutputs(String needs){
	  double distanceL = direction * Robot.drivetrain.getLeftEncoderDistance();
      double distanceR = direction * Robot.drivetrain.getRightEncoderDistance();

      double speedLeft = direction * followerLeft.calculate(distanceL);
      double speedRight = direction * followerRight.calculate(distanceR);
      
      double goalHeading = followerLeft.getHeading();
      double observedHeading = Robot.drivetrain.getGyroAngleInRadians();

      double angleDiffRads = ChezyMath.getDifferenceInAngleRadians(observedHeading, goalHeading);
      double angleDiff = Math.toDegrees(angleDiffRads);

      double turn = kTurn * angleDiff;
      
      switch(needs){
      case "left": return speedLeft;
      case "right": return speedRight;
      case "turn": return turn;
      default: System.out.println("TrajectoryDrive is sending 0.0 for some reason");
    	  return 0.0;
      }
	  
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
}