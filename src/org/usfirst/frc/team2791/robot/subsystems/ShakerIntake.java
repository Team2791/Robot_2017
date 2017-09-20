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
	private final double CLIMBING_VBUS = 1.0;
	private final double CLIMBING_VBUS_SLOW = 0.35;

	public Talon intakeSpark;
	
	public boolean isClimbing = false;
	private Solenoid intakeSolenoid;
	
	/**
	 * safety solenoid that activateds the pall piston to prevent slipping during a climb
	 */
	public Solenoid ratchetWingSolenoid;
	
	public ShakerIntake(){
		
	
		
		intakeSpark = new Talon(RobotMap.INTAKE_SPARK_PORT);

		intakeSolenoid = new Solenoid(RobotMap.PCM_MODULE, RobotMap.INTAKE_CHANNEL);
		ratchetWingSolenoid = new Solenoid(RobotMap.PCM_MODULE, RobotMap.WING_CHANNEL);

		disengageRatchetWing();//do not want as default command operator lets go of climb before t=0 (do not want disengaging)
	}
	
	public void initDefaultCommand(){}
	
	//***********Intake/Climber Helper Methods***********//

	/**
	 * @param inOrOut true = open intake to pull in balls / false = keep intake closed
	 */
	public void setIntakePosition(boolean inOrOut){
		intakeSolenoid.set(inOrOut);
	}

	public void motorOnIntake(){//negative is proper direction
		isClimbing = false;
		disengageRatchetWing();
		intakeSpark.setSpeed(SmartDashboard.getNumber("Intake Speed (positive ONLY for climb)",BALLS_IN_POWER));
	}

	public void motorOffIntake(){
		isClimbing = false;

		intakeSpark.setSpeed(0.0);
	}

	public void motorOnClimber(){//should be positive
		isClimbing = true;
		engageRatchetWing();
		intakeSpark.setSpeed(CLIMBING_VBUS);
	}
	
	public void motorOnClimberSlow(){//should be positive
		isClimbing = true;

		engageRatchetWing();
		intakeSpark.setSpeed(CLIMBING_VBUS_SLOW);

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
	
	//***********Debug Helper Methods***********//
	
	/**
	 * @return total intake/climber usage
	 */
	public double getCurrentUsage() {
		return Robot.pdp.getCurrent(RobotMap.POWER_CLIMBER_INTAKE) + Robot.pdp.getCurrent(RobotMap.POWER_CLIMBER_INTAKE_2);
	}
	
	public void debug(){
		SmartDashboard.putNumber("Intake/Climber current usage", getCurrentUsage());
	}
	
}

