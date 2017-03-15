

package org.usfirst.frc.team2791.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2791.robot.Robot.GamePeriod;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team2791.robot.commands.CalibrateGyro;
import org.usfirst.frc.team2791.robot.commands.ResetGear;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;

import org.usfirst.frc.team2791.trajectory.AutoPaths;
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
	public static Compressor compressor;

	public static ShakerHopper hopper;
	public static ShakerIntake intake;
	public static ShakerShooter shooter;
	public static ShakerGear gearMechanism;
	public static ShakerDrivetrain drivetrain;

	public Command autonomousCommand;
	public SendableChooser autoChooser;

	
	/**
	 * Initializes all Subsystems.
	 * Starts Compressor and Camera.
	 * Sends information to SmartDashboard for Auto
	 */
	@Override
	public void robotInit() {
		System.out.println("Starting to init my systems.");
		gamePeriod = GamePeriod.DISABLED;

		pdp = new PowerDistributionPanel(RobotMap.PDP); //CAN id has to be 0

		compressor = new Compressor(RobotMap.PCM_MODULE);
		compressor.setClosedLoopControl(true);
		compressor.start();
		CameraServer.getInstance().startAutomaticCapture();
		drivetrain = new ShakerDrivetrain();
		intake = new ShakerIntake();
		gearMechanism = new ShakerGear();
		hopper = new ShakerHopper();
		shooter = new ShakerShooter();

		oi = new OI();//OI has to be initialized after all subsystems to prevent startCompetition() error

		
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Center Gear", new BLUEDropCenterGear());
		autoChooser.addObject("LeftGear-LeftHopper", new BLUELeftGearLeftHopper());
		autoChooser.addObject("LeftGear-LoadingStation", new BLUELeftGearLoadingStation());
		autoChooser.addObject("Shoot-RightGear", new BLUEShootRightGear());
		autoChooser.addObject("RightGear-Hopper-Shoot", new BLUERightGearRightHopperShoot());
		SmartDashboard.putData("Autonomous Chooser", autoChooser);
		
		if(SmartDashboard.getNumber("kp", -2791) == -2791){
			SmartDashboard.putNumber("kp",7.0);
			SmartDashboard.putNumber("ki",0);
			SmartDashboard.putNumber("kd",0.25);
			SmartDashboard.putNumber("kv",0.09);
			SmartDashboard.putNumber("ka",0.033);
		}
	}

	/**
	 *Reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		gamePeriod = GamePeriod.DISABLED;
	}

	@Override
	public void disabledPeriodic() {
		if(oi.driver.getButtonSt()){
			drivetrain.resetEncoders();
			drivetrain.gyro.reset();
		}
		
		debug();
		Scheduler.getInstance().run();
	}

	/**
	 * Gyro and Encoders reset at StartUp.
	 * Ratchet and Wings start disengaged.
	 * Gear Mechanism Starts Up and Ends Auto Up.
	 */
	@Override
	public void autonomousInit() {
		drivetrain.resetEncoders();
		drivetrain.gyro.reset();

		intake.disengageRatchetWing();
		gearMechanism.changeGearSolenoidState(false);

//		autonomousCommand = new BLUELeftGearLoadingStation();
		autonomousCommand = (Command) autoChooser.getSelected();
		
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		gearMechanism.changeGearSolenoidState(false);

	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		debug();
		
		if(oi.driver.getButtonSt()){
			drivetrain.resetEncoders();
			drivetrain.calibrateGyro();
		}
	}

	@Override
	public void teleopInit() {
		

		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		drivetrain.resetEncoders();
		gamePeriod = GamePeriod.TELEOP;
		intake.disengageRatchetWing();
		//gearMechanism.changeGearSolenoidState(false);//makes it stay up when it turns on; just initiating it as up in the subsystem isn't working
		//intake.moveIntakeOut(false);
		//intake.wingDeployment();
	}


	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		debug();
	}

	

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	public enum GamePeriod {
		AUTONOMOUS, TELEOP, DISABLED
	}

	public void debug() {

		//compressor debugging
		//		System.out.println("Compressor current:"+compressor.getCompressorCurrent());
		//		System.out.println("Compressor enabled? " + compressor.enabled());
		//		System.out.println("Compressor closed loop? " + compressor.getClosedLoopControl());
		//		System.out.println("Compressor switch vaule " + compressor.getPressureSwitchValue());

		SmartDashboard.putNumber("Compressor current", compressor.getCompressorCurrent());
		SmartDashboard.putNumber("Drivetrain total current", drivetrain.getCurrentUsage());
		SmartDashboard.putNumber("Climber/Intake current",intake.getCurrentUsage());
		SmartDashboard.putNumber("Hopper current",hopper.getCurrentUsage());
		SmartDashboard.putNumber("Shooter total current",shooter.getCurrentUsage());

		shooter.debug();
		drivetrain.debug();
		
		//for current based debugging without smart dashboard
		//		System.out.println("Drivetrain total Current: "+drivetrain.getCurrentUsage());
		//		System.out.println("Hopper current draw: "+hopper.getCurrentUsage());
		//		System.out.println("Intake current draw: "+intake.getCurrentUsage());
		////		SmartDashboard.putNumber("Climber current usage: ",intake.getCurrentUsage());
		//		System.out.println("Shooter current draw: "+shooter.getCurrentUsage());
		////		System.out.println("Shooter error: "+shooter.getError());
	}
}
