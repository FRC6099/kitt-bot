// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.enums.ArmPosition;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class EjectNote extends Command {
  private final Intake intake;
  private final Shooter shooter;
  private final DriveTrain driveTrain;
  private final Timer timer = new Timer();

  private boolean wasNotePresent = false;
  private boolean isTimerStarted = false;

  /** Creates a new InjectNote. */
  public EjectNote(Intake intake, Shooter shooter, DriveTrain driveTrain) {
    this.intake = intake;
    this.shooter = shooter;
    this.driveTrain = driveTrain;
    addRequirements(intake, shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.driveTrain.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!isFinished()) {
      if (this.shooter.isPrimed()) {
        this.intake.eject();
      }
      this.shooter.prime();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.intake.stopIntake();
    this.shooter.stop();
    this.timer.stop();
    this.wasNotePresent = false;
    this.isTimerStarted = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !this.getWasNotePresent(); // || ArmPosition.HOME != this.intake.getArmPosition();
  }

  private boolean getWasNotePresent() {
    if (wasNotePresent && isTimerStarted && timer.hasElapsed(2.0)) {
      wasNotePresent = false;
      isTimerStarted = false;
      timer.stop();
    }

    if (this.intake.isNotePresent()) {
      wasNotePresent = true;
    } else if (wasNotePresent && !isTimerStarted) {
      isTimerStarted = true;
      this.timer.reset();
      timer.start();
    }

    return wasNotePresent;
  }
}
