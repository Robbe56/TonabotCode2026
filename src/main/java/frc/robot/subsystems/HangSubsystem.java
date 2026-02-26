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
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants;
import frc.robot.Constants.HangConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  private SparkMax HangMotor;
  private SparkClosedLoopController HangController;

  DigitalInput BottomSwitch;
  RelativeEncoder hangEncoder;
  
  SparkMaxConfig HangMotorConfig = new SparkMaxConfig();
  

  public HangSubsystem() {
    HangMotor = new SparkMax(Constants.HangConstants.hangMotorID, MotorType.kBrushless);
    BottomSwitch = new DigitalInput(HangConstants.BottomLimit_SwitchIO);

    HangController = HangMotor.getClosedLoopController();
    
  
    //set IdleMode for Hang
    HangMotorConfig.idleMode(IdleMode.kBrake);
    HangMotor.configure(HangMotorConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    hangEncoder = HangMotor.getEncoder(); //get encoder value from NEO

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
    }}

  public void ClimberManualControl(double climberCommandSpeed){
     if(climberCommandSpeed > 0 && BottomSwitch.get() == false){ //dont move down if pushing lower limit switch
      HangMotor.stopMotor();
    }
    else if(climberCommandSpeed < 0 && -hangEncoder.getPosition() > Constants.HangConstants.upLimit){//dont move up if encoder says youre at the top
      HangMotor.stopMotor();
    }
    else{
      HangMotor.set(climberCommandSpeed);
    }

    if (BottomSwitch.get() == false){
      HangMotor.getEncoder().setPosition(0); //reset encoder if climber goes to bottom
  }
  }
  public void AutoClimber (double requestSpeed){
    HangMotor.set(requestSpeed);
  }

  public void StopClimber(){
    HangMotor.stopMotor();
  }

  public double GetClimberEncoderPosition(){
    return -hangEncoder.getPosition();
  }

  public boolean GetBottomLimitSwitch(){
    return BottomSwitch.get();
  }

  public boolean ClimberExtended(){
    return hangEncoder.getPosition() > Constants.HangConstants.upLimit;
  }

  public double ClimberCurrent(){
    return HangMotor.getOutputCurrent();
  }
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Bottom Limit Switch", !BottomSwitch.get()); //display if climber is fully retracted
    SmartDashboard.putBoolean("Climber Fully Extended", hangEncoder.getPosition() > Constants.HangConstants.upLimit); //display if climber is fully extended
    SmartDashboard.putNumber("Climber Encoder Value", hangEncoder.getPosition());
    SmartDashboard.putNumber("Climber Current Draw", HangMotor.getOutputCurrent()); 
  }
}

