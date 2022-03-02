// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DrivebaseConstants.*;
import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.HoldConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
// import frc.robot.RobotContainer;

public class Drivebase extends SubsystemBase {

  // Front to Back, 1 to 3
  private final CANSparkMax m_motorL1 = new CANSparkMax(kfrontLeftID, MotorType.kBrushless);
  private final CANSparkMax m_motorL2 = new CANSparkMax(kmidLeftID, MotorType.kBrushless);
  private final CANSparkMax m_motorL3 = new CANSparkMax(krearLeftID, MotorType.kBrushless);

  private final CANSparkMax m_motorR1 = new CANSparkMax(kfrontRightID, MotorType.kBrushless);
  private final CANSparkMax m_motorR2 = new CANSparkMax(kmidRightID, MotorType.kBrushless);
  private final CANSparkMax m_motorR3 = new CANSparkMax(krearRightID, MotorType.kBrushless);

  private final RelativeEncoder m_encoderL = m_motorL1.getEncoder();
  private final RelativeEncoder m_encoderR = m_motorR1.getEncoder();

  private final MotorControllerGroup m_left = new MotorControllerGroup(m_motorL1, m_motorL2, m_motorL3);
  private final MotorControllerGroup m_right = new MotorControllerGroup(m_motorR1, m_motorR2, m_motorR3);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

  private final DoubleSolenoid shifter = new DoubleSolenoid(PneumaticsModuleType.REVPH, kLOW_FORWARD_CHANNEL,
      kHIGH_REVERSE_CHANNEL);

  private double m_speedScaling = kLOW_GEAR_SPEED_SCALING; // Not a great solution
  private boolean m_isSwitchFront = false;

  // final XboxController xbox0;

  /** Creates a new Drivebase. */
  public Drivebase() {

    m_motorL1.setOpenLoopRampRate(kDRIVE_RAMP_RATE);
    m_motorL2.setOpenLoopRampRate(kDRIVE_RAMP_RATE);
    m_motorL3.setOpenLoopRampRate(kDRIVE_RAMP_RATE);
    m_motorR1.setOpenLoopRampRate(kDRIVE_RAMP_RATE);
    m_motorR2.setOpenLoopRampRate(kDRIVE_RAMP_RATE);
    m_motorR3.setOpenLoopRampRate(kDRIVE_RAMP_RATE);

    m_left.setInverted(true);

    if (RobotContainer.debugMode) {
      initDebugMode();
    }

  }

  /**
   * Drive using arcade controls
   * 
   * @param robotOut The desired forward output
   * @param turnAmt  The desired rotation
   */
  public void drive(double robotOutput, double turnAmount) {

    if (m_isSwitchFront)
      m_drive.arcadeDrive(
          -robotOutput * m_speedScaling,
          turnAmount * kTURN_SCALING);
    else
      m_drive.arcadeDrive(
          robotOutput * m_speedScaling,
          turnAmount * kTURN_SCALING);

  }

  public void switchFront() {
    m_isSwitchFront = !m_isSwitchFront;
  }

  /**
   * For use with targetdrivebase.
   * 
   * @see TargetDrivebase
   * @param turnAmt
   */
  public void target(double turnAmt) {
    this.drive(0, turnAmt);
  }

  public Value getGear() {
    return shifter.get();
  }

  public void shiftHigh() {
    shifter.set(Value.kForward);
    m_speedScaling = kHIGH_GEAR_SPEED_SCALING;
  }

  public void shiftLow() {
    shifter.set(Value.kReverse);
    m_speedScaling = kLOW_GEAR_SPEED_SCALING;
  }

  public void stopMotors() {
    m_drive.arcadeDrive(0.0, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (this.getGear() == Value.kForward) {
      SmartDashboard.putBoolean("High Gear Enabled", true);
    } else {
      SmartDashboard.putBoolean("High Gear Enabled", false);
    }

    if (RobotContainer.debugMode) {
      doDebugMode();
    }

  }

  /**
   * Run any debugging here
   * Smart Dashboard Puts & Gets, etc.
   */
  private void doDebugMode() {
    SmartDashboard.putNumber("DEBUG_Drivebase - Avg. Velocity", (Math.abs(m_motorR1.getEncoder().getVelocity())
        + Math.abs(m_motorL1.getEncoder().getVelocity())) / 2);
    SmartDashboard.putNumber("DEBUG_Drivebase - Shift Up Speed", kSHIFT_UP_THRESHOLD);
    SmartDashboard.putNumber("DEBUG_Drivebase - Shift Down Speed", kSHIFT_DOWN_THRESHOLD);

  }

  /**
   * Start any debugging here
   * Useful if getting any values from SD, for tuning specifically
   */
  private void initDebugMode() {

  }
}