// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public final class Constants {

    public static final class IntakeConstants{
        public static final double IntakeSpeed = 0.5; //set default Intake Speed
        public static final double PusherSpeed = 0.5; //set default Pusher Speed
    }
    public static final class ShooterConstants{
        public static final double ShooterSpeed = 0.5; //set default shooter Speed
    }
    public static final class HangConstants{
        public static final double HangSpeed = 0.5; //set default shooter Speed
        public static final double ManualHangSpeed = 0.2; //slower speed incase
        
        public static final double bottomheight = 0.0;//min height for hang motor
        public static final int BottomLimit_SwitchIO = 1;// LimitSwitch ID for bottom
    }
}
