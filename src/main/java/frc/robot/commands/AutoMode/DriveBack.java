// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoMode;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveBack extends Command {

  public final CommandSwerveDrivetrain swerveDrive;

  private final ChassisSpeeds robotSpeeds;
  public final SwerveRequest.ApplyFieldSpeeds request;
  public final Timer timer;


  /** Creates a new CreepSideways. */
  public DriveBack(CommandSwerveDrivetrain m_swerve) {
    // Use addRequirements() here to declare subsystem dependencies.
    swerveDrive = m_swerve;
    timer = new Timer();


    robotSpeeds = new ChassisSpeeds(0, 0, 0);
    request = new SwerveRequest.ApplyFieldSpeeds().withSpeeds(robotSpeeds);

    addRequirements(swerveDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    robotSpeeds.vxMetersPerSecond = -1.5;
    robotSpeeds.vyMetersPerSecond = 0;
    robotSpeeds.omegaRadiansPerSecond = 0;

  

  //send these values to the robot motors
  swerveDrive.setControl(request);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() > 2.5; //stop after this time
  }
}
