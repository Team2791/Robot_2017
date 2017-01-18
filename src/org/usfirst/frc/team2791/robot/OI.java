package org.usfirst.frc.team2791.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import java.sql.Driver;

import org.usfirst.frc.team2791.robot.ShakerJoystick.Operator;
import org.usfirst.frc.team2791.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Driver driver = new Driver();
	Operator operator = new Operator();
	
	if(driver.getXVal()){}
	if(driver.getYVal()){}
	
	if(driver.getDpadUp()){}
	if(driver.getDpadUpRight()){}
	if(driver.getDpadRight()){}
	if(driver.getDpadDownRight()){}
	if(driver.getDpadDown()){}
	if(driver.getDpadDownLeft()){}
	if(driver.getDpadLeft()){}
	if(driver.getDpadUpLeft()){}
	
	if(driver.getButtonA()){}
	if(driver.getButtonB()){}
	if(driver.getButtonX()){}
	if(driver.getButtonY()){}
	
	if(driver.getButtonLB()){}
	if(driver.getButtonRB()){//creep forward button (right button back)
		new DriveWithJoystick();
	}
	
	if(driver.getButtonLS()){}//stick buttons - pushing joysticks in
	if(driver.getButtonRS()){}
	
	if(driver.getButtonSel()){}
	if(driver.getButtonSt()){}
	
	if(driver.getAxisLeftX()){}	
	if(driver.getAxisLeftY()){}	
	
	if(driver.getAxisLT()){//left trigger and right triggers
		new DriveWithJoystick();
	}
	if(driver.getAxisLeftRT()){
		new DriveWithJoystick();
	}
	
	if(driver.getAxisRightX()){//right joystick
		new DriveWithJoystick();
	}
	if(driver.getAxisRightY()){
		new DriveWithJoystick();
	}	
	
	
	
	
	if(operator.getXVal()){}
	if(operator.getYVal()){}
	
	if(operator.getDpadUp()){}
	if(operator.getDpadUpRight()){}
	if(operator.getDpadRight()){}
	if(operator.getDpadDownRight()){}
	if(operator.getDpadDown()){}
	if(operator.getDpadDownLeft()){}
	if(operator.getDpadLeft()){}
	if(operator.getDpadUpLeft()){}
	
	if(operator.getButtonA()){}
	if(operator.getButtonB()){}
	if(operator.getButtonX()){}
	if(operator.getButtonY()){}
	
	if(operator.getButtonLB()){}
	if(operator.getButtonRB()){}
	
	if(operator.getButtonLS()){}
	if(operator.getButtonRS()){}
	
	if(operator.getButtonSel()){}
	if(operator.getButtonSt()){}
	
	if(operator.getAxisLeftX()){}	
	if(operator.getAxisLeftY()){}	
	
	if(operator.getAxisLT()){}
	if(operator.getAxisLeftRT()){}
	
	if(operator.getAxisRightX()){}
	if(operator.getAxisRightY()){}

	/* Note: We're doing it our own way since we wrote an Overridden Joystick class
	 * 
	 * 
	 * 
	 */
	//Button button = new JoystickButton(driver, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	/*TRIGGERING COMMANDS WITH BUTTONS
	Once you have a button, it's trivial to bind it to a button in one of
    three ways:

	Start the command when the button is pressed and let it run the command
	until it is finished as determined by it's isFinished method.
	button.whenPressed(new ExampleCommand());

	Run the command while the button is being held down and interrupt it once
	the button is released.
	button.whileHeld(new ExampleCommand());

	Start the command when the button is released and let it run the command
 	until it is finished as determined by it's isFinished method.
 	button.whenReleased(new ExampleCommand()); */
}
