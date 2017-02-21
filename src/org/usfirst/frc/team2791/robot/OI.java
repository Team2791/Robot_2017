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
import org.usfirst.frc.team2791.robot.commands.TurnShooterOff;
import org.usfirst.frc.team2791.robot.commands.TurnIntakeOff;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static ShakerDriver driver;
	public static ShakerOperator operator;
	public OI(){
		System.out.println("OIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOI");
		driver = new ShakerDriver();///increase driver control expiration to 0.5
		operator = new ShakerOperator();
		
		Button driverA = new JoystickButton(driver, 1);
		Button driverB = new JoystickButton(driver, 2);
		Button driverX = new JoystickButton(driver, 3);
		Button driverY = new JoystickButton(driver, 4);
		
		Button operatorA = new JoystickButton(operator, 1);
		Button operatorB = new JoystickButton(operator, 2);
		Button operatorX = new JoystickButton(operator, 3);
		Button operatorY = new JoystickButton(operator, 4);
		
		Button driverLB = new JoystickButton(driver,5);
		Button driverRB = new JoystickButton(driver,6);
		
		Button operatorLB = new JoystickButton(operator,5);
		Button operatorRB = new JoystickButton(operator,6);
		
		Button driverLS = new JoystickButton(driver,9);//stick buttons - pushing joysticks in
		Button driverRS = new JoystickButton(driver,10);
		
		Button operatorLS = new JoystickButton(operator,9);
		Button operatorRS = new JoystickButton(operator,10);
		
		Button operatorStart = new JoystickButton(operator, 8);
		Button driverStart = new JoystickButton(driver, 8);

		
		operatorX.whileHeld(new RunWallShot());
		operatorY.whileHeld(new RunClimber());
		operatorA.whileHeld(new RunIntake());
		operatorB.whileHeld(new RunIntakeBelt());
		
		driverX.whenPressed(new RemoveGear());
		driverX.whenReleased(new ResetGear());
		driverY.whileHeld(new RunClimber());
		
		driverA.whenReleased(new RunIntake());//for driver tryouts
		driverB.whenPressed(new TurnIntakeOff());//for driver tryouts
		
		driverLB.whileHeld(new DriveWithJoystick());//TODO: change to triggers once they are properly set up
		driverRB.whileHeld(new DriveWithJoystick());//TODO: change to triggers once they are properly set up
		
		operatorLB.whenPressed(new StopClimberAndUnattach());//TODO: change to start buttons or something less needed
		operatorRB.whenPressed(new TurnShooterOff());
		
}
	public void checkForAction(){
		
		
		if(operator.getDpadUp()){
			new ResetGear();
		}
		
		if(operator.getDpadDown()){
			new RemoveGear();
		}
		
		if(operator.getDpadLeft()){
			new RunHopper();
		}
		
		if(operator.getDpadRight()){
			new StopHopper();
		}
		
		if(operator.getAxisLT()>0.0){
			new ShootWithJoystick();
		}
		
		if(operator.getDpadDownLeft()){
			new TurnShooterOff();
		}

		if(Math.abs(driver.getAxisLeftX())>0.0){System.out.println("getAxisLeftX");}//turns drivetrain
		if(Math.abs(driver.getAxisRT())>0.0){System.out.println("Driving with right trigger");}//right trigger
		if(operator.getDpadUpLeft()){/*new RunShooter();*/}
		if(Math.abs(driver.getAxisLT())>0.0){}//left trigger
		if(Math.abs(driver.getXVal())>0.0){}
		if(Math.abs(driver.getYVal())>0.0){}
		if(driver.getDpadUp()){}
		if(driver.getDpadUpRight()){}
		if(driver.getDpadRight()){}
		if(driver.getDpadDownRight()){}
		if(driver.getDpadDown()){}
		if(driver.getDpadDownLeft()){}
		if(driver.getDpadLeft()){}
		if(driver.getDpadUpLeft()){}
		if(driver.getButtonSel()){}
		if(driver.getButtonSt()){}
		if(Math.abs(driver.getAxisLeftY())>0.0){}	
		if(Math.abs(driver.getAxisRightX())>0.0){}//right joystick
		if(Math.abs(driver.getAxisRightY())>0.0){}	
		
		if(Math.abs(operator.getXVal())>0.0){}
		if(Math.abs(operator.getYVal())>0.0){}
		if(operator.getDpadUpRight()){}
		if(operator.getDpadDownRight()){}
		if(operator.getDpadDownLeft()){}
		if(operator.getDpadUpLeft()){}
		if(operator.getButtonSel()){}
		if(operator.getButtonSt()){}
		if(Math.abs(operator.getAxisLeftX())>0.0){}	
		if(Math.abs(operator.getAxisLeftY())>0.0){}	
		if(operator.getAxisRT()>0.0){}
		if(Math.abs(operator.getAxisRightX())>0.0){}
		if(Math.abs(operator.getAxisRightY())>0.0){}
	
		
//		if(driver.getButtonLB()){
//			new DriveWithJoystick();
//		}
//		if(driver.getButtonRB()){//creep forward button (right button back)
//			new DriveWithJoystick();
//		}
//		if(driver.getButtonA()){
//		new RunWallShot();
//	}
//	if(driver.getButtonB()){
//		System.out.println("BBBBB");
//		new StopShot();
//	}
//	if(driver.getButtonX()){
//		System.out.println("XXXXX");
//	}
//	if(driver.getButtonY()){
////		System.out.println("Driver is running the climber");
//		// new RunClimber();
//	}
//		if(driver.getButtonLS()){}
//	
		
		
//		if(operator.getButtonA()){
//			System.out.println("Operator is running the fuel intake");
//			new RunIntake();
//		}
//		if(operator.getButtonB()){
//			System.out.println("Operator is turning off fuel intake");
//			new TurnIntakeOff();
//		}
//		if(operator.getButtonX()){
//			new RunWallShot();
//		}
//		if(operator.getButtonY()){
//			new StopShot();
//		}
//		if(operator.getButtonLB()){}
//		if(operator.getButtonRB()){}
//		if(operator.getButtonRS()){}
//		if(operator.getButtonLS()){}
//		if(driver.getButtonRS()){
//			new StopShot();
//		}
		
	}
}
	
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

