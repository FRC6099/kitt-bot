package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.config.AbsoluteEncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.Constants.ModuleConstants;

public final class Configs {
  private static final double nominalVoltage = 12.0;

  public static final class IntakeSubsystem {
    public static final SparkFlexConfig intakeConfig = new SparkFlexConfig();
    public static final SparkFlexConfig conveyorConfig = new SparkFlexConfig();

    static {
      // Configure basic settings of the intake motor
      intakeConfig
          .inverted(false)
          .idleMode(IdleMode.kCoast)
          .openLoopRampRate(0.5)
          .smartCurrentLimit(40);

      // Configure basic settings of the conveyor motor
      conveyorConfig
          .inverted(true)
          .idleMode(IdleMode.kCoast)
          .openLoopRampRate(0.5)
          .smartCurrentLimit(40);
    }
  }

  public static final class ShooterSubsystem {
    public static final SparkFlexConfig flywheelConfig = new SparkFlexConfig();
    public static final SparkFlexConfig flywheelFollowerConfig = new SparkFlexConfig();
    public static final SparkFlexConfig feederConfig = new SparkFlexConfig();

    static {
      // Configure basic setting of the flywheel motors
      flywheelConfig
          .inverted(true)
          .idleMode(IdleMode.kCoast)
          .closedLoopRampRate(1.0)
          .openLoopRampRate(1.0)
          .smartCurrentLimit(80);

      /*
       * Configure the closed loop controller. We want to make sure we set the
       * feedback sensor as the primary encoder.
       */
      flywheelConfig.closedLoop
          .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
          // Set PID values for position control
          .p(0.0002)
          .outputRange(-1, 1);

      flywheelConfig.closedLoop.maxMotion
          // Set MAXMotion parameters for MAXMotion Velocity control
          .cruiseVelocity(5000)
          .maxAcceleration(10000)
          .allowedProfileError(1);

      // Constants.NeoMotorConstants.kVortexKv is in rpm/V. feedforward.kV is in V/rpm
      // sort we take
      // the reciprocol.
      flywheelConfig.closedLoop.feedForward.kV(nominalVoltage / Constants.NeoMotorConstants.kVortexKv);

      // Configure the follower flywheel motor to follow the main flywheel motor
      flywheelFollowerConfig.apply(flywheelConfig)
          .follow(Constants.ShooterSubsystemConstants.kFlywheelMotorCanId, true);

      // Configure basic setting of the feeder motor
      feederConfig
          .inverted(true)
          .idleMode(IdleMode.kCoast)
          .openLoopRampRate(1.0)
          .smartCurrentLimit(60);
    }
  }

  public static final class SlapperSubsystem {
    public static final SparkMaxConfig slapperConfig = new SparkMaxConfig();

    static {
      slapperConfig
          .inverted(false)
          .idleMode(IdleMode.kBrake)
          .openLoopRampRate(0.5)
          .smartCurrentLimit(15);
    }
  }

  public static final class MAXSwerveModule {
    public static final SparkMaxConfig drivingConfig = new SparkMaxConfig();
    public static final SparkMaxConfig turningConfig = new SparkMaxConfig();

    static {
      // Use module constants to calculate conversion factors and feed forward gain.
      double drivingFactor = ModuleConstants.kWheelDiameterMeters * Math.PI
          / ModuleConstants.kDrivingMotorReduction;
      double turningFactor = 2 * Math.PI;
      double nominalVoltage = 12.0;
      double drivingVelocityFeedForward = nominalVoltage / ModuleConstants.kDriveWheelFreeSpeedRps;

      drivingConfig
          .idleMode(IdleMode.kBrake)
          .smartCurrentLimit(50);
      drivingConfig.encoder
          .positionConversionFactor(drivingFactor) // meters
          .velocityConversionFactor(drivingFactor / 60.0); // meters per second
      drivingConfig.closedLoop
          .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
          // These are example gains you may need to them for your own robot!
          .pid(0.04, 0, 0)
          .outputRange(-1, 1).feedForward.kV(drivingVelocityFeedForward);

      turningConfig
          .idleMode(IdleMode.kBrake)
          .smartCurrentLimit(20);

      turningConfig.absoluteEncoder
          // Invert the turning encoder, since the output shaft rotates in the opposite
          // direction of the steering motor in the MAXSwerve Module.
          .inverted(true)
          .positionConversionFactor(turningFactor) // radians
          .velocityConversionFactor(turningFactor / 60.0) // radians per second
          // This applies to REV Through Bore Encoder V2 (use REV_ThroughBoreEncoder for
          // V1):
          .apply(AbsoluteEncoderConfig.Presets.REV_ThroughBoreEncoderV2);

      turningConfig.closedLoop
          .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
          // These are example gains you may need to them for your own robot!
          .pid(1, 0, 0)
          .outputRange(-1, 1)
          // Enable PID wrap around for the turning motor. This will allow the PID
          // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
          // to 10 degrees will go through 0 rather than the other direction which is a
          // longer route.
          .positionWrappingEnabled(true)
          .positionWrappingInputRange(0, turningFactor);
    }
  }
}
