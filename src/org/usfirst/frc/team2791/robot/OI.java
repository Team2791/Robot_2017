package org.usfirst.frc.team2791.robot;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.Button;
//import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerOperator;
import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerDriver;
import org.usfirst.frc.team2791.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team2791.robot.commands.ShootWithJoystick;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.ResetGear;
import org.usfirst.frc.team2791.robot.commands.RunClimber;
import org.usfirst.frc.team2791.robot.commands.RunHopper;
import org.usfirst.frc.team2791.robot.commands.RunIntake;
import org.usfirst.frc.team2791.robot.commands.RunIntakeBelt;
import org.usfirst.frc.team2791.robot.commands.RunWallShot;
import org.usfirst.frc.team2791.robot.commands.StopClimberAndUnattach;
//import org.usfirst.frc.team2791.robot.commands.RunWallShot;
import org.usfirst.frc.team2791.robot.commands.StopHopper;
import org.usfirst.frc.team2791.robot.commands.TurnIntakeOff;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {//joystick layout pic with Gaurab
	public static ShakerDriver driver;
	public static ShakerOperator operator;
	public OI(){
		System.out.println("OIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOI");
		driver = new ShakerDriver();///increase driver control expiration to 0.5
		operator = new ShakerOperator();
		
		new JoystickButton(driver, 4).whileHeld(new RunClimber()); 

		//if(driver.getXVal()){}
	//	if(driver.getYVal()){}
		
		//Button driveForward = new JoystickButton(driver, 3);
		
		//driveForward.whileHeld(new DriveWithJoystick());
		
		

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
	public void checkForAction(){
		if(driver.getDpadUp()){}
		if(driver.getDpadUpRight()){}
		if(driver.getDpadRight()){}
		if(driver.getDpadDownRight()){}
		if(driver.getDpadDown()){}
		if(driver.getDpadDownLeft()){}
		if(driver.getDpadLeft()){}
		if(driver.getDpadUpLeft()){}
		
		if(driver.getButtonA()){}
		if(driver.getButtonB()){
			System.out.println("BBBBB");
		}
		if(driver.getButtonX()){
			System.out.println("XXXXX");
		}
		if(driver.getButtonY()){
//			System.out.println("Driver is running the climber");
			// new RunClimber();
		}
		
		if(driver.getButtonLB()){
			new DriveWithJoystick();
		}
		if(driver.getButtonRB()){//creep forward button (right button back)
			new DriveWithJoystick();
		}
		
		if(driver.getButtonLS()){}//stick buttons - pushing joysticks in
		if(driver.getButtonRS()){}
		
		if(driver.getButtonSel()){}
		if(driver.getButtonSt()){}
		
		if(Math.abs(driver.getAxisLeftX())>0.0){
			System.out.println("getAxisLeftX");
			new DriveWithJoystick();
		}	
		if(Math.abs(driver.getAxisLeftY())>0.0){}	
		
		if(Math.abs(driver.getAxisLT())>0.0){//left trigger and right triggers
			new DriveWithJoystick();
		}
		if(Math.abs(driver.getAxisRT())>0.0){
			System.out.println("RTRTRTRTRTRTRTRTRTRTRT");
			new DriveWithJoystick();
			System.out.println("Called new command from button");
		}
		
		if(Math.abs(driver.getAxisRightX())>0.0){//right joystick
		}
		if(Math.abs(driver.getAxisRightY())>0.0){//check to see if get AxisRightX does trick, otherwise switch to getAxisRightY
//			new DriveWithJoystick();
		}	
		
		
		
		
		if(Math.abs(operator.getXVal())>0.0){
		}
		if(Math.abs(operator.getYVal())>0.0){
		}
		
		if(operator.getDpadUp()){
			new ResetGear();
		}
		if(operator.getDpadUpRight()){}
		if(operator.getDpadRight()){
			new StopHopper();
		}
		if(operator.getDpadDownRight()){}
		if(operator.getDpadDown()){
			new RemoveGear();
		}
		if(operator.getDpadDownLeft()){}
		if(operator.getDpadLeft()){
			new RunHopper();
		}
		if(operator.getDpadUpLeft()){}
		
		if(operator.getButtonA()){
			System.out.println("Operator is running the fuel intake");
			new RunIntake();
		}
		if(operator.getButtonB()){
			System.out.println("Operator is turning off fuel intake");
			new TurnIntakeOff();
		}
		if(operator.getButtonX()){
			new RunWallShot();
		}
		if(operator.getButtonY()){
		}
		
		if(operator.getButtonLB()){}
		if(operator.getButtonRB()){
			System.out.println("only running intake belts");
			new RunIntakeBelt();
		}
		
		if(operator.getButtonLS()){
			new StopClimberAndUnattach();
		}
		if(operator.getButtonRS()){}
		
		if(operator.getButtonSel()){}
		if(operator.getButtonSt()){}
		
		if(Math.abs(operator.getAxisLeftX())>0.0){}	
		if(Math.abs(operator.getAxisLeftY())>0.0){}	
		
		if(operator.getAxisLT()>0.0){
			new ShootWithJoystick();
		}
		if(operator.getAxisRT()>0.0){}
		
		if(Math.abs(operator.getAxisRightX())>0.0){}
		if(Math.abs(operator.getAxisRightY())>0.0){}
	}
	}
	


