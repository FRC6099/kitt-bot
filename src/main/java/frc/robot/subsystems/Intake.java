// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.enums.ArmPosition;

public class Intake extends SubsystemBase {

  private final SparkMax armMotor = new SparkMax(Constants.INTAKE_ARM_MOTOR_CAN_ID, MotorType.kBrushed);
  private final RelativeEncoder armEncoder = armMotor.getEncoder();
  private final WPI_TalonSRX grabberMotor = new WPI_TalonSRX(Constants.INTAKE_GRABBER_MOTOR_CAN_ID);
  private final DigitalInput noteLimitSwitch = new DigitalInput(Constants.NOTE_LIMIT_SWITCH);

  /** Creates a new Intake. */
  public Intake() {

    // Clear out any motor specific settings when controller was first configured
    // armMotor.restoreFactoryDefaults();
    
    // armPID.setFeedbackDevice(armEncoder);

    // TODO: Configure encoder
    // Test Controller in REV Hardware Client to detect position to tune settings
    // PID coefficients
    //   Proportional = Multiplied with Error to produce motor output
    //   Integral = Multiplied with Duration Error has occured (keeps motor moving) to produce motor output
    //   Derivative = Multipled with Change in Error (slope) to produce motor output
    //   Error = Set point - current value
    //   FeedForward = Multiplier to maintain velocity
    // Example: https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Encoder%20Feedback%20Device/src/main/java/frc/robot/Robot.java
    // double kP = 0.1; 
    // double kI = 0.0001;
    // double kD = 0.0; 
    // double kIz = 0.0; 
    // double kFF = 0.0; 
    // double kMaxOutput = 0.5; 
    // double kMinOutput = -0.5;

    // set PID coefficients
    // armPID.setP(kP);
    // armPID.setI(kI);
    // armPID.setD(kD);
    // armPID.setIZone(kIz);
    // armPID.setFF(kFF);
    // armPID.setOutputRange(kMinOutput, kMaxOutput);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // https://docs.revrobotics.com/brushless/spark-max/revlib/migrating

  public void moveArmForward(double speed) {
    double positiveSpeed = Math.abs(speed);
    if (getArmPosition() != ArmPosition.EXTENDED) {
      moveArm(positiveSpeed);
    }
  }

  public void moveArmBackward(double speed) {
    double negativeSpeed = -1.0 * Math.abs(speed);
    if (getArmPosition() != ArmPosition.HOME) {
      moveArm(negativeSpeed);
    }
  }

  public void moveArm(ArmPosition position) {
    if (ArmPosition.HOME == position) {
      armMotor.set(0.2);
      // armPID.setReference(0, ControlType.kPosition);
    } else if (ArmPosition.EXTENDED == position) {
      armMotor.set(-0.2);
      // TODO: Get Actual Position for EXTENDED
      // armPID.setReference(90, ControlType.kPosition);
    }
  }

  public void moveArm(double speed) {
    armMotor.set(speed);
  }

  public void stopArm() {
    armMotor.set(0.0);
    // armPID.setReference(0.0, ControlType.kVoltage);
  }

  public void inject() {
    grabberMotor.set(ControlMode.PercentOutput, 0.75);
  }

  public void eject() {
    grabberMotor.set(ControlMode.PercentOutput, -0.75);
  }

  public void stopIntake() {
    grabberMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public ArmPosition getArmPosition() {
    SmartDashboard.putNumber("Arm ticks", armEncoder.getPosition());
    // TODO: Find actual positions
    if (armEncoder.getPosition() < 100.0) {
      return ArmPosition.HOME;
    } else if (armEncoder.getPosition() > 1000.0) {
      return ArmPosition.EXTENDED;
    }
    return ArmPosition.OUT_OF_POSITION;
  }

  public boolean isNotePresent() {
    return !noteLimitSwitch.get();
  }
}
