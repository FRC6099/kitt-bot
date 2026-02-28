// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants;

public class SlapperSubsystem extends SubsystemBase {
   private final SparkMax m_slapperMotor = new SparkMax(Constants.SlapperSubsystemConstants.kSlapperMotorCanId, MotorType.kBrushless);

  /** Creates a new SlapperSubsystem. */
  public SlapperSubsystem() {
    m_slapperMotor.configure(
      Configs.SlapperSubsystem.slapperConfig, 
      ResetMode.kResetSafeParameters,
      PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void extend() {
    m_slapperMotor.set(Constants.SlapperSubsystemConstants.kSlapperSpeed);
  }

  public void retract() {
    m_slapperMotor.set(-1.0 * Constants.SlapperSubsystemConstants.kSlapperSpeed);
  }

  public void stop() {
    m_slapperMotor.set(0.0);
  }
}
