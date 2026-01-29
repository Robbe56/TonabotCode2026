// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.subsystems.ShooterSubsystem;

/** Add your docs here. */
public class DashboardContainer {
     private NetworkTable table;
    private NetworkTableEntry ShooterspeedEntry;
    private NetworkTableEntry batteryEntry;

    NetworkTableInstance inst = NetworkTableInstance.getDefault();

    public void DashboardCreator(){
            table = inst.getTable("SmartDashboard");

        table.getEntry("ShooterSpeed").setDefaultDouble(0.0);
        table.getEntry("HangPosition").setDefaultDouble(0.0);
        
    }
    public void DashboardUpdater(){
        table.getEntry("ShooterSpeed").setDouble(0);
    }


}
