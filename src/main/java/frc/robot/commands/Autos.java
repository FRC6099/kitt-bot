// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.List;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.enums.RobotDistance;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public final class Autos {

  public static Command exampleAuto(DriveSubsystem drive, ShooterSubsystem shooter, IntakeSubsystem intake) {
    // Create config for trajectory
        TrajectoryConfig config = new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(DriveConstants.kDriveKinematics)
                .setReversed(true);

        // An example trajectory to follow. All units in meters.
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(-0.15, 0, Rotation2d.fromDegrees(0)),
                config);

        var thetaController = new ProfiledPIDController(
                AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
                exampleTrajectory,
                drive::getPose, // Functional interface to feed supplier
                DriveConstants.kDriveKinematics,

                // Position controllers
                new PIDController(AutoConstants.kPXController, 0, 0),
                new PIDController(AutoConstants.kPYController, 0, 0),
                thetaController,
                drive::setModuleStates,
                drive);

        drive.zeroHeading();
        // Reset odometry to the starting pose of the trajectory.
        drive.resetOdometry(exampleTrajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return swerveControllerCommand
                .andThen(() -> drive.drive(0, 0, 0, false), drive)
                .andThen(
                        shooter.runShooterCommand(RobotDistance.ADJACENT)
                                .alongWith(intake.runIntakeCommand())
                                .withTimeout(10.0)
                );
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
