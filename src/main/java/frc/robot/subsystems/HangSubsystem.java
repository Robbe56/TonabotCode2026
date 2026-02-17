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

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


import frc.robot.Constants.HangConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax HangMotor;
  DigitalInput BottomSwitch;
  private SparkClosedLoopController HangController;

 

  SparkMaxConfig HangMotorConfig = new SparkMaxConfig();
 

  public HangSubsystem() {
    HangMotor = new SparkMax(22, MotorType.kBrushless);
    BottomSwitch = new DigitalInput(HangConstants.BottomLimit_SwitchIO);
    HangController = HangMotor.getClosedLoopController();
    
  
//set IdleMode for Hang
HangMotorConfig.idleMode(IdleMode.kBrake);

HangMotor.configure(HangMotorConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

  }
  public void MoveHanger(double Position) {
    HangController.setSetpoint(Position, ControlType.kPosition);
  }
  public void ManualHang(double Direction){
    if ((Direction == 1)&& (BottomSwitch.get() == false)){
      HangMotor.stopMotor();
    }
    else {
      HangMotor.set(-HangConstants.HangSpeed*Direction);
    }
    
    
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
