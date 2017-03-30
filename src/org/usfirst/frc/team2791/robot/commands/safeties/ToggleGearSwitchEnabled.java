package org.usfirst.frc.team2791.robot.commands.safeties;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Disables/Enables Gear Limit Switches </p>
 * In case the switches get stuck or become unresponsive, this will disable the limit switches.
 * After the limit switches are disabled, the gear mechanism will work the same as always, except that
 * it will not automatically come up when it gets a gear. However, the operator will be able to bring the intake down,
 * even if we have a gear </br>
 * To re-enable, just hit the button again.
 */
public class ToggleGearSwitchEnabled extends Command{
	public ToggleGearSwitchEnabled (){
		super("ToggleGearSwitches");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.toggleSwitchEnabled();
	}
	
	protected void execute(){
	}
	
	protected boolean isFinished(){
		return true;
	}
	
	protected void end(){
	}
	
	protected void interrupted(){
		end();
	}
}
