package org.usfirst.frc.team2791.trajectory;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.trajectory.Trajectory.Segment;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * PID + Feedforward controller for following a Trajectory.
 *
 * @author Jared341
 */
public class TrajectoryFollower {

  private double kp_;
  private double ki_;  // Not currently used, but might be in the future.
  private double kd_;
  private double kv_;
  private double ka_;
  private double last_error_;
  private double current_heading = 0;
  private int current_segment;
  private Trajectory profile_;
  public String name;

  public TrajectoryFollower(String name) {
    this.name = name;
  }

  public void configure(double kp, double ki, double kd, double kv, double ka) {
    kp_ = SmartDashboard.getNumber(name+"-kp",kp);
    ki_ = SmartDashboard.getNumber(name+"-ki",ki);
    kd_ = SmartDashboard.getNumber(name+"-kd",kd);
    kv_ = SmartDashboard.getNumber(name+"-kv",kv);
    ka_ = SmartDashboard.getNumber(name+"-ka",ka);
  }

  public void reset() {
    last_error_ = 0.0;
    current_segment = 0;
  }

  public void setTrajectory(Trajectory profile) {
    profile_ = profile;
  }

  public double calculate(double distance_so_far) {

    if (current_segment < profile_.getNumSegments()) {
      Trajectory.Segment segment = profile_.getSegment(current_segment);
      double error = segment.pos - distance_so_far;
      double output = kp_ * error + kd_ * ((error - last_error_)
              / segment.dt - segment.vel) + (kv_ * segment.vel
              + ka_ * segment.acc);

      last_error_ = error;
      current_heading = segment.heading;
      current_segment++;
      
      debug(distance_so_far, error, output, segment);
      
      return output;
    } else {
      return 0;
    }
  }

public double getHeading() {
    return current_heading;
  }

  public boolean isFinishedTrajectory() {
    return current_segment >= profile_.getNumSegments();
  }
  
  public int getCurrentSegment() {
    return current_segment;
  }
  
  public int getNumSegments() {
    return profile_.getNumSegments();
  }
  
  private void debug(double distance_so_far, double error, double output, Segment segment){

      SmartDashboard.putNumber(name + " PosSensor", distance_so_far);
      SmartDashboard.putNumber(name + " PosGoal", segment.pos);
      SmartDashboard.putNumber(name + " PosError", error);
      
      double currentVelocity =  Robot.drivetrain.getAverageVelocity(); 
      double velocityError=segment.vel-currentVelocity;
      
      
      SmartDashboard.putNumber(name + " VelSensor", currentVelocity);
      SmartDashboard.putNumber(name + " VelGoal", segment.vel);
      SmartDashboard.putNumber(name + " VelError", velocityError);
      
      double currentAcceleration =  Robot.drivetrain.getAverageAcceleration(); 
      double accelerationError=segment.acc-currentAcceleration;
      
      SmartDashboard.putNumber(name + " AccSensor", currentAcceleration);
      SmartDashboard.putNumber(name + " AccGoal", segment.acc);
      SmartDashboard.putNumber(name + " AccError", accelerationError);
      
	  
  }
}
