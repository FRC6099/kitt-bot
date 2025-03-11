// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.enums.ArmPosition;

public class Intake extends SubsystemBase {

  private final SparkMax armLeftMotor = new SparkMax(Constants.LEFT_ARM_MOTOR_CAN_ID , MotorType.kBrushless);
  private final SparkMax armRightMotor = new SparkMax(Constants.RIGHT_ARM_MOTOR_CAN_ID, MotorType.kBrushless);

  private final RelativeEncoder armEncoder = armLeftMotor.getEncoder();
  private final SparkMax leftIntakeMotor = new SparkMax(Constants.LEFT_INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);
  private final SparkMax rightIntakeMotor = new SparkMax(Constants.RIGHT_INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);


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
      armLeftMotor.set(-0.2);
      armRightMotor.set(0.2);

      // armPID.setReference(0, ControlType.kPosition);
    } else if (ArmPosition.EXTENDED == position) {
      armLeftMotor.set(0.2);
      armRightMotor.set(-0.2);
      // TODO: Get Actual Position for EXTENDED
      // armPID.setReference(90, ControlType.kPosition);
    }
  }

  public void moveArm(double speed) {
    armLeftMotor.set(-speed*0.3);
    armRightMotor.set(speed*0.3);
  
  }

  public void stopArm() {
    armLeftMotor.set(0.0);
    armRightMotor.set(0.0);
    // armPID.setReference(0.0, ControlType.kVoltage);
  }

  public void inject() {
    leftIntakeMotor.set(-0.25);
    rightIntakeMotor.set( -0.25);
  }

  public void eject() {
    leftIntakeMotor.set( 0.25);
    rightIntakeMotor.set(0.25);
  }

  public void stopIntake() {
    leftIntakeMotor.set( 0.0);
    rightIntakeMotor.set( 0.0);

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

  public boolean isElementPresent() {
    return true;
  }

}