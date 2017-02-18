package org.usfirst.frc.team2791.trajectory;

import org.usfirst.frc.team2791.robot.Robot;;
/**
 * TrajectoryDriveController.java
 * This controller drives the robot along a specified trajectory.
 * @author Tom Bottiglieri
 */
public class TrajectoryDriveHelper{ //eventually implements Loopale 

  public TrajectoryDriveHelper() {
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
    	 Robot.drivetrain.setLeftRightMotorOutputs(0.0, 0.0);
    	 disable();
      } 
     else  {
          double speedLeft = getOutputs("left");
          double speedRight = getOutputs("right");
          double turn =getOutputs("turn");
          //double turn=0;
          Robot.drivetrain.setLeftRightMotorOutputs(speedLeft + turn, speedRight -turn);
          //Robot.drivetrain.setLeftRightMotorOutputs(speedLeft + turn, speedRight - turn);
    }
  }

  public void setTrajectory(Trajectory t) {
    this.trajectory = t;
  }

  public double getGoal() {
    return 0;
  }
  
  public double getOutputs(String needs){
	  double distanceL = direction * Robot.drivetrain.getLeftDistance();
      double distanceR = direction * Robot.drivetrain.getRightDistance();

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
      default: System.out.println("TrajectoryDrive doesn't know what you want from it");
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