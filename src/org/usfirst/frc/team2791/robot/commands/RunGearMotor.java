package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Runs the Gear Intake's motors as long as the gear mechanism is down and the switches aren't activated
 *</br> This is the default command for the ShakerGear Subsystem
 */
public class RunGearMotor extends Command {

	public RunGearMotor() {
		super("RunGearMotor");
		requires(Robot.gearMechanism);

	}

	protected void initialize() {	}

	protected void execute() {

		if(Robot.gearMechanism.isDown()){
			Robot.gearMechanism.runGearIntake();

			if(Robot.gearMechanism.getLimitSwitchState()){
				//System.out.println("The Limit Switch is bringing the piston up");
				Robot.gearMechanism.changeGearSolenoidState(false);
			}}else{
				Robot.gearMechanism.stopGearIntake();
				
			}

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.gearMechanism.stopGearIntake();    
	}

	protected void interrupted() {
		end();
	}
}
