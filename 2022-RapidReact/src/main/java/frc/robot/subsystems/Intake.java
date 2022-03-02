package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotContainer;

public class Intake extends SubsystemBase {
    private final CANSparkMax intakeMotor = new CANSparkMax(kintakeMotorID, MotorType.kBrushed);
    private final DoubleSolenoid intakePistons = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 5);

    public Intake() {
        if (RobotContainer.debugMode) {
            initDebugMode();
        }
    }

    @Override
    public void periodic() {
        if (RobotContainer.debugMode) {
            doDebugMode();
        }
    }

    /**
     * Default intake
     */
    public void run() {
        this.run(kINTAKE_SPEED);
    }

    public void run(double speed) {
        intakeMotor.set(speed);
    }

    public void togglePistons() {
        if (intakePistons.get() == Value.kReverse) {
            pullIntakeIn();
        } else {
            pushIntakeOut();
        }
    }

    public void pullIntakeIn() {
        intakePistons.set(Value.kForward);
    }

    public void pushIntakeOut() {
        intakePistons.set(Value.kReverse);
    }

    public void stop() {
        run(0);
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
