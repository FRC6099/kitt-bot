package frc.robot.enums;

public enum RobotDistance {
    ADJACENT(345.0, 0.0),
    MIDDLE(400.0, 1.0),
    FAR(450.0, 3.0);

    private final double shooterSpeed;
    private final double robotDistance;

    RobotDistance(double shooterSpeed, double robotDistance) {
        this.shooterSpeed = shooterSpeed;
        this.robotDistance = robotDistance;
    }

    public double getShooterSpeed() {
        return this.shooterSpeed;
    }

    public double getRobotDistance() {
        return this.robotDistance;
    }
}
