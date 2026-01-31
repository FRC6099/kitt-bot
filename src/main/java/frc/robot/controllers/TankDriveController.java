// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.controllers;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class TankDriveController {

    private final CommandJoystick leftJoystick;
    private final CommandJoystick rightJoystick;

    public TankDriveController(CommandJoystick leftJoystick, CommandJoystick rightJoystick) {
        this.leftJoystick = leftJoystick;
        this.rightJoystick = rightJoystick;
    }

    public double getLeftPosition() {
        return leftJoystick.getY() * -1.0;
    }

    public double getRightPosition() {
        return rightJoystick.getY() * -1.0;
    }
    
}
