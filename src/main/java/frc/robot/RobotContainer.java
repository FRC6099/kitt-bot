// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DetectArmPosition;
import frc.robot.commands.EjectNote;
import frc.robot.commands.ExtendIntakeArm;
import frc.robot.commands.InjectNote;
import frc.robot.commands.OperateClimber;
import frc.robot.commands.RetractIntakeArm;
import frc.robot.commands.TankDrive;
import frc.robot.commands.autonomous.DriveBackward;
import frc.robot.commands.autonomous.DriveForward;
import frc.robot.commands.autonomous.EjectNoteAndMove;
import frc.robot.commands.autonomous.EjectTwoNoteSequence;
import frc.robot.controllers.ClimberController;
import frc.robot.controllers.TankDriveController;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain driveTrain = new DriveTrain();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Climber climber = new Climber();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController xboxController = new CommandXboxController(Constants.XBOX_CONTROLLER_USB_ID);
  private final CommandJoystick leftJoystick = new CommandJoystick(Constants.LEFT_JOYSTICK_USB_ID);
  private final CommandJoystick rightJoystick = new CommandJoystick(Constants.RIGHT_JOYSTICK_USB_ID);

  // Default commands
  private final TankDrive tankDrive = new TankDrive(driveTrain, new TankDriveController(leftJoystick, rightJoystick));
  private final ExtendIntakeArm extendIntakeArm = new ExtendIntakeArm(intake);
  private final RetractIntakeArm retractIntakeArm = new RetractIntakeArm(intake);
  private final InjectNote injectNote = new InjectNote(intake);
  private final EjectNote ejectNote = new EjectNote(intake, shooter);
  private final OperateClimber operateClimber = new OperateClimber(new ClimberController(xboxController), climber);
  private final DetectArmPosition detectArmPosition = new DetectArmPosition(intake);

  // Autonomous Commands
  // Add ability to choose autonomous mode in SmartDashboard
  private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    configureSubsystems();
    configureAutonomousModes();
  }

  private void configureSubsystems() {
    this.driveTrain.setDefaultCommand(tankDrive);
    this.climber.setDefaultCommand(operateClimber);
    this.intake.setDefaultCommand(detectArmPosition);
  }

  private void configureAutonomousModes() {
    this.autonomousChooser.setDefaultOption("Do nothing", new WaitCommand(10.0));
    this.autonomousChooser.addOption("Drive Backwards", new DriveBackward(driveTrain, 3.0));
    this.autonomousChooser.addOption("Drive Forward", new DriveForward(driveTrain, 3.0));
    this.autonomousChooser.addOption("Score A Note", new EjectNoteAndMove(intake, shooter, driveTrain));
    // this.autonomousChooser.addOption("Score two notes", new EjectTwoNoteSequence(intake, shooter, driveTrain));
    SmartDashboard.putData("Autonomous Options", this.autonomousChooser);
    // SmartDashboard.putNumber("Autonomous Number", 0);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    xboxController.leftBumper().whileTrue(extendIntakeArm);
    xboxController.rightBumper().whileTrue(retractIntakeArm);
    xboxController.leftTrigger().whileTrue(injectNote);
    xboxController.rightTrigger().whileTrue(ejectNote);
    // xboxController.a()
    // .whileTrue(new RunCommand(() -> shooter.prime(), shooter))
    // .whileFalse(new RunCommand(() -> shooter.stop(), shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autonomousChooser.getSelected();
  }
}
