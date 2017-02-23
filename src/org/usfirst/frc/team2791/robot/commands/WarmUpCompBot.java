package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class WarmUpCompBot extends Command{
	public WarmUpCompBot() {
		super("WarmUpCompBot");
		requires(Robot.shooter);
		requires(Robot.hopper);
		requires(Robot.intake);
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.intake.disengageRatchetWing();
	}

	@Override
	protected void execute() {
		Robot.drivetrain.setLeftRightMotorOutputs(0.40, 0.40);
		Robot.hopper.setHopperSpeed(-0.40);
		Robot.intake.setIntakeSpeed(-0.40);
		Robot.shooter.setShooterSpeedVBus(-0.30);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.stopMotors();
		Robot.hopper.stopHopper();
		Robot.drivetrain.disable();
		Robot.intake.motorOffIntake();
	}

	@Override
	protected void interrupted() {
		Robot.shooter.stopMotors();
		Robot.hopper.stopHopper();
		Robot.drivetrain.disable();
		Robot.intake.motorOffIntake();
	}
}

