// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.controllers;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/** Add your docs here. */
public class IntakeController {
    private final CommandXboxController xboxController;
    public IntakeController(CommandXboxController xboxController) {
        this.xboxController = xboxController;
    }

    public boolean isInjectionEngaged() {
        return this.xboxController.getLeftTriggerAxis() > 0.9;
    }

    public boolean isEjectionEngaged() {
        return this.xboxController.getRightTriggerAxis() > 0.9;
    }
}
