// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Automode;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;



import edu.wpi.first.wpilibj2.command.Command;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoHang extends Command {
  /** Creates a new AutoHang. */
  public final HangSubsystem hanger;
  public final Timer timer;
  public AutoHang(HangSubsystem m_hanger) {
    // Use addRequirements() here to declare subsystem dependencies.
    hanger = m_hanger;
    addRequirements(hanger);
     timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.get() <1){//up
      hanger.ClimberManualControl(Constants.HangConstants.HangSpeed);
    }

    else if (timer.get()>3) {//down
    hanger.ClimberManualControl(-Constants.HangConstants.HangSpeed);
    }

    else hanger.StopClimber();
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hanger.StopClimber();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get()<4;
  }
}
