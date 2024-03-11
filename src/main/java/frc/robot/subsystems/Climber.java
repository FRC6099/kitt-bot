// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

  private final WPI_TalonSRX leftClimber = new WPI_TalonSRX(Constants.LEFT_CLIMBER_MOTOR_CAN_ID);
  private final WPI_TalonSRX rightClimber = new WPI_TalonSRX(Constants.RIGHT_CLIMBER_MOTOR_CAN_ID);
  private final Relay leftRelay = new Relay(Constants.LEFT_CLIMBER_RELAY_ID);
  private final Relay rightRelay = new Relay(Constants.RIGHT_CLIMBER_RELAY_ID);
  /** Creates a new Climber. */
  public Climber() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveLeft(double speed) {
    leftRelay.set(Value.kOn);
    leftClimber.set(ControlMode.PercentOutput, speed);
  }

  public void moveRight(double speed) {
    rightRelay.set(Value.kOn);
    rightClimber.set(ControlMode.PercentOutput, -speed);
  }

  public void stopLeft() {
    leftClimber.set(ControlMode.PercentOutput, 0.0);
    leftRelay.set(Value.kOff);
  }

  public void stopRight() {
    rightClimber.set(ControlMode.PercentOutput, 0.0);
    rightRelay.set(Value.kOff);
  }
}
