package org.usfirst.frc.team2791.robot;

import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerDriver;
import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerOperator;
import org.usfirst.frc.team2791.robot.commands.*;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * This class does not handle the joysticks driving , which is handled in GTADrive
 * @see GTADrive
 */
public class OI {
	public static ShakerDriver driver;
	public static ShakerOperator operator;
	
	
	protected Button driverA, driverB, driverX, driverY, driverLB, driverRB, driverBack, driverStart, driverLS, driverRS;
	protected Button operatorA, operatorB, operatorX, operatorY, operatorLB, operatorRB, operatorBack, operatorStart, 
		operatorLS, operatorRS; 

	protected Button driverDpadUp, driverDpadUpRight, driverDpadRight, driverDpadDownRight, driverDpadDown,
		driverDpadDownLeft, driverDpadLeft, driverDpadUpLeft;
	protected Button operatorDpadUp, operatorDpadUpRight, operatorDpadRight, operatorDpadDownRight, 
		operatorDpadDown, operatorDpadDownLeft, operatorDpadLeft, operatorDpadUpLeft;
	
	/**
	 * This is where the joysticks are initialized and buttons are mapped to certain commands (driver and operator controls)
	 */
	public OI(){
		System.out.println("OI initialized");
		driver = new ShakerDriver();
		operator = new ShakerOperator();

		initButtons();
		initDpad();
		//note: the triggers are called in GTADrive and in the joystick objects themselves so we do not have to map them here, esp. since they are for default commands
		
		/********************************** Operator Button Assignments ****************************************/
		
		operatorX.whileHeld(new RunWallShotFullHopper()); 
		operatorY.whileHeld(new RunLongShotFullHopper());
		
		operatorA.whileHeld(new RunIntake());
		
		operatorB.whileHeld(new RunClimber());
		operatorLB.whileHeld(new EngageRope());

		operatorDpadDown.whenPressed(new GearMechDown()); // noah wants to hit the button to start intaking and hit a button to finish intaking
		operatorDpadUp.whenPressed(new GearMechUp());
		
		operatorDpadLeft.whileHeld(new HopperOn());
		operatorDpadRight.whileHeld(new RunHopperBackwards());
		
		operatorRB.whenPressed(new StopHopper());
		operatorBack.whenPressed(new StopClimberAndDisengage());//safety
		operatorStart.whenPressed(new TurnShooterOff());//safety
		
		/********************************** Driver Button Assignments ****************************************/
		
		driverX.whileHeld(new ScoreGear()); //karan wants to hold the button down to score and let go to let the gear mech back up
		
		driverY.whileHeld(new RunClimber());
		driverA.toggleWhenPressed(new RunIntake());
		
		driverB.whileHeld(new GearDownOverrideSwitches());
		
		driverLB.whileHeld(new DriveWithJoystick());
		driverRB.whileHeld(new DriveWithJoystick());
		
		driverBack.whileHeld(new CalibrateGyro());
	}
	
	/**
	 * Initializes all Buttons
	 */
	private void initButtons(){
		driverA = new JoystickButton(driver,1);
		driverB = new JoystickButton(driver,2);
		driverX = new JoystickButton(driver,3);
		driverY = new JoystickButton(driver,4);
		driverLB = new JoystickButton(driver,5);
		driverRB = new JoystickButton(driver,6);
		driverBack = new JoystickButton(driver,7);
		driverStart = new JoystickButton(driver,8);
		driverLS = new JoystickButton(driver,9);
		driverRS = new JoystickButton(driver,10);
		
		operatorA = new JoystickButton(operator, 1);
		operatorB = new JoystickButton(operator, 2);
		operatorX = new JoystickButton(operator, 3);
		operatorY = new JoystickButton(operator, 4);
		operatorLB = new JoystickButton(operator,5);
		operatorRB = new JoystickButton(operator,6);
		operatorBack = new JoystickButton(operator,7);
		operatorStart = new JoystickButton(operator, 8);
		operatorLS = new JoystickButton(operator,9);
		operatorRS = new JoystickButton(operator,10);
	}
	
	/**
	 * Initializes the Dpad
	 */
	private void initDpad(){
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
}
