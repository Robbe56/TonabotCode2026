// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.HangSubsystem;
import com.revrobotics.spark.SparkBase;
import frc.robot.Constants.HangConstants;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualHangCommand extends Command {

  public final HangSubsystem hang;
  public final CommandXboxController operatorController;

  /** Creates a new ManualShootCommand. */
  public ManualHangCommand(HangSubsystem m_spinHang, CommandXboxController m_operatorController) {
    // Use addRequirements() here to declare subsystem dependencies.
    hang = m_spinHang;
    operatorController = m_operatorController;
    

    addRequirements(hang);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  
  public void execute() {
     if (operatorController.getHID().getLeftBumperButton()){//down
      hang.ManualHang(1);
    }
    else if (operatorController.getHID().getRightBumper()) {//up
      hang.ManualHang(-1);
    }
    else {hang.ManualHang(0);}


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hang.ManualHang(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
