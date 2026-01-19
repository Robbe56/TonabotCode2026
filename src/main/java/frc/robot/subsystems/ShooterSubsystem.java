// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax ShooterMotor;

  private final SparkClosedLoopController ShooterController;

  SparkMaxConfig ShooterMotorConfig = new SparkMaxConfig();
 

  public ShooterSubsystem() {
    ShooterMotor = new SparkMax(21, MotorType.kBrushless);
    ShooterController = ShooterMotor.getClosedLoopController();
  
//set PID gains for shooter
ShooterMotorConfig.closedLoop
.p(0.0008)
.i(0)
.d(0.000015)
.outputRange(0, 1500);


  }
  public void spinShooter(double ShooterSpeed) {
    ShooterController.setSetpoint(ShooterSpeed, ControlType.kVelocity);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
