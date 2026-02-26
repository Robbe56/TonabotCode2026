// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.HangSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualHangCommand extends Command {

  public final HangSubsystem hanger;
  public final CommandXboxController operatorController;

  /** Creates a new ManualShootCommand. */
  public ManualHangCommand(HangSubsystem m_hanger, CommandXboxController m_operatorController) {
    // Use addRequirements() here to declare subsystem dependencies.
    hanger = m_hanger;
    operatorController = m_operatorController;
    

    addRequirements(hanger);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  
  public void execute() {
     if (operatorController.getHID().getAButton()){//down
      hanger.ClimberManualControl(Constants.HangConstants.HangSpeed);
    }

    else if (operatorController.getHID().getYButton()) {//up
    hanger.ClimberManualControl(-Constants.HangConstants.HangSpeed);
    }

    else hanger.StopClimber();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  hanger.StopClimber();;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
