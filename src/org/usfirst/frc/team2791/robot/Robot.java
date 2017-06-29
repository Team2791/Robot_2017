package org.usfirst.frc.team2791.robot;

import org.usfirst.frc.team2791.robot.commands.pid.DriveStraightEncoderGyro;
import org.usfirst.frc.team2791.robot.commands.pid.StationaryGyroTurn;
import org.usfirst.frc.team2791.robot.commands.pid.automodes.*;
import org.usfirst.frc.team2791.robot.subsystems.*;
import org.usfirst.frc.team2791.robot.util.CONSTANTS;
import org.usfirst.frc.team2791.robot.util.CommandSelector;
import org.usfirst.frc.team2791.robot.util.VisionNetworkTable;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
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

	//	public ShakerVisionServer vision;

	public static VisionNetworkTable visionTable; 

	private double lastAutonLoopTime;

	private double smartDashBSFix = 0.00001;



	/**
	 * setting autonomousCommand to a Command will cause that Command to run in autonomous init
	 */
	public Command autonomousCommand;
	private boolean lookForAction = false;
	
	/**
	 * Object containing an ArrayList to add different autoModes to 
	 * and then select them based on a key that is changed with the
	 * operator controller
	 */
	private CommandSelector autoSelector;	


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
//
//				try{
//					UsbCamera gear_cam = CameraServer.getInstance().startAutomaticCapture("Gear Camera",1);
//					gear_cam.setPixelFormat(PixelFormat.kMJPEG);
//					gear_cam.setFPS(10); //wont allow me to set above 10
//					if(!gear_cam.setResolution(240, 180)){
//						gear_cam.setResolution(240, 180); //try 240 x 180 next
//						System.out.println("******Desired resolution failed for GEAR Camera******");
//
//					}
//				
//		//			gear_cam.getProperty(name)
//				}catch(Exception e){
//					System.out.println("*****Gear Camera Failed*****");
//					e.printStackTrace();
//				}
//				
//				try{
//					UsbCamera front_cam = CameraServer.getInstance().startAutomaticCapture("Front Camera", 0);
//					front_cam.setPixelFormat(PixelFormat.kMJPEG);
//					front_cam.setFPS(15); //was 15
//					
//					if(!front_cam.setResolution(160, 90)){ //halfed, try other resultions mauybe
//						front_cam.setResolution(320, 180);//defualt value if the other resolution does not work
//						System.out.println("******Desired resolution failed for FRONT Camera******");
//					}
//				}catch(Exception e){
//					System.out.println("*****FRONT Camera Failed*****");
//					e.printStackTrace();
//				}
				

		drivetrain = new ShakerDrivetrain();
		intake = new ShakerIntake();
		gearMechanism = new ShakerGear();
		hopper = new ShakerHopper();
		shooter = new ShakerShooter();


		oi = new OI();//OI has to be initialized after all subsystems to prevent startCompetition() error

		drivetrain.setAutoPID();

		visionTable = new VisionNetworkTable();

		autoSelector = new CommandSelector("Auto Mode");
		
		/*
		 *  the reason that names are added separately is b/c the auto commands need the teamColor
		 *  as a parameter (so they can't be initialized until we know what the color should be). So:
		 *  TODO: change the auto modes so they don't use parameters
		 */
		autoSelector.addName("Center Gear", 0);
		autoSelector.addName("Boiler Side Gear", 1);
		autoSelector.addName("LoadingStation Gear", 2);
		autoSelector.addName("Hopper Auton", 3);
		autoSelector.addName("Center Gear & Shoot", 4);
		autoSelector.addName("PID Drive Tuning", 5);
		autoSelector.addName("PID Turn Tuning", 6);
		
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
		if(OI.driver.getButtonSt()){
			drivetrain.reset();
		}

		//prevents buttons from incrementing too fast
		if(!(OI.operator.getButtonLB() || OI.operator.getButtonRB()))
			lookForAction = true;

		if(OI.operator.getButtonRB() && lookForAction){
			autoSelector.incrementKey(); 
			lookForAction = false;
		}
		if(OI.operator.getButtonLB() && lookForAction){
			autoSelector.decrementKey(); 
			lookForAction = false;
		}

		if(OI.operator.getButtonX())
			autoSelector.setColorToBlue();
		if(OI.operator.getButtonB())
			autoSelector.setColorToRed();

		debug(); //allows us to debug (e.g. encoders and gyro) while disabled

		Scheduler.getInstance().run();
		run();
	}

	/**
	 * This autonomous (along with the selector code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * selector code above or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {		
		drivetrain.reset();

		intake.disengageRatchetWing();
		gearMechanism.setGearIntakeDown(false);
		
		String teamColor = autoSelector.getColor();

		autoSelector.addCommand(new CenterGearAuton(teamColor), 0);
		autoSelector.addCommand(new BoilerGearAuton(teamColor), 1);
		autoSelector.addCommand(new LoadingStationGearAuton(teamColor), 2);
		autoSelector.addCommand(new HopperAuton(teamColor), 3);
		autoSelector.addCommand(new CenterGearAutonShooting(teamColor), 4);
		autoSelector.addCommand(new DriveStraightEncoderGyro(SmartDashboard.getNumber("TUNE PID Distance", 0.0), 0.7, 6), 5);
		autoSelector.addCommand(new StationaryGyroTurn(SmartDashboard.getNumber("TUNE PID Stat Angle", 0.0), 1), 6);
		
		//***When Adding a Command, Remember to add the Command's Name in robotInit***

		autonomousCommand = autoSelector.getSelected();
		
		if(autonomousCommand.getName().equals("Center Gear & Shoot")){
			if(teamColor.equals("RED"))
				visionTable.setVisionOffset(-60.0);
			else
				visionTable.setVisionOffset(60.0);
		}
		
		System.out.println("***Starting "+teamColor+" "+autonomousCommand.getName()+" AutoMode***");

		if (autonomousCommand != null)
			autonomousCommand.start();


	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		run();
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
		shooter.stopMotors();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		run();
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
		run();
	}

	public void debug() {

		autoSelector.debug();

		SmartDashboard.putNumber("Compressor current", compressor.getCompressorCurrent());
		SmartDashboard.putNumber("Drivetrain total current", drivetrain.getCurrentUsage());
		SmartDashboard.putNumber("Climber/Intake current",intake.getCurrentUsage());
		SmartDashboard.putNumber("Hopper current",hopper.getCurrentUsage());
		SmartDashboard.putNumber("Shooter total current",shooter.getCurrentUsage());

		SmartDashboard.putNumber("Realtime vision angle error", visionTable.getRealtimeBoilerAngleError());
		SmartDashboard.putNumber("Realtime vision distance", visionTable.getRealtimeDistanceToBoiler());

		SmartDashboard.putNumber("Camera vision angle error", visionTable.targetAngleError);
		SmartDashboard.putNumber("Camera vision gyro offset", visionTable.gyroOffset);
		SmartDashboard.putBoolean("Robot still", visionTable.robotNotTurning.getOutputValue());
		smartDashBSFix *= -1;

		//System.out.println("Vision error = "+ visionTable.getRealtimeBoilerAngleError());

		drivetrain.debug();
		shooter.debug();
		gearMechanism.debug();
		hopper.debug();
		visionTable.debug();

	}

	public enum GamePeriod {
		AUTONOMOUS, TELEOP, DISABLED
	}
	// THROW BACK TO OLD CODE
	public void run(){
				visionTable.run();
	}

}

