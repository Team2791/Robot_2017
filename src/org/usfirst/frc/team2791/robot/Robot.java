package org.usfirst.frc.team2791.robot;

import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.Color;
import org.usfirst.frc.team2791.robot.commands.autos.traj.TrajTesting;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;
import org.usfirst.frc.team2791.robot.util.ShakerVisionServer;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath;
import org.usfirst.frc.team2791.robot.commands.autos.traj.FollowPath.*;


/**
 * <b><i>Robot.java for Stoker, the 2017 robot from FRC2791 Shaker Robotics</i></b>
 * 
 * </p>
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *</p> 
 * <i>Feel free to email the authors for info on this package or any other aspect of the source code.</i>
 * </p>
 * @author Unnas Hussain: 
 * <a href="uwh.547@gmail.com"> uwh.547@gmail.com </a>
 * @author Gaurab Banerjee: 
 * <a href="gbanerjee97@gmail.com"> gbanerjee97@gmail.com </a>
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

	public ShakerVisionServer vision;
	
	private double lastAutonLoopTime;

	/**
	 * setting autonomousCommand to a Command will cause that Command to run in autonomous init
	 */
	public Command autonomousCommand;

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

		drivetrain.setAutoPID();
		debug();

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

		//allows us to reset the gyro and both encoders while disabled
		if(oi.driver.getButtonSt()){
			drivetrain.reset();
		}

		debug(); //allows us to debug (e.g. encoders and gyro) while disabled

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

		drivetrain.reset();

		intake.disengageRatchetWing();
		gearMechanism.setGearIntakeDown(false);
		
		
//		autonomousCommand = new FollowPath("TestingOneTwo", "RED", "BACKWARDS");

		//autonomousCommand = new CenterGearAuton(red);
		//autonomousCommand = new BoilerGearAuton(red);
//		autonomousCommand = new LoadingStationGearAuton(red);

		//autonomousCommand = new DriveStraightEncoderGyro(SmartDashboard.getNumber("TUNE PID Distance", 0.0), 0.7);
		//autonomousCommand = new StationaryGyroTurn(SmartDashboard.getNumber("TUNE PID Stat Angle", 0.0), 1);
		
		autonomousCommand = new TrajTesting("RED");
		
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
		gearMechanism.setGearIntakeDown(false);
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
		Scheduler.getInstance().run();
		debug();
		LiveWindow.run();
	}

	public void debug() {

		SmartDashboard.putNumber("Compressor current", compressor.getCompressorCurrent());
		SmartDashboard.putNumber("Drivetrain total current", drivetrain.getCurrentUsage());
		SmartDashboard.putNumber("Climber/Intake current",intake.getCurrentUsage());
		SmartDashboard.putNumber("Hopper current",hopper.getCurrentUsage());
		SmartDashboard.putNumber("Shooter total current",shooter.getCurrentUsage());

		drivetrain.debug();
		shooter.debug();
		gearMechanism.debug();
		hopper.debug();

	}

	public enum GamePeriod {
		AUTONOMOUS, TELEOP, DISABLED
	}
	
	/**
	 * Allows the trajectory auto to be automatically set a certain side (red or blue),
	 * and for the path to be reversed 
	 */
	public enum AutoMode {
		RED(1.0), BLUE(1.0), RED_REVERSED(-1.0), BLUE_REVERSED( -1.0);
		
		
		private double reversingConstant;
		
		AutoMode(double constant){
			this.reversingConstant = constant;
			
		}
		
		/**
		 * @return 1.0 = forward / -1.0 = reverse
		 */
		public double getConstant(){
			return this.reversingConstant;
			
		}
		
	}
	
}
