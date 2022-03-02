// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DefaultDrive;
import frc.robot.subsystems.Drivebase;

public class RobotContainer {

  public static boolean debugMode = false;

  // Declare Subsystems
  private final Drivebase m_drivebase = new Drivebase();

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
