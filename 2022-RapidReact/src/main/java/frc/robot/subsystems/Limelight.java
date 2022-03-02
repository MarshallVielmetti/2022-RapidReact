package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.SwitchPipeline;

public class Limelight extends SubsystemBase {
  public static Limelight instance;

  private NetworkTable m_visionTable;
  private NetworkTableEntry m_visV;
  private NetworkTableEntry m_visX;
  private NetworkTableEntry m_visY;
  private NetworkTableEntry m_visA;
  private NetworkTableEntry m_ledState;
  SwitchPipeline pipelineSwitch;

  private String m_name;

  public Limelight(String name) {
    Limelight.instance = this;
    m_name = name;

    m_visionTable = NetworkTableInstance.getDefault().getTable(name);
    m_visV = m_visionTable.getEntry("tv");
    m_visX = m_visionTable.getEntry("tx");
    m_visY = m_visionTable.getEntry("ty");
    m_visA = m_visionTable.getEntry("ta");
    m_ledState = m_visionTable.getEntry("ledMode");

    pipelineSwitch = new SwitchPipeline(this);

    SmartDashboard.putData("SwitchPipeline", pipelineSwitch);
    m_visV = m_visionTable.getEntry("tv");
    m_ledState = m_visionTable.getEntry("ledMode");

    m_ledState.forceSetDouble(3.0);
  }

  public boolean targetSpotted() {
    if (getV() == 1)
      return true;
    return false;
  }

  public void switchPipeline() {
    if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("getpipe").getDouble(0) == 0.0) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    } else {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(m_name + "tx", getX());
  }

  public void setPipeline(int pipeline) {
    // 0 = blue_cargo
    // 1 = red_cargo

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
  }

  public void setPipelineByName(String pipeline) {
    if (pipeline.equals("blue_cargo")) {
      setPipeline(0);
    } else if (pipeline.equals("red_cargo")) {
      setPipeline(1);
    } else {
      setPipeline(0);
    }

    SmartDashboard.putString("limelight_PipelineName", pipeline);
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

  public static Limelight getInstance() {
    return Limelight.instance;
  }

  public double getV() {
    return m_visV.getDouble(0.0);
  }
}