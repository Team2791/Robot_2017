

package org.usfirst.frc.team2791.robot;

import org.usfirst.frc.team2791.robot.commands.auton.CenterGearAuton;
import org.usfirst.frc.team2791.robot.commands.auton.LoadingStationGearAuton;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;

import org.usfirst.frc.team2791.robot.commands.auton.BoilerGearAuton;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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

	private double lastAutonLoopTime;

	/**
	 * setting autonomousCommand to a Command will cause that Command to run in autonomous init
	 */
	public Command autonomousCommand;
	//SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
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

		if(SmartDashboard.getNumber("kp", -2791) == -2791){
			SmartDashboard.putNumber("kp",7.0);
			SmartDashboard.putNumber("ki",0);
			SmartDashboard.putNumber("kd",0.25);
			SmartDashboard.putNumber("kv",0.09);
			SmartDashboard.putNumber("ka",0.033);
		}
		
		SmartDashboard.putNumber("Stat Angle P", CONSTANTS.STATIONARY_ANGLE_P);
		SmartDashboard.putNumber("Stat Angle I", CONSTANTS.STATIONARY_ANGLE_I);
		SmartDashboard.putNumber("Stat Angle D", CONSTANTS.STATIONARY_ANGLE_D);
		SmartDashboard.putNumber("Moving Angle P", CONSTANTS.DRIVE_ANGLE_P);
		SmartDashboard.putNumber("Moving Angle I", CONSTANTS.DRIVE_ANGLE_I);
		SmartDashboard.putNumber("Moving Angle D", CONSTANTS.DRIVE_ANGLE_D);
		SmartDashboard.putNumber("Distance P", CONSTANTS.DRIVE_DISTANCE_P);
		SmartDashboard.putNumber("Distance I", CONSTANTS.DRIVE_DISTANCE_I);
		SmartDashboard.putNumber("Distance D", CONSTANTS.DRIVE_DISTANCE_D);
		
		SmartDashboard.putNumber("TUNE PID Distance", 0.0);
		SmartDashboard.putNumber("TUNE PID Stat Angle", 0.0);
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
		if(oi.driver.getButtonSt()){
			drivetrain.resetEncoders();
			drivetrain.gyro.reset();
		}
		
		debug();
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
		drivetrain.resetEncoders();
		drivetrain.gyro.reset();

		intake.disengageRatchetWing();
		gearMechanism.changeGearSolenoidState(false);

		boolean red = false;

//		autonomousCommand = new CenterGearAuton(red);
//		autonomousCommand = new BoilerGearAuton(red);
		autonomousCommand = new LoadingStationGearAuton(red);

//		autonomousCommand = new DriveStraightEncoderGyro(SmartDashboard.getNumber("TUNE PID Distance", 0.0), 0.7);
//		autonomousCommand = new StationaryGyroTurn(SmartDashboard.getNumber("TUNE PID Stat Angle", 0.0), 1);
		
		if (autonomousCommand != null)
			autonomousCommand.start();

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
		

		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		drivetrain.resetEncoders();
		gamePeriod = GamePeriod.TELEOP;
		intake.disengageRatchetWing();
		//gearMechanism.changeGearSolenoidState(false);//makes it stay up when it turns on; just initiating it as up in the subsystem isn't working
		//intake.moveIntakeOut(false);
		//intake.wingDeployment();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
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
