// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.enums.ArmPosition;
import frc.robot.subsystems.Intake;

public class EjectFieldElement extends Command {
  private final Intake intake;
  private final Timer timer = new Timer();

  private boolean wasElementPresent = false;
  private boolean isTimerStarted = false;
  private final double duration;


  /** Creates a new InjectNote. */
  public EjectFieldElement(Intake intake) {
    this(intake, 7.0);
  }

  public EjectFieldElement(Intake intake, double duration) {
    this.intake = intake;
    this.duration=duration;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!isFinished()) {
      this.intake.eject();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.intake.stopIntake();
    this.timer.stop();
    this.wasElementPresent = false;
    this.isTimerStarted = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return !this.getWasElementPresent(); // || ArmPosition.HOME != this.intake.getArmPosition();
    return timer.hasElapsed(duration);
  }

  private boolean getWasElementPresent() {
    if (wasElementPresent && isTimerStarted && timer.hasElapsed(2.0)) {
      wasElementPresent = false;
      isTimerStarted = false;
      timer.stop();
    }

    if (this.intake.isElementPresent()) {
      wasElementPresent = true;
    } else if (wasElementPresent && !isTimerStarted) {
      isTimerStarted = true;
      this.timer.reset();
      timer.start();
    }

    return wasElementPresent;
  }
}
