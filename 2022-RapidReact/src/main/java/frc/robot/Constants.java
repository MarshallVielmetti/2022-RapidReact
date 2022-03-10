// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class ClimbConstants {
        // CLIMB
        public static int kClimbMotorID_LEFT = 20;
        public static int kClimbMotorID_RIGHT = 27;

        public static double kCLIMB_SPEED = 0.4;

        public static double kFULL_EXTENSION = 170.0;
        public static double kFULL_RETRACT = 0.0;
        public static double kBAR_RELEASE = 70.0;
        public static double kPREPARE = 130.0;
        public static double kTRAVERSAL_RETRACT = 80.0;

        public static final double kMANUAL_CLIMB_SPEED = 0.4;
        public static final double kCLIMB_HIGH_SPEED = 1.0; // this is a factor of speed
        public static final double kCLIMB_LOW_SPEED = 0.1; // this is too

        public static final double kCLIMB_RAMP_RATE = 0.2;// adjusts acceleration speed. higher number = slower
                                                          // acceleration

        public static int kCLIMB_LOW_FORWARD_CHANNEL = 3;
        public static int kCLIMB_HIGH_REVERSE_CHANNEL = 2;

        public static final double kP = 0.1;
        public static final double kI = 0;
        public static final double kD = 0;
    }

    /*
     * MOTOR IDS
     * 
     * LEFT FRONT RIGHT
     * 
     * 11 | | 12
     * 13 | -- DRIVE -- | 14
     * 15 | | 16
     * 20 ---- CLIMB ---- 27
     * 21 INTAKE Hold U 26
     * 22 Hold L FlyWheel Top 25
     * 23 Spare Flywheel Bot. 24
     * 
     * LEFT BACK RIGHT
     * 
     */

    public static class DrivebaseConstants {
        // Drivebase Values
        public static final int kfrontLeftID = 11;
        public static final int kmidLeftID = 13;
        public static final int krearLeftID = 15;
        public static final int kfrontRightID = 12;
        public static final int kmidRightID = 14;
        public static final int krearRightID = 16;

        public static final double kDRIVE_RAMP_RATE = 0.3; // bigger number = less acceleration

        public static final double kSHIFT_DOWN_THRESHOLD = 900; // NEEDS CALCULATING // for autoshift
        public static final double kSHIFT_UP_THRESHOLD = 1150; // NEEDS CALCULATING // for autoshift
        public static final int kLOW_FORWARD_CHANNEL = 1;
        public static final int kHIGH_REVERSE_CHANNEL = 0;
        // public static final Value LOW_GEAR = Value.kForward; // Gets it from import
        // edu.wpi.first.wpilibj.DoubleSolenoid.Value;
        // public static final Value HIGH_GEAR = Value.kReverse; // ""

        public static final double kHIGH_GEAR_SPEED_SCALING = 0.7;
        public static final double kLOW_GEAR_SPEED_SCALING = 1;
        public static final double kTURN_SCALING = 0.8;

        // FOR TARGETING
        public static final double kTurnP = 1 / 9;
        public static final double kTolerance = 0.05;

        public static final double kSeekScaler = 0.1;

    }

    // MECHS
    public static final class IntakeConstants {
        public static final int kintakeMotorID = 21;

        public static final double kINTAKE_SPEED = 0.5;
        public static final double kEJECT_SCALING = 1;
    }

    public static final class LauncherConstants {
        public static int kflywheelTopID = 24;
        public static int kflywheelBottomID = 25;

        public static final double kP = 0.1;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kToleranceRPM = 150;

        // TODO - Run Motor Characterization Tool
        // public static final double kSVolts
        // public static final double kVVoltSecondsPerRotation

    }

    public static final class HoldConstants {
        public static final int kbeltTopID = 22;

        public static double kHOLD_SPEED = 0.3;
    }

    public static double cameraToFlat = 45;
    public static final double heightToHub = 60;

}

