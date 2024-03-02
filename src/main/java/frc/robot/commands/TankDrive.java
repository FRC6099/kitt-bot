// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.controllers.TankDriveController;
import frc.robot.subsystems.DriveTrain;

public class TankDrive extends Command {

  private final DriveTrain driveTrain;
  private final TankDriveController controller;
  private boolean enabled = true;

  /** Creates a new TankDrive. */
  public TankDrive(DriveTrain driveTrain, TankDriveController controller) {
    this.driveTrain = driveTrain;
    this.controller = controller;
    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!enabled) {
      return;
    }
    double left = controller.getLeftPosition();
    double right = controller.getRightPosition();
    double leftDirection = left > 0.0 ? 1.0 : -1.0;
    double rightDirection = right > 0.0 ? 1.0 : -1.0;
    
    this.driveTrain.moveLeftMotors(left * left * leftDirection);
    this.driveTrain.moveRightMotors(right * right * rightDirection);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.driveTrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void enable() {
    this.enabled = true;
  }

  public void disable() {
    this.enabled = false;
  }
}
