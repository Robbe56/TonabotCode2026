// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants.IntakeConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax IntakeMotor;
  private SparkMax PusherMotor;

 

  SparkMaxConfig IntakeMotorConfig = new SparkMaxConfig();
 

  public IntakeSubsystem() {
    IntakeMotor = new SparkMax(23, MotorType.kBrushless);
    PusherMotor = new SparkMax(24, MotorType.kBrushless);
    
  
//set PID gains for shooter
//IntakeMotorConfig.closedLoop;

IntakeMotor.configure(IntakeMotorConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

  }
  public void spinIntake(double Direction) {
    IntakeMotor.set(IntakeConstants.IntakeSpeed*Direction);
   
  }
  public void spinPusher(){
    PusherMotor.set(IntakeConstants.PusherSpeed);
  }
  public void stopPusher(){
    PusherMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
