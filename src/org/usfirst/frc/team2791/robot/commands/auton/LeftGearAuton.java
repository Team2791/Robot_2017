package org.usfirst.frc.team2791.robot.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by team2791 on 3/15/2016.
 */
public class LeftGearAuton extends Command {
	
	private int state = 1;
	private Timer timer = new Timer();
    
	private double firstDistance;
    private double turnToAngle;
    private double secondDistance;

    public LeftGearAuton() {
    	super("LeftGearAuton");
    	requires(Robot.drivetrain);
    	requires(Robot.gearMechanism);
    	this.firstDistance = 10;
        this.turnToAngle = 0;
        this.secondDistance = 0;
    }
    
    protected void initialize() {}

    public void execute() {
        switch (state) {
            case 0:
                Robot.drivetrain.disable();
                timer.reset();
                break;
            case 1:
            	System.out.println("Starting LeftGear auton.");
                Robot.drivetrain.resetEncoders();
                timer.reset();
                timer.start();
                state++;
            case 2:
//            	System.out.println("Trying to drive.");
//                if (Robot.drivetrain.setDistance(firstDistance, 0, 0.4, true)) {
//                    System.out.println("Finished driving, hanging gear.");
//                    Robot.drivetrain.resetEncoders();
//                    
//                    timer.reset();
//                    state++;
//                }
            case 3:
            	Robot.gearMechanism.changeGearSolenoidState(true);
            	System.out.println("Hanged gear, ending auto.");
            	state = 0;
        }
    }
    
	@Override
	protected boolean isFinished() {
		return state == 0;
	}
	
	@Override
	protected void end() {
		Robot.drivetrain.setLeftRightMotorOutputs(0.0,0.0);
	}

	protected void interrupted() {
		Robot.drivetrain.setLeftRightMotorOutputs(0.0,0.0);
		System.out.println("LeftGearAuton interrupted.");
	}
}