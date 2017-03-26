package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class corresponds to the combined intake/climber subsystem. There are two 775pros running off of two Sparks which
 * receive signal from a branched PWM. This motor output controls the intake belts and also the climbing axle. The system
 * has a ratcheting gear which is joined with a pall during climbing. The pall is activated with a piston. This piston is
 * controlled by the same solenoid that controls the wing expansions at the beginning of the game. The intake is also on 
 * two pistons which push it out to pick up balls.
 * 
 * @author team2791: See Robot.java for contact info
 */
public class ShakerIntake extends Subsystem {
	private final double BALLS_IN_POWER = -1.0;
	private final double CLIMBING_VBUS = 0.75;

	public Talon intakeSpark;
	private Solenoid intakeSolenoid;
	public Solenoid ratchetWingSolenoid;
	
	public ShakerIntake(){
		intakeSpark = new Talon(RobotMap.INTAKE_SPARK_PORT);

		intakeSolenoid = new Solenoid(RobotMap.PCM_MODULE, RobotMap.INTAKE_CHANNEL);
		ratchetWingSolenoid = new Solenoid(RobotMap.PCM_MODULE, RobotMap.WING_CHANNEL);
		
		ratchetWingSolenoid.set(true);
		intakeSolenoid.set(false);//all Pistons should be closed (false) at beginning
	}
	
	public void initDefaultCommand(){
		ratchetWingSolenoid.set(true);
		intakeSolenoid.set(false);//all Pistons should be closed (false) at beginning
	}
	
	//***********Ratchet Wing***********//
	public void engageRatchetWing(){
		ratchetWingSolenoid.set(false);
	}
	
	public void disengageRatchetWing(){
		ratchetWingSolenoid.set(true);
	}
	public boolean isRatchetWingDisengaged(){
		return ratchetWingSolenoid.get();
	}
	
	//***********Intake/Climber Helper Methods***********//

	/**
	 * sets intake to in(true) or out (false)
	 */
	public void moveIntakeOut(boolean inOrOut){
		intakeSolenoid.set(inOrOut);
	}

	public void motorOnIntake(){//negative is proper direction
		System.out.print("Intake going in");
		intakeSpark.setSpeed(SmartDashboard.getNumber("Intake Balls Speed",BALLS_IN_POWER));
	}

	public void motorOffIntake(){
		intakeSpark.setSpeed(0.0);
	}

	public void motorOnClimber(){//should be positive
		ratchetWingSolenoid.set(false);
		// SmartDashboard.getNumber("Climbing Vbus",CLIMBING_VBUS)
		intakeSpark.setSpeed(1.0);

	}
	
	public void motorOnClimberSlow(){//should be positive
		ratchetWingSolenoid.set(false);
		// SmartDashboard.getNumber("Climbing Vbus",CLIMBING_VBUS)
		intakeSpark.setSpeed(0.35);

	}

	public void debug(){
		SmartDashboard.putNumber("Intake/Climber current usage", getCurrentUsage());
	}
	public double getCurrentUsage() {
		return Robot.pdp.getCurrent(RobotMap.POWER_CLIMBER_INTAKE) + Robot.pdp.getCurrent(RobotMap.POWER_CLIMBER_INTAKE_2);
	}
}

