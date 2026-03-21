// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoMode;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.RobotContainer;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoTravelOverBump extends Command {
  /** Creates a new AutoTravelOverBump. */
  public final CommandSwerveDrivetrain swerveDrive;
  public final Timer timer;
   public double angleTarget;

     private final ChassisSpeeds robotSpeeds;
  public final SwerveRequest.ApplyFieldSpeeds request;

  public AutoTravelOverBump(CommandSwerveDrivetrain m_swervedrive) {
    // Use addRequirements() here to declare subsystem dependencies.
    swerveDrive = m_swervedrive;
    timer = new Timer();

     robotSpeeds = new ChassisSpeeds(0, 0, 0);
    request = new SwerveRequest.ApplyFieldSpeeds().withSpeeds(robotSpeeds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    angleTarget = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (DriverStation.getAlliance().get() == Alliance.Red){
      if (swerveDrive.getState().Pose.getRotation().getDegrees() < 0){
      angleTarget = -165;
      }
      else angleTarget = 165;

    robotSpeeds.omegaRadiansPerSecond = (angleTarget - swerveDrive.getState().Pose.getRotation().getDegrees())*Constants.DriveConstants.BumpKp;

    if (Math.abs(angleTarget - swerveDrive.getState().Pose.getRotation().getDegrees()) > 5){
      robotSpeeds.vxMetersPerSecond = 0;
      robotSpeeds.vyMetersPerSecond = 0;
    }
    else if (swerveDrive.getState().Pose.getRotation().getDegrees() < 0){
      robotSpeeds.vxMetersPerSecond = Constants.DriveConstants.BumpDriveSpeed;
      robotSpeeds.vyMetersPerSecond = 0;
    }
    else {
      robotSpeeds.vxMetersPerSecond = Constants.DriveConstants.BumpDriveSpeed;
      robotSpeeds.vyMetersPerSecond = 0;
    }
  }
  //Now if Blue alliance since straight forward is a heading of zero degrees
  else{
      if (swerveDrive.getState().Pose.getRotation().getDegrees() < 0){
      angleTarget = -15;
      }
      else angleTarget = 15;

    robotSpeeds.omegaRadiansPerSecond = (angleTarget - swerveDrive.getState().Pose.getRotation().getDegrees())*Constants.DriveConstants.BumpKp;

    if (Math.abs(angleTarget - swerveDrive.getState().Pose.getRotation().getDegrees()) > 5){
      robotSpeeds.vxMetersPerSecond = 0;
      robotSpeeds.vyMetersPerSecond = 0;
    }
    else if (swerveDrive.getState().Pose.getRotation().getDegrees() < 0){
      robotSpeeds.vxMetersPerSecond = -Constants.DriveConstants.BumpDriveSpeed;
      robotSpeeds.vyMetersPerSecond = 0;
    }
    else {
      robotSpeeds.vxMetersPerSecond = -Constants.DriveConstants.BumpDriveSpeed;
      robotSpeeds.vyMetersPerSecond = 0;
    }
  }
   swerveDrive.setControl(request);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    robotSpeeds.vxMetersPerSecond = 0;
    robotSpeeds.vyMetersPerSecond = 0;
    robotSpeeds.omegaRadiansPerSecond = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get()>3;
  }
}
