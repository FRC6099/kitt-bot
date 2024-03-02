// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.controllers.ClimberController;
import frc.robot.subsystems.Climber;

public class OperateClimber extends Command {

  private final ClimberController controller;
  private final Climber climber;

  /** Creates a new OperateClimber. */
  public OperateClimber(ClimberController controller, Climber climber) {
    this.controller = controller;
    this.climber = climber;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double left = controller.getLeftPosition();
    double right = controller.getRightPosition();

    if (Math.abs(left) > 0.1) {
      climber.moveLeft(left);
    } else {
      climber.stopLeft();
    }
    if (Math.abs(right) > 0.1) {
      climber.moveRight(right);
    } else {
      climber.stopRight();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.stopLeft();
    climber.stopRight();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
