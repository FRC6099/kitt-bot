// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class DriveForward extends Command {
   private final DriveTrain driveTrain;
  private final double duration;
  private final Timer timer;

  public DriveForward(DriveTrain driveTrain) {
    this(driveTrain, 4.0);
  }

  public DriveForward(DriveTrain driveTrain, double duration) {
    this(driveTrain, duration, new Timer());
  }

  /** Creates a new Reverse. */
  public DriveForward(DriveTrain driveTrain, double duration, Timer timer) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveTrain = driveTrain;
    this.duration = duration;
    this.timer = timer;
    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.moveLeftMotors(0.25);
    driveTrain.moveRightMotors(0.26);
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
