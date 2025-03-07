// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.EjectFieldElement;
import frc.robot.commands.ExtendIntakeArm;
import frc.robot.commands.InjectFieldElement;
import frc.robot.commands.RetractIntakeArm;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class EjectTwoNoteSequence extends SequentialCommandGroup {
  /** Creates a new EjectTwoNoteSequence. */
  public EjectTwoNoteSequence(Intake intake, DriveTrain driveTrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    InjectFieldElement injectNote = new InjectFieldElement(intake);
    DriveForward driveForward = new DriveForward(driveTrain, 2.0);
    addCommands(
      new EjectFieldElement(intake, driveTrain),
      new ExtendIntakeArm(intake, 2.0),
      new ParallelCommandGroup(
        injectNote, 
        driveForward,
        new CancelCommandsWhenAnyFinish(injectNote, driveForward)),
          new DriveBackward(driveTrain, 0.05),
        // new ParallelCommandGroup(
          new RetractIntakeArm(intake, 2.0),
          new DriveBackward(driveTrain, 1.75),     
          //),
      new EjectFieldElement(intake, driveTrain)
    );
  }
}
