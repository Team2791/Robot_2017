package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class RunLongShot extends Command{
	public RunLongShot() {
		super("RunLongShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}

	@Override
	protected void initialize() {
		Robot.shooter.prepWallShot();
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(true); //down position
		Robot.shooter.prepLongShot(); //bringing shooter up to speed
		
		// if we need more balls or the shooter is ready
		if(!Robot.hopper.isBallAtTop() || Robot.shooter.atSpeed()) {
			Robot.hopper.runHopper();
		} else {
			Robot.hopper.stopHopper();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.stopMotors();
		Robot.hopper.stopHopper();
	}

	@Override
	protected void interrupted() {
		Robot.shooter.stopMotors();
		Robot.hopper.stopHopper();
	}
}

