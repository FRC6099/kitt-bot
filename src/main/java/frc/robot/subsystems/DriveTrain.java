// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  private final WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(Constants.LEFT_FRONT_DRIVE_MOTOR_CAN_ID);
  private final WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(Constants.RIGHT_FRONT_DRIVE_MOTOR_CAN_ID);
  private final WPI_TalonSRX leftRearMotor = new WPI_TalonSRX(Constants.LEFT_REAR_DRIVE_MOTOR_CAN_ID);
  private final WPI_TalonSRX rightRearMotor = new WPI_TalonSRX(Constants.RIGHT_REAR_DRIVE_MOTOR_CAN_ID);


  /** Creates a new DriveTrain. */
  public DriveTrain() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveLeftMotors(double speed) {
    leftFrontMotor.set(ControlMode.PercentOutput, speed);
    leftRearMotor.set(ControlMode.PercentOutput, speed);
  }
  
  public void moveRightMotors(double speed) {
    rightFrontMotor.set(ControlMode.PercentOutput, -speed);
    rightRearMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void stop() {
    moveLeftMotors(0.0);
    moveRightMotors(0.0);
  }
}
