// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ShooterSubsystem;
import com.revrobotics.spark.SparkBase;
import frc.robot.Constants.HangConstants;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualTurnTurret extends Command {

  public final ShooterSubsystem shoot;
  public final CommandXboxController operatorController;

  /** Creates a new ManualShootCommand. */
  public ManualTurnTurret(ShooterSubsystem m_spinTurret, CommandXboxController m_operatorController) {
    // Use addRequirements() here to declare subsystem dependencies.
    shoot = m_spinTurret;
    operatorController = m_operatorController;
    

    addRequirements(shoot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  
  public void execute() {
     shoot.spinTurret(operatorController.getRightX());

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
