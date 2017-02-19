package org.usfirst.frc.team2791.robot.subsystems;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerIntake extends Subsystem{ //code for intake and climber
	private final double BALLS_IN_POWER = -1.0;
	private final double CLIMBING_VBUS = 0.75;

	public Talon intakeSpark;
	private Solenoid intakeSolenoid;
	public Solenoid ratchetWingSolenoid;

	public ShakerIntake(){
		intakeSpark = new Talon(RobotMap.INTAKE_SPARK_PORT);

		intakeSolenoid = new Solenoid(RobotMap.PCM_MODULE, RobotMap.INTAKE_CHANNEL);
		ratchetWingSolenoid = new Solenoid(RobotMap.PCM_MODULE, RobotMap.WING_CHANNEL);
		//		wingSolenoid.set(true);
		intakeSolenoid.set(false);//all Pistons should be closed (true) at beginning
	}

	public void initDefaultCommand(){
		System.out.println("Initiating intake");
	}
	
	public void engageRatchetWing(){
		ratchetWingSolenoid.set(false);
	}
	
	public void disengageRatchetWing(){
		ratchetWingSolenoid.set(true);
	}
	public void moveIntakeOut(boolean yes){
		intakeSolenoid.set(yes);
//		System.out.print("The intake movedThe intake movedThe intake movedThe intake moved");
	}

	public void motorOnIntake(){//negative is proper direction
		System.out.print("Intake going in");
		intakeSpark.setSpeed(BALLS_IN_POWER);
	}

	public void motorOffIntake(){
//		System.out.print("Intake/Climer stoppingIntake/Climer stoppingIntake/Climer stoppingIntake/Climer stopping");
		intakeSpark.setSpeed(0.0);
	}

	public void motorOnClimber(){//should be positive
		ratchetWingSolenoid.set(false);
//		System.out.print("climber going inclimber going inclimber going inclimber going in");
		intakeSpark.setSpeed(CLIMBING_VBUS);

	}

	// TODO RENAME THIS AND PUT THE LOGIC IN A COMMAND
	//	public boolean stopMotor(){
	//		if(getClimberCurrent()>5.0){
	//			intakeSpark.stopMotor();
	//			System.out.println("Climbing ceased; remove me from this rope");
	//			return true;
	//		}
	//		return false;
	//	}

	public void debug(){
		SmartDashboard.putNumber("Intake/Climber current usage", getCurrentUsage());
	}
	public double getCurrentUsage() {
		return Robot.pdp.getCurrent(3);
	}
}

