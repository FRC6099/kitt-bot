// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class DriveForwardCustom extends Command {
   private final DriveTrain driveTrain;
  private final Timer timer;
  private double duration;

  /** Creates a new Reverse. */
  public DriveForwardCustom(DriveTrain driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveTrain = driveTrain;
    this.timer = new Timer();
    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.duration = SmartDashboard.getNumber("Custom Auton Drive Forward", 1.5);
    System.out.println("Duration is set to: " + duration);
    driveTrain.moveLeftMotors(0.2);
    driveTrain.moveRightMotors(0.2);
    timer.reset();
    timer.start();
    System.out.println("** Moving Forward");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
    timer.stop();
    System.out.println("** Moving Forward Complete");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(duration);
  }
}
