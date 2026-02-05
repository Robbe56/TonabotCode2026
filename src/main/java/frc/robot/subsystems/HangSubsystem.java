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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax HangMotor;

  private SparkClosedLoopController HangController;

  SparkMaxConfig HangMotorConfig = new SparkMaxConfig();
 

  public HangSubsystem() {
    HangMotor = new SparkMax(21, MotorType.kBrushless);
    
    HangController = HangMotor.getClosedLoopController();
    
  
//set IdleMode for Hang
HangMotorConfig.idleMode(IdleMode.kBrake);

HangMotor.configure(HangMotorConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

  }
  public void MoveHanger(double TopPosition,double BottomPosition) {
    if ((HangController.getMAXMotionSetpointPosition() <= TopPosition) ){
    
  }
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
