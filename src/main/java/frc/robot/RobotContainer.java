// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SlapperSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.List;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final SlapperSubsystem m_slapper = new SlapperSubsystem();

    // The driver's controller
    XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
    private final CommandXboxController m_operatorController =
      new CommandXboxController(OIConstants.kOperatorControllerPort);


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands
        m_robotDrive.setDefaultCommand(
                // The left stick controls translation of the robot.
                // Turning is controlled by the X axis of the right stick.
                new RunCommand(
                        () -> m_robotDrive.drive(
                                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                                true),
                        m_robotDrive));

        m_slapper.setDefaultCommand(new RunCommand(() -> m_slapper.stop(), m_slapper));
        SmartDashboard.putData(m_intake);
        SmartDashboard.putData(m_shooter);

        SmartDashboard.putNumber("Bat Voltage", RobotController.getBatteryVoltage());

        SmartDashboard.putData("Intake", m_intake.runIntakeCommand().withName("Intake - Intaking"));
        SmartDashboard.putData("Extake", m_intake.runExtakeCommand().withName("Intake - Extaking"));

        SmartDashboard.putData("Feeder", m_shooter.runFeederCommand().withName("Shooter - Feeding and Shooting"));
        SmartDashboard.putData("Flywheel", m_shooter.runFlywheelCommand().withName("Shooter - Spinning up Flywheel"));

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
     * subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
     * passing it to a
     * {@link JoystickButton}.
     */
    private void configureButtonBindings() {
        new JoystickButton(m_driverController, Button.kR1.value)
                .whileTrue(new RunCommand(
                        () -> m_robotDrive.setX(),
                        m_robotDrive));

        new JoystickButton(m_driverController, XboxController.Button.kStart.value)
                .onTrue(new InstantCommand(
                        () -> m_robotDrive.zeroHeading(),
                        m_robotDrive));

        // Right Trigger -> Run fuel intake in reverse
        m_operatorController
                .rightTrigger(OIConstants.kTriggerButtonThreshold)
                .whileTrue(m_intake.runIntakeCommand());

        // Left Trigger -> Run fuel intake in reverse
        m_operatorController
                .leftTrigger(OIConstants.kTriggerButtonThreshold)
                .whileTrue(m_intake.runExtakeCommand());

        // Left Bumper -> Slapper Up
        m_operatorController
                .leftBumper()
                .whileTrue(new RunCommand(() -> m_slapper.retract(), m_slapper));

        // Right Bumper -> Slapper Down
        m_operatorController
                .leftBumper()
                .whileTrue(new RunCommand(() -> m_slapper.extend(), m_slapper));

        // Y Button -> Run intake and run the shooter flywheel and feeder
        m_operatorController.y().toggleOnTrue(m_shooter.runShooterCommand().alongWith(m_intake.runIntakeCommand()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return Autos.exampleAuto(m_robotDrive);
    }
}
