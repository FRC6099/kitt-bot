// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    /*** USB Ports  ***/
    public static final int XBOX_CONTROLLER_USB_ID = 0;
    public static final int LEFT_JOYSTICK_USB_ID = 1;
    public static final int RIGHT_JOYSTICK_USB_ID = 2;

    /*** CAN IDs  ***/
    // Drive Train
    public static final int RIGHT_FRONT_DRIVE_MOTOR_CAN_ID = 1;
    public static final int RIGHT_REAR_DRIVE_MOTOR_CAN_ID = 2;
    public static final int LEFT_FRONT_DRIVE_MOTOR_CAN_ID = 3;
    public static final int LEFT_REAR_DRIVE_MOTOR_CAN_ID = 4;

    // Intake Arm
    public static final int INTAKE_ARM_MOTOR_CAN_ID = 5;
    public static final int INTAKE_GRABBER_MOTOR_CAN_ID = 6;

    // Shooter
    public static final int LEFT_SHOOTER_ARM_MOTOR_CAN_ID = 7;
    public static final int RIGHT_SHOOTER_ARM_MOTOR_CAN_ID = 8;

    // Climber
    public static final int LEFT_CLIMBER_MOTOR_CAN_ID = 9;
    public static final int RIGHT_CLIMBER_MOTOR_CAN_ID = 10;

    /*** Relays ***/
    // Climber
    public static final int LEFT_CLIMBER_RELAY_ID = 0;
    public static final int RIGHT_CLIMBER_RELAY_ID = 1;

    /*** Digital IO ***/
    public static final int NOTE_LIMIT_SWITCH = 0;
}
