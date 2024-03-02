// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.enums.ArmPosition;
import frc.robot.subsystems.Intake;

public class EjectNote extends Command {
  private final Intake intake;

  /** Creates a new InjectNote. */
  public EjectNote(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!isFinished()) {
      boolean isShooterPrimed = true;
      if (isShooterPrimed) {
        this.intake.eject();
      }
      // TODO: this.shooter.prime()
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.intake.stopIntake();
    //this.shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !this.intake.isNotePresent() || ArmPosition.HOME != this.intake.getArmPosition();
  }
}
