// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.EjectFieldElement;
import frc.robot.commands.ExtendIntakeArm;
import frc.robot.commands.RetractIntakeArm;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class EjectFieldElementAndMoveCustom extends SequentialCommandGroup {
  /** Creates a new EjectTwoNoteSequence. */
  public EjectFieldElementAndMoveCustom(Intake intake, DriveTrain driveTrain) {
    addCommands(
      new DriveForwardCustom(driveTrain), // Pulls time from SmartDashboard
      new DriveBackward(driveTrain, 0.1),
      new RetractIntakeArm(intake, 0.5),
      new ExtendIntakeArm(intake, 1.0),
      new EjectFieldElement(intake, 0.5),
      new RetractIntakeArm(intake, 1.5)
    );
  }
}
