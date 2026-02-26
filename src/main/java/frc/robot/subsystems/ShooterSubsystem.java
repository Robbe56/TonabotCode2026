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

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax ShooterMotor;
  private SparkMax StorageMotor;
  private SparkMax TurretMotor;
  private VictorSP FeedMotor;
  private SparkClosedLoopController ShooterController;
  

  SparkMaxConfig ShooterMotorConfig = new SparkMaxConfig();
 

  public ShooterSubsystem() {
    ShooterMotor = new SparkMax(21, MotorType.kBrushless);
      StorageMotor = new SparkMax(24, MotorType.kBrushless);
    TurretMotor = new SparkMax(25, MotorType.kBrushless);
    FeedMotor = new VictorSP(26);
    ShooterController = ShooterMotor.getClosedLoopController();
  
//set PID gains for shooter
ShooterMotorConfig.closedLoop
.p(0.001)
.i(0)
.d(0.0000)
.outputRange(0, 3000);

ShooterMotor.configure(ShooterMotorConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

  }
  public void spinShooter(double ShooterSpeed) {
    //ShooterController.setSetpoint(ShooterSpeed, ControlType.kVelocity);
    StorageMotor.set(IntakeConstants.PusherSpeed);
    FeedMotor.set(IntakeConstants.PusherSpeed);
   
  }
  public void spinTurret(double Direction){
    TurretMotor.set(Direction*ShooterConstants.Turnspeed);
  }
  public void reverseFeed(){
    StorageMotor.set(-IntakeConstants.PusherSpeed);
    FeedMotor.set(-IntakeConstants.PusherSpeed);
  }
  
  public void stopFeed(){
    StorageMotor.stopMotor();
    FeedMotor.stopMotor();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
