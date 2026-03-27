// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutomodeShootBalls extends Command {
  /** Creates a new AutomodeShootBalls. */

  public final ShooterSubsystem shooter;
  public final Timer timer;

  public AutomodeShootBalls(ShooterSubsystem m_shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = m_shooter;
    timer = new Timer();

    addRequirements(shooter);
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
    shooter.spinShooter(Constants.ShooterConstants.ShootIntercept + shooter.TrackHubY()*Constants.ShooterConstants.ShootSlope,1); //run flywheel at correct speed
    shooter.spinTurret(shooter.TrackHubX()*Constants.ShooterConstants.turretKp); //track hub with turret
  
  if (timer.get() < Constants.AutoConstants.shooterDelay){
    shooter.stopConveyor();
    shooter.stopHopper();
  }
  else {
    shooter.FeedBalls();
  }
  
   }
  
  
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
    shooter.stopConveyor();
    shooter.stopHopper();
    shooter.stopTurret();
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() > Constants.AutoConstants.doneShooting;
  }
}

