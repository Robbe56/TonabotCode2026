// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.IntakeSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualIntakeCommand extends Command {

  public final IntakeSubsystem intake;
  public final CommandXboxController driverController;

  /** Creates a new ManualShootCommand. */
  public ManualIntakeCommand(IntakeSubsystem m_intake, CommandXboxController m_driverController) {
    // Use addRequirements() here to declare subsystem dependencies.
    intake = m_intake;
    driverController = m_driverController;
    

    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     if (driverController.getHID().getLeftBumperButton()){
      intake.intakeActive();
     }
     else if (driverController.getHID().getRightBumperButton()){
      intake.spitOut();
     }
     else intake.intakeRest();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.intakeRest();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
