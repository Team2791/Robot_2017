package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class GearMechUp extends Command{
	public GearMechUp (){
		super("ResetGear");
		requires(Robot.gearMechanism);
	}
	protected void initialize(){
		Robot.gearMechanism.runGearIntake();
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	
	protected void execute(){
	}
	
	protected boolean isFinished(){
		return true;
	}
	
	protected void end(){
		Robot.gearMechanism.stopGearIntake();
	}
	
	protected void interrupted(){
		end();
	}
}
