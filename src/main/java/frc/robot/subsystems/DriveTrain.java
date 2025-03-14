// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  private final SparkMax leftFrontMotor = new SparkMax(Constants.LEFT_FRONT_DRIVE_MOTOR_CAN_ID, MotorType .kBrushless);
  private final SparkMax rightFrontMotor = new SparkMax(Constants.RIGHT_FRONT_DRIVE_MOTOR_CAN_ID, MotorType .kBrushless);
  private final SparkMax leftRearMotor = new SparkMax(Constants.LEFT_REAR_DRIVE_MOTOR_CAN_ID, MotorType.kBrushless);
  private final SparkMax rightRearMotor = new SparkMax(Constants.RIGHT_REAR_DRIVE_MOTOR_CAN_ID, MotorType .kBrushless);


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    // TODO: Configure motors to use encoders instead (see update-drivetrain-sample branch)
    SparkMaxConfig leftFrontConfig = getLeaderConfig(false);
    SparkMaxConfig rightFrontConfig = getLeaderConfig(true);

    leftFrontMotor.configure(
      leftFrontConfig,
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    leftRearMotor.configure(
      getFollowerConfig(leftFrontMotor),
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    rightFrontMotor.configure(
      rightFrontConfig, 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    rightRearMotor.configure(
      getFollowerConfig(rightFrontMotor),
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);
  }

  private SparkMaxConfig getLeaderConfig(boolean inverted) {
    // https://www.chiefdelphi.com/t/motion-magic-for-spark-max/472664/6
    SparkMaxConfig config = new SparkMaxConfig();
    config.encoder
          .positionConversionFactor(1.0)
          .velocityConversionFactor(1.0);
    // leftFrontConfig.closedLoopRampRate(1.0); // One second to go full throttle
    config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(0.5)
        .i(0.0)
        .d(0.0)
        .velocityFF(0.0)
        .maxOutput(1.0)
        .minOutput(-1.0)
        .maxMotion
            .maxAcceleration(1000.0)
            .maxVelocity(2000.0)
            .allowedClosedLoopError(0.1);
    config
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(40)
        .inverted(inverted);
    return config;
  }

  private SparkBaseConfig getFollowerConfig(SparkMax leader) {
    return new SparkMaxConfig()
      .idleMode(IdleMode.kBrake)
      .smartCurrentLimit(40)
      .follow(leader);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveLeftMotors(double speed) {
    leftFrontMotor.set(speed);
  }
  
  public void moveRightMotors(double speed) {
    rightFrontMotor.set(speed);
  }

  public void stop() {
    moveLeftMotors(0.0);
    moveRightMotors(0.0);
  }
}
