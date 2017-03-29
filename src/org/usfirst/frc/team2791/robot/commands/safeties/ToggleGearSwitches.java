package org.usfirst.frc.team2791.robot.commands.safeties;


import org.usfirst.frc.team2791.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *THIS COMMAND IS DESIGNED 	TO BE USED ON A BUTTON WITH .TOGGLEWHENPRESSED();
 * 
 * Disables/Enables Gear Limit Switches </br>
 * In case the switches get stuck or become unresponsive, this will disable the limit switches.
 * After the limit switches are disabled, the gear mechanism will work the same as always, except that
 * it will not automatically come up when it gets a gear. </br>
 * To re-enable, just hit the button again.
 */
public class ToggleGearSwitches extends Command{
	public ToggleGearSwitches (){
		super("ToggleGearSwitches");
		requires(Robot.gearMechanism);
	}
	
	protected void initialize(){
		Robot.gearMechanism.toggleSwitchEnables();

	}
	
	protected void execute(){
	}
	
	protected boolean isFinished(){
		return false;//don't return true because we want command to end when driver releases button
	}
	
	protected void end(){
	}
	
	protected void interrupted(){
		end();
	}
}
