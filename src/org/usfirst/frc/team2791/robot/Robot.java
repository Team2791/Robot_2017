
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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2791.robot.Robot.GamePeriod;

import org.usfirst.frc.team2791.robot.subsystems.*;
import org.usfirst.frc.team2791.robot.commands.autos.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static OI oi;
	public static GamePeriod gamePeriod;
	public static PowerDistributionPanel pdp; //CAN ID has to be 0 for current sensing

	public static ShakerDrivetrain drivetrain;
	public static ShakerGear gearMechanism;
	
	private double lastAutonLoopTime;
	
	/**
	 * setting autonomousCommand to a Command will cause that Command to run in autonomous init
	 */
	Command autonomousCommand;
	SendableChooser chooser = new SendableChooser();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("Starting to init my systems.");
		gamePeriod = GamePeriod.DISABLED;
		pdp = new PowerDistributionPanel(); //CAN id has to be 0
		//compressor = new Compressor(RobotMap.PCM_MODULE);
		//compressor.setClosedLoopControl(true);
		
		drivetrain = new ShakerDrivetrain();
		gearMechanism= new ShakerGear();
		
		oi = new OI();//OI has to be initialized after all subsystems to prevent startCompetition() error
		
		if(SmartDashboard.getNumber("kp", -2791) == -2791){
			SmartDashboard.putNumber("kp",7.0);
		    SmartDashboard.putNumber("ki",0);
		    SmartDashboard.putNumber("kd",0.025); //.25
		    SmartDashboard.putNumber("kv",0.09);
		    SmartDashboard.putNumber("ka",0.033);
		}
		
		//From past experience, putting these here may cause a startCompetition
		chooser.addDefault("Default Auto", new FollowPath("TestingOneTwo", false));
		chooser.addObject("Gear Hopper Auto", new GearHopperAuto());
		chooser.addObject("Gear Path", new FollowPath("BLUELeftGear", true));
		chooser.addObject("Gear-Hopper Path", new FollowPath("BLUELeftGearToLeftHopper", false));

		SmartDashboard.putData("Auto Mode", chooser);
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
		debug();
		
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
		//intake.wingDeployment();//opens up robot as soon as robot starts
		drivetrain.resetEncoders();

//		autonomousCommand= new FollowPath("RightGear", true);
		if (autonomousCommand != null)
			autonomousCommand.start();
		lastAutonLoopTime = Timer.getFPGATimestamp();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		debug();
		
		double thisAutoLoopTime = Timer.getFPGATimestamp();
		double timeDiff = thisAutoLoopTime - lastAutonLoopTime;
//		System.out.println("Auton time diff = "+timeDiff+"s rate = "+(1.0/timeDiff)+"hz");
//		lastAutonLoopTime = thisAutoLoopTime;
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
		drivetrain.resetEncoders();
			gamePeriod = GamePeriod.TELEOP;
//			gearMechanism.changeGearSolenoidState(false);//makes it stay up when it turns on; just initiating it as up in the subsystem isn't working
//			intake.moveIntakeOut(false);
//			intake.wingDeployment();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
//		System.out.println("Compressor current:"+compressor.getCompressorCurrent());
//		SmartDashboard.putNumber("Compressor current", compressor.getCompressorCurrent());
		Scheduler.getInstance().run();
		debug();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void debug(){
		drivetrain.debug();
	}
	
	public enum GamePeriod {
        AUTONOMOUS, TELEOP, DISABLED
    }
}
