// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.time.Instant;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Hold;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Limelight;

public class SmartLaunch extends SequentialCommandGroup {
  private final Launcher m_launcher;
  private final Limelight m_launcherLimelight;
  private final Drivebase m_drivebase;
  private final Hold m_hold;
  private final Intake m_intake;
  // public static double pixHeight;
  // public int level;
  // private int distance = 20;
  // public static int RPM;
  // public double SmartDashRPM;

  /**
   * Creates a new Shoot.
   * 
   * @param RobotContainer
   */

  public SmartLaunch(Launcher launcher, Limelight launcherLimelight, Drivebase drivebase, Intake intake, Hold hold) {
    this.m_launcher = launcher;
    this.m_launcherLimelight = launcherLimelight;
    this.m_drivebase = drivebase;
    this.m_hold = hold;
    this.m_intake = intake;

    /*
     * Parallel:
     ** - Target Drive Base
     ** Sequential:
     ** Sequential:
     *** - Start Hold
     *** - Wait .3s
     *** - Stop Hold
     ** - Rev Shooter
     * Parallel:
     ** - Run Intake
     ** - Smart Feed
     */

    addCommands(
        parallel(
            new TargetDrivebase(m_drivebase, m_launcherLimelight),
            new PrintCommand("Running First Parallel Stuff"),
            sequence(
                sequence(
                    new InstantCommand(m_hold::runBackward, m_hold),
                    new WaitCommand(.3),
                    new InstantCommand(m_hold::stop, m_hold)),
                new PrintCommand("Finished Hold Deploy"),
                new FunctionalCommand(() -> m_launcher.targetPixels(m_launcherLimelight.getY()), () -> {
                }, interrupted -> {
                },
                    () -> m_launcher.atSetpoint(), m_launcher)),
            new PrintCommand("Finished Launch / Nested Sequence")),
        new PrintCommand("Finished First Parallel"),
        parallel(
            new InstantCommand(m_intake::run, m_intake),
            new FunctionalCommand(() -> {
            }, () -> {
              if (m_launcher.atSetpoint()) {
                m_hold.run();
              }
            }, (interrupted) -> {
              m_launcher.disable();
              m_hold.stop();
            }, () -> {
              return false;
            }, m_launcher, m_hold)));

    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(m_launcher, m_limelight, m_intake);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_launcher.disable();
    m_hold.stop();
    m_intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
