// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ReturnOverBump extends Command {

  public final CommandSwerveDrivetrain swerveDrive;
  public final CommandXboxController driverController;

  private final ChassisSpeeds robotSpeeds;
  public final SwerveRequest.ApplyFieldSpeeds request;

  public double angleTarget;

  /** Creates a new ReturnOverBump. */
  public ReturnOverBump(CommandSwerveDrivetrain m_swerve, CommandXboxController m_driverController) {
    // Use addRequirements() here to declare subsystem dependencies.
    swerveDrive = m_swerve;
    driverController = m_driverController;

    robotSpeeds = new ChassisSpeeds(0, 0, 0);
    request = new SwerveRequest.ApplyFieldSpeeds().withSpeeds(robotSpeeds);

    addRequirements(swerveDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    angleTarget = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //if drive back into home zone from neutral zone

    //figure out which angle is closest and turn to it
      if (swerveDrive.getState().Pose.getRotation().getDegrees() < 0){
      angleTarget = -45;
      }
      else angleTarget = 45;

    robotSpeeds.omegaRadiansPerSecond = (angleTarget - swerveDrive.getState().Pose.getRotation().getDegrees())*Constants.DriveConstants.BumpKp;

    if (Math.abs(angleTarget - swerveDrive.getState().Pose.getRotation().getDegrees()) > 5){
      robotSpeeds.vxMetersPerSecond = 0;
      robotSpeeds.vyMetersPerSecond = 0;
    }
    else if (swerveDrive.getState().Pose.getRotation().getDegrees() < 0){
      robotSpeeds.vxMetersPerSecond = Constants.DriveConstants.BumpDriveSpeed;
      robotSpeeds.vyMetersPerSecond = Constants.DriveConstants.BumpDriveSpeed;
    }
    else {
      robotSpeeds.vxMetersPerSecond = Constants.DriveConstants.BumpDriveSpeed;
      robotSpeeds.vyMetersPerSecond = -Constants.DriveConstants.BumpDriveSpeed;
    }

  //send these values to the robot motors
  swerveDrive.setControl(request);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !driverController.getHID().getAButton();
  }
}
