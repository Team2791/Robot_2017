package org.usfirst.frc.team2791.robot.commands;

import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class GearIntakeUp extends Command{
	
	Timer timer = new Timer();
	private double motorRunTime = .5;
	
	public GearIntakeUp (){
		super("GearMechUp");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		timer.start();
		
		System.out.println("Gear Mech doing up");
		Robot.gearMechanism.changeGearSolenoidState(false);
	}
	
	protected void execute(){
		System.out.println("GearIntake running, time:"+timer.get());
		Robot.gearMechanism.runGearIntake();
	}
	
	protected boolean isFinished(){
		return timer.hasPeriodPassed(motorRunTime);
	}
	
	protected void end(){
		System.out.println("GearIntake stopped while up");
		Robot.gearMechanism.stopGearIntake();
	}
	
	protected void interrupted(){
		end();
	}
}
