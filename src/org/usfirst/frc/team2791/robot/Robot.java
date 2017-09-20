package org.usfirst.frc.team2791.robot;


import org.usfirst.frc.team2791.robot.commands.pid.*;
import org.usfirst.frc.team2791.robot.commands.pid.automodes.*;
import org.usfirst.frc.team2791.robot.subsystems.*;
import org.usfirst.frc.team2791.robot.util.CommandSelector;
import org.usfirst.frc.team2791.robot.util.LightController;
import org.usfirst.frc.team2791.robot.vision.VisionNetworkTable;

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

	private boolean displayAutoTimes = false;
	private double lastAutonLoopTime;

	/**
	 * true --> Using the competition Robot (Stoker)</br>
	 * false --> Using the practice Robot (Stoker Jr.)
	 */
	public static boolean isCompBot = true;
	
	public Command autonomousCommand;
	private boolean lookForAction = false;
	private CommandSelector autoSelector;	

	public static TeamColor teamColor = TeamColor.BLUE;

	public static LightController lights = new LightController();
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

		drivetrain = new ShakerDrivetrain();
		intake = new ShakerIntake();
		gearMechanism = new ShakerGear();
		hopper = new ShakerHopper();
		shooter = new ShakerShooter();


		visionTable = new VisionNetworkTable();
		autoSelector = new CommandSelector("Auto Mode");

		drivetrain.setAutoPID();

		/*
		 *  the reason that names are added separately is b/c the auto commands need to know
		 *  what color we are on (which happens after disabled), but we need to know what the
		 *  selector is currently selecting for the Driver Station to know
		 */
		autoSelector.addName("Center Gear & Shoot", 0);
		autoSelector.addName("Boiler Side Gear", 1);
		autoSelector.addName("LoadingStation Gear", 2);
		autoSelector.addName("Hopper Auton", 3);
		autoSelector.addName("Center Gear", 4);
		autoSelector.addName("PID Drive Tuning", 5);
		autoSelector.addName("PID Turn Tuning", 6);
		//***When Adding a Command, Remember to add the Command in autonomousInit***

		oi = new OI();//OI has to be initialized after all subsystems to prevent startCompetition() error
		runUSBCameras();

		debug();
	}

	//************************************************************
	//********************Disabled Runners********************
	//************************************************************

	@Override
	public void disabledInit() {

		gamePeriod = GamePeriod.DISABLED;

		debug();
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

		if(OI.operator.getButtonX()){
			teamColor = TeamColor.BLUE;
		}
		if(OI.operator.getButtonB()){
			teamColor = TeamColor.RED;
		}

		Scheduler.getInstance().run();
	}

	//*************************************************************
	//******************Autonomous Runners******************
	//*************************************************************

	@Override
	public void autonomousInit() {		

		gamePeriod = GamePeriod.AUTONOMOUS;

		debug();

		drivetrain.reset();

		intake.disengageRatchetWing();
		gearMechanism.setGearIntakeDown(false);


		autoSelector.addCommand(new CenterGearAutonShooting(), 0);
		autoSelector.addCommand(new BoilerGearAuton(), 1);
		autoSelector.addCommand(new LoadingStationGearAuton(), 2);
		autoSelector.addCommand(new HopperAuton(), 3);
		autoSelector.addCommand(new CenterGearAuton(), 4);
		autoSelector.addCommand(new DriveStraightEncoderGyro(SmartDashboard.getNumber("TUNE PID Distance", 0.0), 0.7, 6), 5);
		autoSelector.addCommand(new StationaryGyroTurn(SmartDashboard.getNumber("TUNE PID Stat Angle", 0.0), 1), 6);

		//***When Adding a Command, Remember to add the Command's Name in robotInit***

		autonomousCommand = autoSelector.getSelected();

		if(autonomousCommand.getName().equals("Center Gear & Shoot")){
			visionTable.setVisionOffset(teamColor.visionOffset);
		}

		//		autonomousCommand = new TurnGyroBangBang(0.65 , 90.0);

		System.out.println("***Starting "+teamColor+" "+autonomousCommand.getName()+" AutoMode***");

		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		double thisAutoLoopTime = Timer.getFPGATimestamp();
		double timeDiff = thisAutoLoopTime - lastAutonLoopTime;
		System.out.print(displayAutoTimes ? "Auton time diff = "+timeDiff+"s rate = "+(1.0/timeDiff)+"hz\n" : "");
		lastAutonLoopTime = thisAutoLoopTime;
	}

	//************************************************************
	//*********************Teleop Runners*********************
	//************************************************************

	@Override
	public void teleopInit() {

		debug();

		if (autonomousCommand != null)
			autonomousCommand.cancel();

		drivetrain.resetEncoders();
		gamePeriod = GamePeriod.TELEOP;
		intake.disengageRatchetWing();
		gearMechanism.setGearIntakeDown(false);
		shooter.stopMotors();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		run();
	}

	//***********************************************************
	//**********************Test Runners**********************
	//***********************************************************


	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		LiveWindow.run();

		run();
	}


	/**Runs in all GamePeriods*/
	public void run(){
		isCompBot = SmartDashboard.getBoolean("botIsCompetition", isCompBot);
		visionTable.run();
		lights.run();
		debug();
	}


	//*******************************************************************
	//************************Helper Methods************************
	//*******************************************************************

	private void runUSBCameras() {

		try{
			UsbCamera gear_cam = CameraServer.getInstance().startAutomaticCapture("Gear Camera",1);
			gear_cam.setPixelFormat(PixelFormat.kMJPEG);
			gear_cam.setFPS(10); //wont allow me to set above 10
			if(!gear_cam.setResolution(240, 180)){
				gear_cam.setResolution(240, 180); 
				System.out.println("******Desired resolution FAILED for GEAR Camera******");

			}

			//			gear_cam.getProperty(name)
		}catch(Exception e){
			System.out.println("*****Gear Camera FAILED*****");
			e.printStackTrace();
		}

		try{
			UsbCamera front_cam = CameraServer.getInstance().startAutomaticCapture("Front Camera", 0);
			front_cam.setPixelFormat(PixelFormat.kMJPEG);
			front_cam.setFPS(15); 

			if(!front_cam.setResolution(160, 90)){ //halfed
				front_cam.setResolution(320, 180);//default value if the other resolution does not work
				System.out.println("******Desired resolution FAILED for FRONT Camera******");
			}
		}catch(Exception e){
			System.out.println("*****FRONT Camera FAILED*****");
			e.printStackTrace();
		}

	}

	/**
	 * Any subsystem or utility that needs to communicate with SmartDashboard should have a debug method.
	 * That debug method should be called here.
	 */
	public void debug() {
		
		SmartDashboard.putBoolean("Competition Bot?", isCompBot);

		SmartDashboard.putString("Selected Team Color", teamColor.toString());

		SmartDashboard.putNumber("Compressor current", compressor.getCompressorCurrent());
		SmartDashboard.putNumber("Drivetrain total current", drivetrain.getCurrentUsage());
		SmartDashboard.putNumber("Climber/Intake current",intake.getCurrentUsage());
		SmartDashboard.putNumber("Hopper current",hopper.getCurrentUsage());
		SmartDashboard.putNumber("Shooter total current",shooter.getCurrentUsage());

		autoSelector.debug();

		drivetrain.debug();
		shooter.debug();
		gearMechanism.debug();
		hopper.debug();

		visionTable.debug();

	}

	public enum GamePeriod {
		AUTONOMOUS, TELEOP, DISABLED
	}

	public enum TeamColor{
		BLUE("BLUE", 60.0), RED("RED", -60.0);

		private String color;
		public double visionOffset;

		TeamColor(String color, double vOffset){
			this.color = color;
			this.visionOffset = vOffset;
		}

		@Override
		public String toString(){
			return color;
		}

	}
}

