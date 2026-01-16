// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax ShooterMotor;

  SparkMaxConfig ShooterMotorConfig = new SparkMaxConfig();

  public ShooterSubsystem() {
    ShooterMotor = new SparkMax(21, MotorType.kBrushless);


  }
  public void spinShooter(double ShooterSpeed) {
    ShooterMotor.set(ShooterSpeed);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
