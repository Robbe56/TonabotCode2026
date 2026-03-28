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
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
  private SparkClosedLoopController spinnerController;
  private SparkClosedLoopController conveyorController;
  private SparkClosedLoopController turretController;
  
  RelativeEncoder shooterEncoder;
  RelativeEncoder turrentEncoder;
  RelativeEncoder spinnerEncoder;

  SparkMaxConfig ShooterMotorConfig = new SparkMaxConfig();
  SparkMaxConfig SpinnerConfig = new SparkMaxConfig();
  SparkMaxConfig TurrentConfig = new SparkMaxConfig();

  SlewRateLimiter SpinnerRate;

  public double SpeedAdjustFactor;

  //Limelight
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tid = table.getEntry("tid");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
 

  public ShooterSubsystem() {
    ShooterMotor = new SparkMax(Constants.ShooterConstants.shooterMotorID, MotorType.kBrushless);
    ShooterController = ShooterMotor.getClosedLoopController();

    spinnerMotor = new SparkMax(Constants.ShooterConstants.spinnerMotorID, MotorType.kBrushless);
    spinnerController = spinnerMotor.getClosedLoopController();

    conveyorMotor = new SparkMax(Constants.ShooterConstants.conveyorMotorID, MotorType.kBrushless);
    conveyorController = conveyorMotor.getClosedLoopController();

    turretMotor = new SparkMax(Constants.ShooterConstants.turretMotorID, MotorType.kBrushless);
    turretController = turretMotor.getClosedLoopController();

    shooterEncoder = ShooterMotor.getEncoder(); //get encoder value from NEO
    turrentEncoder = turretMotor.getEncoder(); //get encoder value from NEO
    spinnerEncoder = spinnerMotor.getEncoder(); //get encoder value from NEO

  
//set PID gains for shooter and spinner
ShooterMotorConfig.closedLoop
.p(0.0004)
.i(0.00000)
.d(0.0001)
.outputRange(0, 10000);

SpinnerConfig.closedLoop
.p(0.0001)
.i(0)
.d(.01)
.outputRange(-4000, 4000);

ShooterMotor.configure(ShooterMotorConfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
ShooterMotorConfig.idleMode(IdleMode.kBrake);

spinnerMotor.configure(SpinnerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
SpinnerConfig.closedLoopRampRate(Constants.ShooterConstants.SpinnerRampTime);
SpinnerConfig.idleMode(IdleMode.kBrake);
TurrentConfig.idleMode(IdleMode.kBrake);


SpinnerRate = new SlewRateLimiter(Constants.ShooterConstants.SpinRateLimit);

shooterEncoder.setPosition(0); //initialize shooter encoder at zero when starting

  }

  public void spinShooter(double ShooterSpeed, double SpeedAdjust) {
    ShooterController.setSetpoint(ShooterSpeed*SpeedAdjust, ControlType.kVelocity);
    SpeedAdjustFactor = SpeedAdjust * 100;
  }

    public void FeedBalls(){
    //spinnerMotor.set(SpinnerRate.calculate(Constants.ShooterConstants.spinnerSpeed));
    spinnerController.setSetpoint(Constants.ShooterConstants.SpinnerVelocity, ControlType.kVelocity);
    conveyorMotor.set(Constants.ShooterConstants.conveyorSpeed);
  }

  public void Unjam(){
    //spinnerMotor.set(SpinnerRate.calculate(Constants.ShooterConstants.spinnerSpeed));
    spinnerController.setSetpoint(-Constants.ShooterConstants.SpinnerVelocity, ControlType.kVelocity);
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

  public void stopTurret(){
    turretMotor.stopMotor();
  }

  public void spinTurret(double turretCommandSpeed){
    if (turretCommandSpeed < 0 && turrentEncoder.getPosition() < Constants.ShooterConstants.turretEnd2){
      turretMotor.stopMotor();
      //turretController.setSetpoint(-turrentEncoder.getPosition()-431, ControlType.kPosition);
    }
    else if (turretCommandSpeed > 0 && turrentEncoder.getPosition() > Constants.ShooterConstants.turretEnd){
      turretMotor.stopMotor();
    }
    else if(turretCommandSpeed < 0.04 && turretCommandSpeed > -0.04){
      turretMotor.stopMotor();
    }
    else turretMotor.set(turretCommandSpeed);
  }

  public void ResetTurretEncoder(){
    turretMotor.getEncoder().setPosition(0); //reset turret encoder if correct buttons are pressed
  }

  public double TrackHubX(){
    return tx.getDouble(0);
  }

  public double TrackHubY(){
    return ty.getDouble(0);
  }

  public double TrackHubTagArea(){
    return ta.getDouble(0);
  }

  public double HubTagID(){
    return tid.getDouble(0);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Speed", shooterEncoder.getVelocity());
    SmartDashboard.putNumber("Turret Encoder", turrentEncoder.getPosition());
    SmartDashboard.putNumber("Spinner Plate Speed", spinnerEncoder.getVelocity());

    SmartDashboard.putNumber("Shooter Speed Values", SpeedAdjustFactor);

    SmartDashboard.putNumber("Hub Tag X Value", tx.getDouble(0));
    SmartDashboard.putNumber("Hub Tag Y Value", ty.getDouble(0));
    SmartDashboard.putNumber("Hub Tag Area", ta.getDouble(0));
    SmartDashboard.putNumber("Hub Tag ID Number", tid.getDouble(0));
    
  }
}
