// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

  private final SparkMax clawMotor = new SparkMax(Constants.CLIMBER_MOTOR_CAN_ID, MotorType.kBrushless);
  /** Creates a new Climber. */
  public Climber() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void operateClaw(double speed) {
    // TODO: Add encoder to limit open and close max positions (perhaps it can also be limited by motor current draw?)
    clawMotor.set(speed);
  }

  public void stopClaw() {
    clawMotor.set(0.0);
  }
}
