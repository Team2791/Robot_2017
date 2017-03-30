package org.usfirst.frc.team2791.trajectory;

import org.usfirst.frc.team2791.trajectory.Trajectory.Segment;

/**
 * Base class for an autonomous path.
 * A path has a pair of Trajectories, one left and right. </p>
 * Paths can be inverted in the x and y axis
 * @author Jared341
 * @author Unnas Hussain
 */
public class Path {
  protected Trajectory.Pair go_left_pair_;
  protected String name_;
  protected boolean go_left_;
  
  
  /**
   * @param name name of the Path, must be a valid Java Class name 
   * @param go_left_pair the left-right Pair
   */
  public Path(String name, Trajectory.Pair go_left_pair) {
    name_ = name;
    go_left_pair_ = go_left_pair;
    go_left_ = true;
  }
  
  public String getName() { return name_; }
  
  /**
   * Uninverts y axis
   */
  public void goLeft() { 
    go_left_ = true; 
    go_left_pair_.left.setInvertedY(false);
    go_left_pair_.right.setInvertedY(false);
  }
  
  /**
   * Inverts Y axis
   */
  public void goRight() {
    go_left_ = false; 
    go_left_pair_.left.setInvertedY(true);
    go_left_pair_.right.setInvertedY(true);
  }
  
  public Trajectory getLeftWheelTrajectory() {
    return (go_left_ ? go_left_pair_.left : go_left_pair_.right);
  }
  
  public Trajectory getRightWheelTrajectory() {
    return (go_left_ ? go_left_pair_.right : go_left_pair_.left);
  }
  
  /**
   * @param segmentNum segment that needs to be checked for inverting
   * @return true if the left and right segment have the same position and velocity
   */
  public boolean canFlip(int segmentNum) {
    Segment a = go_left_pair_.right.getSegment(segmentNum);
    Segment b = go_left_pair_.left.getSegment(segmentNum);
    return (a.pos == b.pos) && (a.vel == b.vel);
  }

  /**
   * @return the final angle the robot will be heading
   */
  public double getEndHeading() {
    int numSegments = getLeftWheelTrajectory().getNumSegments();
    Segment lastSegment = getLeftWheelTrajectory().getSegment(numSegments - 1);
    return lastSegment.heading;
  }
  
}
