// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/** Add your docs here. */
public class ClimberController {
    private final CommandXboxController controller;

    public ClimberController(CommandXboxController xboxController) {
        this.controller = xboxController;
    }

    public double getLeftPosition() {
        return controller.getRawAxis(Axis.kLeftY.value);
    }

    public double getRightPosition() {
        return controller.getRawAxis(Axis.kRightY.value);
    }
}
