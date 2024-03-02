// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.enums.ArmPosition;

public class Intake extends SubsystemBase {

  private final CANSparkMax armMotor = new CANSparkMax(Constants.INTAKE_ARM_MOTOR_CAN_ID, MotorType.kBrushless);
  private final SparkPIDController armMotorController = armMotor.getPIDController();
  private final SparkAbsoluteEncoder armEncoder = armMotor.getAbsoluteEncoder();
  private final TalonSRX grabberMotor = new TalonSRX(Constants.INTAKE_GRABBER_MOTOR_CAN_ID);
  private final DigitalInput noteLimitSwitch = new DigitalInput(Constants.NOTE_LIMIT_SWITCH);

  /** Creates a new Intake. */
  public Intake() {
    // TODO: Configure encoder
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
      armMotorController.setReference(0, ControlType.kPosition);
    } else if (ArmPosition.EXTENDED == position) {
      armMotorController.setReference(90, ControlType.kPosition);
    }
  }

  public void moveArm(double speed) {
    armMotorController.setReference(speed * 10, ControlType.kVelocity);
  }

  public void stopArm() {
    armMotorController.setReference(0.0, ControlType.kVoltage);
  }

  public void inject() {
    grabberMotor.set(TalonSRXControlMode.PercentOutput, 0.5);
  }

  public void eject() {
    grabberMotor.set(TalonSRXControlMode.PercentOutput, -0.5);
  }

  public ArmPosition getArmPosition() {
    return ArmPosition.HOME;
  }

  public boolean isNotePresent() {
    return noteLimitSwitch.get();
  }
}
