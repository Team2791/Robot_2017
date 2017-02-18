
/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * Make sure mini piston on intake is in proper position before running intake
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
package org.usfirst.frc.team2791.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2791.robot.Robot.GamePeriod;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
//import org.usfirst.frc.team2791.robot.commands.ExampleCommand;
//import org.usfirst.frc.team2791.robot.subsystems.ExampleSubsystem;
//import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
//import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static OI oi;
//	public Thread driveTrainThread;
	public static GamePeriod gamePeriod;
	public static PowerDistributionPanel pdp; //CAN ID has to be 0 for current sensing
	public static ShakerIntake intake;
	public static ShakerShooter shooter;
	public static ShakerGear gearMechanism;
	public static Compressor compressor;
	public static ShakerDrivetrain drivetrain;
	public static ShakerHopper hopper;
	
	Command autonomousCommand = null;
	//SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("Starting to init my systems.");
		gamePeriod = GamePeriod.DISABLED;
		pdp = new PowerDistributionPanel(0); //CAN id has to be 0
		compressor = new Compressor(RobotMap.PCM_MODULE);
		compressor.setClosedLoopControl(true);
		drivetrain = new ShakerDrivetrain();
		intake = new ShakerIntake();
		gearMechanism = new ShakerGear();
		hopper = new ShakerHopper();
//		Thread.sleep(500);
		shooter = new ShakerShooter();
//		Thread.sleep(500);
		oi = new OI();//OI has to be initialized after all subsystems to prevent startCompetition() error
		//driveTrainThread = new Thread(drivetrain);
        //driveTrainThread.start();
		//chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", chooser);
//		SmartDashboard.putData(drivetrain);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
		gamePeriod = GamePeriod.DISABLED;
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
//		intake.wingSolenoid.set(false);//opens up robot as soon as robot starts
//		if (autonomousCommand != null)
//			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		if(Robot.hopper.moreBalls())
			Robot.hopper.runHopper();
		else
			Robot.hopper.stopHopper();
	}

	@Override
	public void teleopInit() {
//		intake.wingDeployment();//comment out when auton is runnings
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		//if (autonomousCommand != null)
			//autonomousCommand.cancel();
			gamePeriod = GamePeriod.TELEOP;
//			intake.wingSolenoid.set(false);
//			gearMechanism.changeGearSolenoidState(false);//makes it stay up when it turns on; just initiating it as up in the subsystem isn't working
//			intake.moveIntakeOut(false);
		}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		System.out.println("Compressor current:"+compressor.getCompressorCurrent());
//		SmartDashboard.putNumber("Compressor current", compressor.getCompressorCurrent());
		System.out.println("Hopper current draw: "+hopper.getCurrentUsage());
		System.out.println("Intake current draw: "+intake.getCurrentUsage());
		System.out.println("Shooter current draw: "+shooter.getCurrentUsage());
		Scheduler.getInstance().run();
		
		/* if(Robot.hopper.moreBalls())
			Robot.hopper.runHopper();
		else
			Robot.hopper.stopHopper();
		*/
		oi.checkForAction();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	public enum GamePeriod {
        AUTONOMOUS, TELEOP, DISABLED
    }
}
