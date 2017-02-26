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
		kp_ = SmartDashboard.getNumber("kp", 0);
		ki_ = SmartDashboard.getNumber("ki", 0);
		kd_ = SmartDashboard.getNumber("kd", 0);
		kv_ = SmartDashboard.getNumber("kv", 0);
		ka_ = SmartDashboard.getNumber("ka", 0);
	}

	public void configure(double kp, double ki, double kd, double kv, double ka) {
		kp_ = SmartDashboard.getNumber("kp", kp);
		ki_ = SmartDashboard.getNumber("ki", ki);
		kd_ = SmartDashboard.getNumber("kd", kd);
		kv_ = SmartDashboard.getNumber("kv", kv);
		ka_ = SmartDashboard.getNumber("ka", ka);

		SmartDashboard.putNumber("kp",kp_);
		SmartDashboard.putNumber("ki",ki_);
		SmartDashboard.putNumber("kd",kd_);
		SmartDashboard.putNumber("kv",kv_);
		SmartDashboard.putNumber("ka",ka_);

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

			debug(distance_so_far, error, output, segment, current_segment);

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

	private void debug(double distance_so_far, double error, double output, Segment segment, int current_segment){

		SmartDashboard.putString(name+"PosGoal v\n PosActual:", segment.pos+":"+distance_so_far);

		double currentVelocity;
		double currentAcceleration;
		
		if(name == "left") {
			currentVelocity = Robot.drivetrain.getLeftVelocity(); 
			currentAcceleration = Robot.drivetrain.getLeftAcceleration(); 
			//System.out.println("ACC:"+currentAcceleration);
		} else {
			currentVelocity = Robot.drivetrain.getRightVelocity(); 
			currentAcceleration = Robot.drivetrain.getRightAcceleration(); 
			//System.out.println("ACC:"+currentAcceleration);
		}

		double velocityError = segment.vel-currentVelocity;
		SmartDashboard.putString(name+"VelGoal v\n VelActual:", segment.vel+":"+currentVelocity);

		double accelerationError = segment.acc - currentAcceleration;
		SmartDashboard.putString(name+"AccGoal v\n AccActual:", segment.acc+":"+currentAcceleration);

		SmartDashboard.putNumber(name+"PosError",error);
		SmartDashboard.putNumber(name+"VelError",velocityError);
		SmartDashboard.putNumber(name+"AccError",accelerationError);
		System.out.println("Just looked at segment #"+current_segment);

	}
}
