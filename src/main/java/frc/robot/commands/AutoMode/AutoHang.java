// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.Constants;



/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoHang extends Command {

  public final HangSubsystem hanger;
  public final Timer timer;
  

  /** Creates a new ManualShootCommand. */
  public AutoHang(HangSubsystem m_hang) {
    // Use addRequirements() here to declare subsystem dependencies.
    hanger = m_hang;
    timer = new Timer();
    
    

    addRequirements(hanger);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  
  public void execute() {
     if (timer.get() <1){//else
      hanger.AutoClimber(-1);//up
    }
    else if ((timer.get()<3)) {//down
      hanger.AutoClimber(1);
    }
    else {hanger.AutoClimber(0);}


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
    hanger.AutoClimber(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get()>Constants.HangConstants.Hangtime;
  }
}
