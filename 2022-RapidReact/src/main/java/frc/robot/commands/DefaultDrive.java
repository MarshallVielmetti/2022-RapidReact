package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

public class DefaultDrive extends CommandBase {
    private final Drivebase m_drivebase;

    private final DoubleSupplier m_forward;
    private final DoubleSupplier m_backward;
    private final DoubleSupplier m_rotation;

    /**
     * Creates a new DefaultDrive.
     *
     * @param subsystem The drive subsystem this command wil run on.
     * @param forward   The control input for driving forwards/backwards
     * @param rotation  The control input for turning
     */
    public DefaultDrive(Drivebase drivebase, DoubleSupplier forward, DoubleSupplier backward, DoubleSupplier rotation) {
        m_drivebase = drivebase;
        m_forward = forward;
        m_backward = backward;
        m_rotation = rotation;

        addRequirements(m_drivebase);
    }

    @Override
    public void execute() {
        m_drivebase.drive(m_forward.getAsDouble() - m_backward.getAsDouble(), m_rotation.getAsDouble());
    }
}
