package frc.robot.subsystems;

import static frc.robot.Constants.HoldConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Hold extends SubsystemBase {

    private final CANSparkMax m_motorBelt = new CANSparkMax(kbeltTopID, CANSparkMaxLowLevel.MotorType.kBrushless);

    private final DigitalInput limTop = new DigitalInput(0);
    // public static DigitalInput limBottom;

    public Hold() {
        if (RobotContainer.debugMode) {
            initDebugMode();
        }
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("LimitSwitchTop", !this.getLimSwitchTop());

        if (RobotContainer.debugMode) {
            doDebugMode();
        }
    }

    public boolean getLimSwitchTop() {
        return limTop.get();
    }

    public void stop() {
        run(0);
    }

    public void run(double power) {
        m_motorBelt.set(power);
    }

    public void run() {
        run(kHOLD_SPEED);
    }

    public void runBackward() {
        run(-kHOLD_SPEED);
    }

    /**
     * Run any debugging here
     * Smart Dashboard Puts & Gets, etc.
     */
    private void doDebugMode() {

    }

    /**
     * Start any debugging here
     */
    private void initDebugMode() {

    }
}