package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Runs the {@link ShakerIntake} motors and actuates the intake to the outward position
 */
public class IntakeFlap extends Command {
	private double intakeFlapPeriod = CONSTANTS.INTAKE_FLAP_PERIOD;
	private Timer timer;
	private boolean intakeIsOut;
	
	public IntakeFlap(){
		super("IntakeFlap");
		requires(Robot.intake);
		timer = new Timer();
		intakeIsOut = false;
	}
	protected void initialize(){
		Robot.intake.disengageRatchetWing();
		Robot.intake.motorOnClimber();
		timer.reset();
		timer.start();
	}
	
	protected void execute(){
		Robot.intake.disengageRatchetWing();//TODO: get rid of this here to fix double climb bug
		
		if(Robot.intake.isRatchetWingDisengaged()){
			// flap the intake every so often
			if(timer.get() > intakeFlapPeriod / 2.0) {
				intakeIsOut = !intakeIsOut;
				timer.reset();
			}
			Robot.intake.setIntakePosition(intakeIsOut);
			Robot.intake.motorOnIntake();
		}
	}
	protected boolean isFinished(){
		return false;
	}
	protected void end(){
		Robot.intake.motorOffIntake();
		Robot.intake.setIntakePosition(false);
	}
	protected void interrupted(){
		end();
	}
}