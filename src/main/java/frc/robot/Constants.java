// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


/** Add your docs here. */
public final class Constants {

    public static final class DriveConstants{
        public static final double driveSlowFactor = .5;
        public static final double maxDriveSpeed = 5.12; //use this for max speed in m/s
        public static final double BumpKp = 0.09;
        public static final double BumpDriveSpeed = 2.4;
        public static final double HubSpinKp = 0.1;
        public static final double CreepSpeed = -0.2;
    
    }
        
    public static final class IntakeConstants{
        public static final int intakeMotorID = 23;
        public static final double IntakeSpeed = .8; //set default Intake Speed
        public static final double PusherSpeed = 0.5; //set default Pusher Speed
    }
    public static final class ShooterConstants{
        public static final int spinnerMotorID = 24;
        public static final int conveyorMotorID = 25;
        public static final int turretMotorID = 26;
        public static final int shooterMotorID = 27;
        public static final double spinnerSpeed = -0.4; //speed for spinner plate with holes
        public static final double SpinRateLimit = .5; //set ramp rate for spinner wheel
        public static final double conveyorSpeed = -1; //vertical conveyor speed
        public static final double ShooterSpeed = 0.5; //set default shooter Speed
        
        public static final double ShootSlope = -90; //slope for shooter speed curve
        public static final double ShootIntercept = 3450; //y-intercept for shooter speed curve

        public static final double LongShootSlope = -90; //slope for shooter speed curve from far
        public static final double LongShootIntercept = 4000; //y-intercept for shooter speed curve from far

        public static final double SwapShootSlopeY = -16; //Max Hub Y before swapping to formula 2

        public static final double turretEnd = 50; //limit for how far turret can rotate
        public static final double SlowTurret = 0.2;
        public static final double turretKp = 0.03;
    }
    public static final class HangConstants{
        public static final int hangMotorID = 22;
        public static final double HangSpeed = -1; //set default climber retract Speed
        public static final double ManualHangSpeed = 0.2; //slower speed incase
        public static final double upLimit = 85; //encoder value when hook is at 30 inches
        
        public static final double bottomheight = 0.0;//min height for hang motor
        public static final int BottomLimit_SwitchIO = 1;// LimitSwitch ID for bottom
    }
    public static final class AutoConstants{
        public static final double autoShooterSpeed = 1800;
        public static final double shooterDelay = 2;
        public static final double doneShooting = 15;
        
        public static final double intakeExtended = 1; //how long to run into to get it out of robot
        public static final double runIntake = 4; //run intake when getting balls
    
    }
}
