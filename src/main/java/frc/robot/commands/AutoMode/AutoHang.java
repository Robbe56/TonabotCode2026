// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.HangSubsystem;
import com.revrobotics.spark.SparkBase;
import frc.robot.Constants.HangConstants;

import edu.wpi.first.wpilibj.Timer;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoHang extends Command {

  public final HangSubsystem hang;
  public final Timer timer;
  

  /** Creates a new ManualShootCommand. */
  public AutoHang(HangSubsystem m_spinHang) {
    // Use addRequirements() here to declare subsystem dependencies.
    hang = m_spinHang;
    timer = new Timer();
    
    

    addRequirements(hang);
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
      hang.ManualHang(-1);//up
    }
    else if ((timer.get()<3)) {//down
      hang.ManualHang(1);
    }
    else {hang.ManualHang(0);}


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
    hang.ManualHang(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get()>HangConstants.Hangtime;
  }
}
