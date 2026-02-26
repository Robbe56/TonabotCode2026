// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public final class Constants {

    public static final class IntakeConstants{
         public static final int intakeMotorID = 23;
        public static final double IntakeSpeed = 0.5; //set default Intake Speed
        public static final double PusherSpeed = 0.5; //set default Pusher Speed
    }
    public static final class ShooterConstants{
        public static final int spinnerMotorID = 24;
        public static final int conveyorMotorID = 25;
        public static final int turretMotorID = 26;
        public static final int shooterMotorID = 27;
        public static final double spinnerSpeed = -0.3; //speed for spinner plate with holes
        public static final double conveyorSpeed = -0.9; //vertical conveyor speed
        public static final double ShooterSpeed = 0.5; //shooting Speed
        public static final double Turnspeed = 0.4; ///Set rotation speed of turret
        
    }
    public static final class HangConstants{
        public static final int hangMotorID = 22;
        public static final double HangSpeed = 1; //set default climber retract Speed
        public static final double ManualHangSpeed = 0.2; //slower speed incase
        public static final double upLimit = 135; //encoder value when hook is at 30 inches
        
        public static final double bottomheight = 0.0;//min height for hang motor
        public static final int BottomLimit_SwitchIO = 1;// LimitSwitch ID for bottom

        //Automode Constants
        public static final int Hangtime = 6;
    }
    public static final class LimelightConstants{
    public static final double hub_tag_height = 10;
    }
}
