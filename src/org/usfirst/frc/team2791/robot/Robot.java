package org.usfirst.frc.team2791.robot;

import org.usfirst.frc.team2791.robot.commands.RunLongShot;
import org.usfirst.frc.team2791.robot.commands.TurnGyroBangBang;
import org.usfirst.frc.team2791.robot.commands.autos.pid.*;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.subsystems.ShakerGear;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerIntake;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;
import org.usfirst.frc.team2791.robot.util.VisionNetworkTable;

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
 * <b><i>Curie Quarterfinalists</i></b>
 * </p>
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *</p> 
 * <i>Feel free to email the authors for info on this package or any other aspect of the source code.</i>
 * </p>
 * @author Max Llewellyn
 * @author Unnas Hussain: 
 * <a href="uwh.547@gmail.com"> uwh.547@gmail.com </a>
 * @author Gaurab Banerjee: 
 * <a href="gaurab.banerjee97@gmail.com"> gaurab.banerjee97@gmail.com </a>
*
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

	public static VisionNetworkTable visionTable; 

	private double lastAutonLoopTime;
	
	private int autoMode = 0;
	private String teamColor = "RED";
	private int numOfAutos = 8;
		
	
	private double smartDashBSFix = 0.00001;




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
//		compressor.start();

		//I Commented these out because the the prints are SOOO Annoying ya know?
		try{
			UsbCamera gear_cam = CameraServer.getInstance().startAutomaticCapture("Gear Camera",0);
			gear_cam.setPixelFormat(PixelFormat.kMJPEG);
			gear_cam.setFPS(10); //wont allow me to set above 10
			if(!gear_cam.setResolution(240, 180)){
				gear_cam.setResolution(240, 180); //try 240 x 180 next
				System.out.println("******Desired resolution failed for GEAR Camer******");

			}
			//			gear_cam.getProperty(name)
		}catch(Exception e){
			System.out.println("*****FRONT Camera Failed*****");
			e.printStackTrace();
		}
		//424 * 240 @ 10 
		try{
			UsbCamera front_cam = CameraServer.getInstance().startAutomaticCapture("Front Camera", 1);
			front_cam.setPixelFormat(PixelFormat.kMJPEG);
			front_cam.setFPS(15); //was 15

			if(!front_cam.setResolution(160, 90)){ //halfed, try other resultions mauybe
				front_cam.setResolution(320, 180);//defualt value if the other resolution does not work
				System.out.println("******Desired resolution failed for FRONT Camer******");
			}
		}catch(Exception e){
			System.out.println("*****BACK Camera Failed*****");
			e.printStackTrace();
		}

		//		try{
		//			AxisCamera axis = CameraServer.getInstance().startAutomaticCapture("10.27.91.");
		//			axis.
		//		}catch(Exception e){
		//			System.out.println("*****FRONT Camera Failed*****");
		//			e.printStackTrace();
		//		}

		drivetrain = new ShakerDrivetrain();
		intake = new ShakerIntake();
		gearMechanism = new ShakerGear();
		hopper = new ShakerHopper();
		shooter = new ShakerShooter();

		oi = new OI();//OI has to be initialized after all subsystems to prevent startCompetition() error

		drivetrain.setAutoPID();
		//		shooter.pControlThread.start();

		visionTable = new VisionNetworkTable();


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
	
	boolean flag = false;
	@Override
	public void disabledPeriodic() {

		//allows us to reset the gyro and both encoders while disabled
		if(oi.driver.getButtonSt()){
			drivetrain.reset();
		}

		if(oi.operator.getButtonLB() && !flag){
			autoMode = (autoMode -1) % numOfAutos;
			flag = true;
		}
		if(oi.operator.getButtonRB()&& !flag){
			autoMode = (autoMode +1) % numOfAutos;
			flag = true;
		}
		
		if(!oi.operator.getButtonLB() && !oi.operator.getButtonRB()) {
			flag = false;
		}
			

		if(oi.operator.getButtonB())
			teamColor = "RED";
		if(oi.operator.getButtonX())
			teamColor = "BLUE";

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

		drivetrain.reset();

		intake.disengageRatchetWing();
		gearMechanism.setGearIntakeDown(false);

		autonomousCommand = selectAuto();

		System.out.println("***Selected Autonomous: " + teamColor + " " + autonomousCommand.getName() + "***");

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
		shooter.stopMotors();
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
		new RunLongShot();
	}

	public void debug() {
		
		SmartDashboard.putString("Selected Autonomous", selectAuto().getName());
		SmartDashboard.putNumber("Selected Autonomous Key", autoMode);
		SmartDashboard.putString("Selected Team Color", teamColor);

		SmartDashboard.putNumber("Compressor current", compressor.getCompressorCurrent());
		SmartDashboard.putNumber("Drivetrain total current", drivetrain.getCurrentUsage());
		SmartDashboard.putNumber("Climber/Intake current",intake.getCurrentUsage());
		SmartDashboard.putNumber("Hopper current",hopper.getCurrentUsage());
		SmartDashboard.putNumber("Shooter total current",shooter.getCurrentUsage());

		SmartDashboard.putNumber("Realtime vision error", visionTable.getRealtimeBoilerAngleError());


		SmartDashboard.putNumber("Camera vision error", visionTable.targetError);
		SmartDashboard.putNumber("Camera vision gyro offset", visionTable.gyroOffset);
		SmartDashboard.putBoolean("Robot still", visionTable.robotStill.getOutputValue());
		smartDashBSFix *= -1;

		SmartDashboard.putNumber("Camera distance from target (reflective tape) in Inches", visionTable.getRealtimeDistanceToBoiler()); 

		//		System.out.println("Vision error = "+ visionTable.getRealtimeBoilerAngleError());

		drivetrain.debug();
		shooter.debug();
		gearMechanism.debug();
		hopper.debug();

	}

	public enum GamePeriod {
		AUTONOMOUS, TELEOP, DISABLED
	}

	public Command selectAuto(){
		
		switch(autoMode){
			case 0: return new CenterGearAuton(teamColor);
			case 1: return new BoilerGearAuton(teamColor);
			case 2: return new LoadingStationGearAuton(teamColor);

			case 3: return new HopperAuton(teamColor);
			case 4: 
				if(teamColor.equals("RED"))
					visionTable.setVisionOffset(-60.0);
				else
					visionTable.setVisionOffset(60.0);
				return new CenterGearAutonShooting(teamColor);
			case  5:  return new DriveStraightEncoderGyro(SmartDashboard.getNumber("TUNE PID Distance", 0.0), 0.7, 6);
			case  6:  return new StationaryGyroTurn(SmartDashboard.getNumber("TUNE PID Stat Angle", 0.0), 1, 1.5);
			case  7:  return new StationaryVisionTurn(.5, 1.0);
			case  8:  return new TurnGyroBangBang(0.3, 45);
			default: return new DriveStraightEncoderGyro(1.0, 0.7, 6);
		}
	}
	
	


}


