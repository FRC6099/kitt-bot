// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private final CANSparkFlex leftShooterMotor = new CANSparkFlex(Constants.LEFT_SHOOTER_ARM_MOTOR_CAN_ID, MotorType.kBrushless);
  private final CANSparkFlex rightShooterMotor = new CANSparkFlex(Constants.RIGHT_SHOOTER_ARM_MOTOR_CAN_ID, MotorType.kBrushless);
  private final Timer timer = new Timer();

  /** Creates a new Shooter. */
  public Shooter() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void prime() {
    timer.start();
    leftShooterMotor.set(-0.4);
    rightShooterMotor.set(0.4);
    // leftShooterMotor.getPIDController().setReference(3.0, ControlType.kVoltage);
    // rightShooterMotor.getPIDController().setReference(3.0, ControlType.kVoltage);
  }

  public void stop() {
    timer.stop();
    timer.reset();
    leftShooterMotor.set(0.0);
    rightShooterMotor.set(0.0);
  }

  public boolean isPrimed() {
    return timer.hasElapsed(0.333);
  }
}
