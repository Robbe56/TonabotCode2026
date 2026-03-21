// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoPushIntake extends Command {
  /** Creates a new AutoPushIntake. */
  public final IntakeSubsystem intake;
  public final Timer timer;
  
  public AutoPushIntake(IntakeSubsystem m_intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    intake = m_intake;
    timer = new Timer();
    addRequirements(intake);
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
    intake.intakeActive();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    intake.intakeRest();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (timer.get()>1);
  }
}
