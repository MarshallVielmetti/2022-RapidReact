package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Simple container for the limelight
 */
public class Limelight {
  private NetworkTable m_visionTable;
  private NetworkTableEntry m_visV;
  private NetworkTableEntry m_visX;
  private NetworkTableEntry m_visY;
  private NetworkTableEntry m_visA;
  private NetworkTableEntry m_ledState;

  public Limelight(String name) {

    m_visionTable = NetworkTableInstance.getDefault().getTable(name);
    m_visV = m_visionTable.getEntry("tv");
    m_visX = m_visionTable.getEntry("tx");
    m_visY = m_visionTable.getEntry("ty");
    m_visA = m_visionTable.getEntry("ta");
    m_ledState = m_visionTable.getEntry("ledMode");

    m_ledState.forceSetDouble(3.0);
  }

  public boolean targetSpotted() {
    if (getV() == 1)
      return true;
    return false;
  }

  public double getX() {
    return m_visX.getDouble(0.0);
  }

  public double getY() {
    return m_visY.getDouble(0.0);
  }

  public double getA() {
    return m_visA.getDouble(0.0);
  }

  public double getV() {
    return m_visV.getDouble(0.0);
  }
}