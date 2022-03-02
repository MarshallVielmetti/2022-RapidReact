package frc.robot.subsystems;

import static frc.robot.Constants.LauncherConstants.*;

import java.util.HashMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.RobotContainer;

public class Launcher extends PIDSubsystem {
    private final CANSparkMax m_motorFlywheelTop = new CANSparkMax(kflywheelTopID, MotorType.kBrushless);
    private final CANSparkMax m_motorFlywheelBottom = new CANSparkMax(kflywheelBottomID, MotorType.kBrushless);

    private final RelativeEncoder m_encoderFlywheelTop = m_motorFlywheelTop.getEncoder();
    // private final RelativeEncoder m_encoderFlywheelBottom =
    // m_motorFlywheelBottom.getEncoder();

    // Pixels, Target RPM
    private HashMap<Double, Integer> m_pixelMap;

    // TODO
    // private final SimpleMotorFeedforward m_shooterFeedforward = new
    // SimpleMotorFeedforward(kSVolts, kVVoltSecondsPerRotation);

    public Launcher() {
        super(new PIDController(kP, kI, kD));
        createPixelMap();

        getController().setTolerance(kToleranceRPM);

        m_motorFlywheelTop.setInverted(true);
        m_motorFlywheelBottom.setInverted(false);

        if (RobotContainer.debugMode) {
            initDebugMode();
        }

    }

    @Override
    public void useOutput(double output, double setpoint) {
        // m_motorFlywheelTop.setVoltage(output +
        // m_shooterFeedforward.calculate(setpoint));
        // m_motorFlywheelBottom.setVoltage(output +
        // m_shooterFeedforward.calculate(setpoint));

        m_motorFlywheelTop.setVoltage(output);
        m_motorFlywheelTop.setVoltage(output);

    }

    @Override
    public double getMeasurement() {
        return m_encoderFlywheelTop.getVelocity();
    }

    @Override
    public void periodic() {
        // Runs all functionality of PIDSubsystem
        super.periodic();

        // Displays whether or not launcher is at setpoint
        SmartDashboard.putBoolean("Launcher - At Setpoint", getController().atSetpoint());

        // This method will be called once per scheduler run
        if (RobotContainer.debugMode) {
            doDebugMode();
        }
    }

    public boolean atSetpoint() {
        return getController().atSetpoint();
    }

    /**
     * Command to target shooter based on pixels
     * Uses RPM from pixelMap unless Debug Mode is enabled
     * 
     * @param pixHeight - Height (In Pixels)
     */
    public void targetPixels(double pixHeight) {
        if (!RobotContainer.debugMode) {
            // Not debug
            getController().setSetpoint(getNearestRPM(pixHeight));
        } else {
            // debug - tuning
            getController().setSetpoint(SmartDashboard.getNumber("DEBUG_Launcher Target RPM", 0));
        }
    }

    /**
     * Used to initialize hashmap of pixel heights to RPM Values
     * Too add a new value, simply write it here
     */
    private void createPixelMap() {
        m_pixelMap = new HashMap<Double, Integer>();
        m_pixelMap.put(0.0, 1600); // Low Goal
        m_pixelMap.put(8.0, 3583);
        m_pixelMap.put(8.3, 3350);
        m_pixelMap.put(22.0, 2900);
    }

    /**
     * Returns the nearest RPM value from m_pixelMap for a given pixel height
     * 
     * @param pixels - Height of pixels
     * @return RPM value from m_pixelMap
     */
    private int getNearestRPM(double pixels) {
        double minDiff = Double.MAX_VALUE;
        double nearest = 0;
        for (double key : m_pixelMap.keySet()) {
            double diff = Math.abs(pixels - key);
            if (diff < minDiff) {
                nearest = key;
                minDiff = diff;
            }
        }

        return m_pixelMap.get(nearest);
    }

    /**
     * Run any debugging here
     * Smart Dashboard Puts & Gets, etc.
     */
    private void doDebugMode() {
        // Used to display launcher setpoint
        SmartDashboard.putNumber("Launcher - Target Setpoint", getController().getSetpoint());
    }

    /**
     * Start any debugging here
     */
    private void initDebugMode() {
        // This value is used for setting launcher setpoint
        SmartDashboard.putNumber("DEBUG_Launcher Target RPM", 0);
    }

}