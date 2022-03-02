// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DefaultDrive;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Hold;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

public class RobotContainer {

  public static boolean debugMode = false;

  // Declare Subsystems
  private final Drivebase m_drivebase = new Drivebase();
  private final Intake m_intake = new Intake();
  private final Hold m_hold = new Hold();
  private final Launcher m_launcher = new Launcher();

  // Declare Controllers
  private final XboxController xbox0 = new XboxController(0); // Driver
  private final XboxController xbox1 = new XboxController(1); // Mech
  private final XboxController xbox2 = new XboxController(2); // Climb

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set Default Commands
    m_drivebase.setDefaultCommand(
        new DefaultDrive(m_drivebase, xbox0::getRightTriggerAxis, xbox0::getLeftTriggerAxis, xbox0::getLeftX));

    // Open Loop Intake - Bound to Left Joystick on Mech
    m_intake.setDefaultCommand(new RunCommand(() -> m_intake.run(xbox1.getRightY()), m_intake));

    // Open Loop Hold - Bound to Left Joystick on Mech
    m_hold.setDefaultCommand(new RunCommand(() -> m_hold.run(xbox1.getRightY()), m_hold));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // DRIVER CONTROLLER ##########################
    // SWITCH FRONT
    new JoystickButton(xbox0, Button.kX.value)
        .whenPressed(new InstantCommand(m_drivebase::switchFront, m_drivebase));

    // FORCE LOW
    new JoystickButton(xbox0, Button.kLeftBumper.value)
        .whenPressed(new InstantCommand(m_drivebase::shiftLow, m_drivebase));

    // FORCE HIGH
    new JoystickButton(xbox0, Button.kRightBumper.value)
        .whenPressed(new InstantCommand(m_drivebase::shiftHigh, m_drivebase));

    // GAME MECH CONTROLLER ######################
    // Standard Intake (With Hold)
    new JoystickButton(xbox1, Button.kB.value).whenHeld(new StartEndCommand(() -> {
      m_intake.run();
      m_hold.run();
    }, () -> {
      m_intake.stop();
      m_hold.stop();
    }, m_intake, m_hold));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
