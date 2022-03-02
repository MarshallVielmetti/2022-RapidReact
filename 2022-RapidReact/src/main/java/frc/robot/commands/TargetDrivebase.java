package frc.robot.commands;

import static frc.robot.Constants.DrivebaseConstants.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Limelight;

public class TargetDrivebase extends PIDCommand {

    private Drivebase m_drivebase;
    private Limelight m_launcherLimelight;

    /**
     * Implements a basic proportional controller to aim the drivebase for launcher
     * 
     * @param drivebase
     * @param launcher_limelight
     */

    public TargetDrivebase(Drivebase drivebase, Limelight launcherLimelight) {
        super(new PIDController(kTurnP, 0, 0),
                launcherLimelight::getX,
                0,
                drivebase::target,
                drivebase);

        m_drivebase = drivebase;
        m_launcherLimelight = launcherLimelight;

        getController().setTolerance(kTolerance);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        m_drivebase.stopMotors();
    }
}
