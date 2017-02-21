package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class RunWallShot extends Command{
	public RunWallShot() {
		super("RunWallShot");
		requires(Robot.shooter);
		requires(Robot.hopper);
	}

	@Override
	protected void initialize() {
		Robot.shooter.prepWallShot();
	}

	@Override
	protected void execute() {
		Robot.shooter.setShooterSolenoidState(false); //down position
		Robot.shooter.prepWallShot(); //bringing shooter up to speed
		
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
		Robot.shooter.disable();
	}

	@Override
	protected void interrupted() {
		new StopHopper();
		new TurnShooterOff();
	}
}
