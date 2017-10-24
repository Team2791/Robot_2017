package org.usfirst.frc.team2791.robot.commands;


import org.usfirst.frc.team2791.robot.Robot;
import org.usfirst.frc.team2791.robot.subsystems.ShakerHopper;
import org.usfirst.frc.team2791.robot.subsystems.ShakerShooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Simultanesouly runs the {@link ShakerShooter} and {@link ShakerHopper}. Sets shooter speed and hood for wallShot. The hopper is set to meter its speed.
 */
public class SetShooterHoodAndHopperPusher extends Command{
	boolean hoodDown;
	public SetShooterHoodAndHopperPusher(boolean hoodDown) {
		super("SetShooterHoodAndHopperPusher");
		this.hoodDown = hoodDown;
		requires(Robot.shooter);
	}

	@Override
	protected void initialize() {
		System.out.println("in SetShooterHoodAndHopperPusher init method");
		Robot.shooter.setShooterSolenoidState(hoodDown);
	}

	@Override
	protected void execute() {
		System.out.println("in SetShooterHoodAndHopperPusher execute method");
		Robot.shooter.setShooterSolenoidState(hoodDown);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end(); // we don't think it's called automatically
	}
}

