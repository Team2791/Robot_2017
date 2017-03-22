package org.usfirst.frc.team2791.robot;

import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerDriver;
import org.usfirst.frc.team2791.robot.ShakerJoystick.ShakerOperator;
import org.usfirst.frc.team2791.robot.commands.CalibrateGyro;
import org.usfirst.frc.team2791.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team2791.robot.commands.RemoveGear;
import org.usfirst.frc.team2791.robot.commands.ResetGear;
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


		/*
		 * DPad Inits
		 */
		Button driverDpadUp = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadUp();
			}
		};

		Button driverDpadUpRight = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadUpRight();
			}
		};

		Button driverDpadRight = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadRight();
			}
		};

		Button driverDpadDownRight = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadDownRight();
			}
		};

		Button driverDpadDown = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadDown();
			}
		};

		Button driverDpadDownLeft = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadDownLeft();
			}
		};

		Button driverDpadLeft = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadLeft();
			}
		};

		Button driverDpadUpLeft = new Button(){
			@Override
			public boolean get(){
				return driver.getDpadUpLeft();
			}
		};

		Button operatorDpadUp = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadUp();
			}
		};

		Button operatorDpadUpRight = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadUpRight();
			}
		};

		Button operatorDpadRight = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadRight();
			}
		};
		Button operatorDpadDownRight = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadDownRight();
			}
		};
		Button operatorDpadDown = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadDown();
			}
		};
		Button operatorDpadDownLeft = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadDownLeft();
			}
		};
		Button operatorDpadLeft = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadLeft();
			}
		};
		Button operatorDpadUpLeft = new Button(){
			@Override
			public boolean get(){
				return operator.getDpadUpLeft();
			}
		};

		/*
		 * Operator Button Assignments
		 */
//		operatorX.whileHeld(new RunWallShot());
//		operatorY.whileHeld(new RunLongShot()); 
		operatorX.whileHeld(new RunWallShotFullHopper());  //Done at TVR Q42
		operatorY.whileHeld(new RunLongShotFullHopper());
		
		operatorRS.whileHeld(new ExtendIntake());
		
		
		operatorA.whileHeld(new RunIntake());
		operatorB.whileHeld(new RunClimber());//climbing
		operatorLB.whileHeld(new EngageRope());

		operatorBack.whenPressed(new StopClimberAndDisengage());
		operatorStart.whenPressed(new TurnShooterOff());

		operatorDpadDown.whenPressed(new RemoveGear());
		operatorDpadDown.whenReleased(new ResetGear());
		
		operatorDpadLeft.whileHeld(new HopperOn());
		operatorDpadUp.whenPressed(new StopHopper());
		operatorDpadRight.whileHeld(new RunHopperBackwards());
		
		/*
		 * Driver Button Assignments
		 */
		driverX.whenPressed(new RemoveGear());
		driverX.whenReleased(new ResetGear());
		driverY.whileHeld(new RunClimber());
		
		driverA.toggleWhenPressed(new RunIntake());
//		driverA.whenReleased(new RunIntake());
//		driverB.whenPressed(new TurnIntakeOff());

		driverLB.whileHeld(new DriveWithJoystick());
		driverRB.whileHeld(new DriveWithJoystick());
		
		driverBack.whileHeld(new CalibrateGyro());
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
