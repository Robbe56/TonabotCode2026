// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ShooterSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualShootCommand extends Command {

  public final ShooterSubsystem shooter;
  public final CommandXboxController operatorController;
  public double shotSpeedPercentage;
  public boolean isPressed;

  /** Creates a new ManualShootCommand. */
  public ManualShootCommand(ShooterSubsystem m_shooter, CommandXboxController m_operatorController) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = m_shooter;
    operatorController = m_operatorController;
    

    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shotSpeedPercentage = 1;
    isPressed = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     if (operatorController.getHID().getLeftBumperButton()){
      //shooter.spinShooter(4500);
      if (operatorController.getHID().getRightStickButton()) {
      shooter.spinShooter(10000, shotSpeedPercentage);
      }
      if (shooter.HubTagID() != -1){ //if limelight sees a valid tag, spin at correct speed
        if (shooter.TrackHubY() > Constants.ShooterConstants.SwapShootSlopeY){ //clsoer to hub, use slower speed equation
        shooter.spinShooter(Constants.ShooterConstants.ShootIntercept + shooter.TrackHubY()*Constants.ShooterConstants.ShootSlope, shotSpeedPercentage);
        }
      else{ //if further away from hub, use higher speed equation
        shooter.spinShooter(Constants.ShooterConstants.LongShootIntercept + shooter.TrackHubY()*Constants.ShooterConstants.LongShootSlope, shotSpeedPercentage);
        }
      }
      else shooter.spinShooter(6000, shotSpeedPercentage); //spin at high speed if no tags are seen
      }
    else shooter.stopShooter(); //if button not pressed

    if (operatorController.getHID().getRightBumperButton()){
      shooter.FeedBalls();
    }
    else if (operatorController.getHID().getBButton()){
      shooter.Unjam();
    }
    else {
      shooter.stopConveyor();
      shooter.stopHopper();
    }

  //turret commands
  if (operatorController.getHID().getXButton()){
    shotSpeedPercentage = 1;
    if (shooter.HubTagID() == -1){
      shooter.stopTurret();
    }
    else {
      shooter.spinTurret((shooter.TrackHubX() - 0.2)*Constants.ShooterConstants.turretKp);
    }
  }
  else shooter.spinTurret(Constants.ShooterConstants.SlowTurret*operatorController.getLeftX()); //manually control turret with left joystick

  if (operatorController.getHID().getStartButton() && operatorController.getHID().getBackButton()){ //if pressing both start and back at the same time reset turret encoder
    shooter.ResetTurretEncoder();

  }

  if (operatorController.getHID().getStartButtonPressed()){
    shotSpeedPercentage = shotSpeedPercentage + 0.025;
  }

  if (operatorController.getHID().getBackButtonPressed()){
    shotSpeedPercentage = shotSpeedPercentage - 0.025;
  }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}