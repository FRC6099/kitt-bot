// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  private final SparkMax leftFrontMotor = new SparkMax(Constants.LEFT_FRONT_DRIVE_MOTOR_CAN_ID, MotorType .kBrushless);
  private final SparkMax rightFrontMotor = new SparkMax(Constants.RIGHT_FRONT_DRIVE_MOTOR_CAN_ID, MotorType .kBrushless);
  private final SparkMax leftRearMotor = new SparkMax(Constants.LEFT_REAR_DRIVE_MOTOR_CAN_ID, MotorType.kBrushless);
  private final SparkMax rightRearMotor = new SparkMax(Constants.RIGHT_REAR_DRIVE_MOTOR_CAN_ID, MotorType .kBrushless);


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    // TODO: Configure motors to use encoders instead
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveLeftMotors(double speed) {
    leftFrontMotor.set(speed);
    leftRearMotor.set(speed);
  }
  
  public void moveRightMotors(double speed) {
    rightFrontMotor.set(-speed);
    rightRearMotor.set(-speed);
  }

  public void stop() {
    moveLeftMotors(0.0);
    moveRightMotors(0.0);
  }
}
