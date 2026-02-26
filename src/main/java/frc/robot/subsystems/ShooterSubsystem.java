// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax ShooterMotor;
  private SparkMax spinnerMotor;
  private SparkMax conveyorMotor;
  private SparkMax turretMotor;

  private SparkClosedLoopController ShooterController;
  private SparkClosedLoopController spinninerController;
  private SparkClosedLoopController conveyorController;
  private SparkClosedLoopController turretController;
  
  RelativeEncoder shooterEncoder;

  SparkMaxConfig ShooterMotorConfig = new SparkMaxConfig();
 

  public ShooterSubsystem() {
    ShooterMotor = new SparkMax(Constants.ShooterConstants.shooterMotorID, MotorType.kBrushless);
    ShooterController = ShooterMotor.getClosedLoopController();

    spinnerMotor = new SparkMax(Constants.ShooterConstants.spinnerMotorID, MotorType.kBrushless);
    spinninerController = spinnerMotor.getClosedLoopController();

    conveyorMotor = new SparkMax(Constants.ShooterConstants.conveyorMotorID, MotorType.kBrushless);
    conveyorController = conveyorMotor.getClosedLoopController();

    turretMotor = new SparkMax(Constants.ShooterConstants.turretMotorID, MotorType.kBrushless);
    turretController = turretMotor.getClosedLoopController();

    shooterEncoder = ShooterMotor.getEncoder();


  
//set PID gains for shooter
ShooterMotorConfig.closedLoop
.p(0.001)
.i(0)
.d(0.0000)
.outputRange(0, 3000);

ShooterMotor.configure(ShooterMotorConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

  }
  public void spinShooter(double ShooterSpeed) {
    ShooterController.setSetpoint(ShooterSpeed, ControlType.kVelocity);
   
  }

    public void FeedBalls(){
    spinnerMotor.set(Constants.ShooterConstants.spinnerSpeed);
    conveyorMotor.set(Constants.ShooterConstants.conveyorSpeed);
  }

  public void Unjam(){
    spinnerMotor.set(-Constants.ShooterConstants.spinnerSpeed);
    conveyorMotor.set(-Constants.ShooterConstants.conveyorSpeed);
  }


  public void stopHopper(){
    spinnerMotor.stopMotor();
  }

  public void stopConveyor(){
    conveyorMotor.stopMotor();
  }

  public void stopShooter(){
    ShooterMotor.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Speed", shooterEncoder.getVelocity());
  }
}
