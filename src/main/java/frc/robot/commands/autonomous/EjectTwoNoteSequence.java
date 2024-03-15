// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.EjectNote;
import frc.robot.commands.ExtendIntakeArm;
import frc.robot.commands.InjectNote;
import frc.robot.commands.RetractIntakeArm;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class EjectTwoNoteSequence extends SequentialCommandGroup {
  /** Creates a new EjectTwoNoteSequence. */
  public EjectTwoNoteSequence(Intake intake, Shooter shooter, DriveTrain driveTrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new EjectNote(intake, shooter),
      new ExtendIntakeArm(intake),
      new ParallelCommandGroup(
        new InjectNote(intake), 
        new DriveForward(driveTrain, 1.0)),
      new RetractIntakeArm(intake),
      new DriveBackward(driveTrain, 1.0),
      new EjectNote(intake, shooter)
    );
  }
}
