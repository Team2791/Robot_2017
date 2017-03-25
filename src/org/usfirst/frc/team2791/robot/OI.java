package org.usfirst.frc.team2791.robot;

import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerDriver;
import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerOperator;
import org.usfirst.frc.team2791.robot.commands.CalibrateGyro;
import org.usfirst.frc.team2791.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team2791.robot.commands.GearIntakeDown;
import org.usfirst.frc.team2791.robot.commands.GearIntakeUp;
import org.usfirst.frc.team2791.robot.commands.RunClimber;
import org.usfirst.frc.team2791.robot.commands.RunHopperBackwards;
import org.usfirst.frc.team2791.robot.commands.RunIntake;
import org.usfirst.frc.team2791.robot.commands.RunIntakeBelt;
import org.usfirst.frc.team2791.robot.commands.RunLongShot;
import org.usfirst.frc.team2791.robot.commands.RunWallShot;
import org.usfirst.frc.team2791.robot.commands.StopClimberAndDisengage;
//import org.usfirst.frc.team2791.robot.commands.RunWallShot;
import org.usfirst.frc.team2791.robot.commands.StopHopper;
import org.usfirst.frc.team2791.robot.commands.TurnIntakeOff;
import org.usfirst.frc.team2791.robot.commands.TurnShooterOff;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerOperator;
import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerDriver;

import org.usfirst.frc.team2791.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static ShakerDriver driver;
	public static ShakerOperator operator;
	
	//This allows the button assignment code to be cleaner by putting all Dpad assignments into their own method
	protected Button driverDpadUp, driverDpadUpRight, driverDpadRight, driverDpadDownRight, driverDpadDown,
		driverDpadDownLeft, driverDpadLeft, driverDpadUpLeft;
	protected Button operatorDpadUp, operatorDpadUpRight, operatorDpadRight, operatorDpadDownRight, 
		operatorDpadDown, operatorDpadDownLeft, operatorDpadLeft, operatorDpadUpLeft;
	
	public OI(){
		System.out.println("OI initialized");
		driver = new ShakerDriver();///increase driver control expiration to 0.5
		operator = new ShakerOperator();

		/*
		 * Button Inits
		 */

		Button driverA = new JoystickButton(driver,1);
		Button driverB = new JoystickButton(driver,2);
		Button driverX = new JoystickButton(driver,3);
		Button driverY = new JoystickButton(driver,4);
		Button driverLB = new JoystickButton(driver,5);
		Button driverRB = new JoystickButton(driver,6);
		Button driverBack = new JoystickButton(driver,7);
		Button driverStart = new JoystickButton(driver,8);
		Button driverLS = new JoystickButton(driver,9);
		Button driverRS = new JoystickButton(driver,10);
		

		Button operatorA = new JoystickButton(operator, 1);
		Button operatorB = new JoystickButton(operator, 2);
		Button operatorX = new JoystickButton(operator, 3);
		Button operatorY = new JoystickButton(operator, 4);
		Button operatorLB = new JoystickButton(operator,5);
		Button operatorRB = new JoystickButton(operator,6);
		Button operatorBack = new JoystickButton(operator,7);
		Button operatorStart = new JoystickButton(operator, 8);
		Button operatorLS = new JoystickButton(operator,9);
		Button operatorRS = new JoystickButton(operator,10);

		initDpad();
		/*
		 * Operator Button Assignments
		 */
		operatorX.whileHeld(new RunWallShotFullHopper()); 
		operatorY.whileHeld(new RunLongShotFullHopper());
		
		operatorA.whileHeld(new RunIntake());
		
		operatorB.whileHeld(new RunClimber());
		operatorLB.whileHeld(new EngageRope());

		operatorDpadDown.toggleWhenPressed(new IntakeGear());
		
		operatorDpadLeft.whileHeld(new HopperOn());
		operatorDpadUp.whenPressed(new StopHopper());
		operatorDpadRight.whileHeld(new RunHopperBackwards());
		
		operatorBack.whenPressed(new StopClimberAndDisengage());//safety
		operatorStart.whenPressed(new TurnShooterOff());//safety
		
		/*
		 * Driver Button Assignments
		 */
		driverX.whenPressed(new GearIntakeDown());
		driverX.whenReleased(new GearIntakeUp());
		
		driverY.whileHeld(new RunClimber());
		driverA.toggleWhenPressed(new RunIntake());
		
		driverLB.whileHeld(new DriveWithJoystick());
		driverRB.whileHeld(new DriveWithJoystick());
		
		driverBack.whileHeld(new CalibrateGyro());
	}
	private void initDpad(){
		/*
		 * DPad Inits
		 */
		driverDpadUp = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadUp();
			}
		};

		driverDpadUpRight = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadUpRight();
			}
		};

		driverDpadRight = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadRight();
			}
		};

		driverDpadDownRight = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadDownRight();
			}
		};

		driverDpadDown = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadDown();
			}
		};

		driverDpadDownLeft = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadDownLeft();
			}
		};

		driverDpadLeft = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadLeft();
			}
		};

		driverDpadUpLeft = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadUpLeft();
			}
		};

		operatorDpadUp = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadUp();
			}
		};

		operatorDpadUpRight = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadUpRight();
			}
		};

		operatorDpadRight = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadRight();
			}
		};
		operatorDpadDownRight = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadDownRight();
			}
		};
		operatorDpadDown = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadDown();
			}
		};
		operatorDpadDownLeft = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadDownLeft();
			}
		};
		operatorDpadLeft = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadLeft();
			}
		};
		operatorDpadUpLeft = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadUpLeft();
			}
		};
	}
	public void checkForAction(){

		if(Math.abs(driver.getAxisLeftX())>0.0){}//left joystick
		if(Math.abs(driver.getAxisRT())>0.0){}//right trigger
		if(operator.getDpadUpLeft()){}
		if(Math.abs(driver.getAxisLT())>0.0){}//left trigger
		if(Math.abs(driver.getXVal())>0.0){}
		if(Math.abs(driver.getYVal())>0.0){}
		if(Math.abs(driver.getAxisLeftY())>0.0){}	
		if(Math.abs(driver.getAxisRightX())>0.0){}//right joystick
		if(Math.abs(driver.getAxisRightY())>0.0){}	

		if(Math.abs(operator.getXVal())>0.0){}
		if(Math.abs(operator.getYVal())>0.0){}
		if(Math.abs(operator.getAxisLeftX())>0.0){}	
		if(Math.abs(operator.getAxisLeftY())>0.0){}	
		if(operator.getAxisRT()>0.0){}
		if(Math.abs(operator.getAxisRightX())>0.0){}
		if(Math.abs(operator.getAxisRightY())>0.0){}

	}
}
