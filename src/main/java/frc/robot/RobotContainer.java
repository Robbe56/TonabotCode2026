// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.FollowPathCommand;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.commands.ManualIntakeCommand;
import frc.robot.commands.ManualShootCommand;
import frc.robot.commands.CreepSideways;
import frc.robot.commands.DriveOverBump;
import frc.robot.commands.ManualHangCommand;

import frc.robot.commands.AutoMode.AutoHang;
import frc.robot.commands.AutoMode.AutoIntake;
import frc.robot.commands.AutoMode.AutoPushIntake;
import frc.robot.commands.AutoMode.AutoShootAll;
import frc.robot.commands.AutoMode.PrepHang;
import frc.robot.commands.AutoMode.SequenceShootBalls;
import frc.robot.commands.AutoMode.Spin;
import frc.robot.commands.ReturnOverBump;
import frc.robot.commands.TurnChassisToHub;
import frc.robot.commands.AutoMode.AutomodeRunIntakeShort;
import frc.robot.commands.AutoMode.AutomodeShootBalls;
import frc.robot.commands.AutoMode.DriveBack;
import frc.robot.commands.ReturnOverBump;

import frc.robot.generated.TunerConstants;

import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.HangSubsystem;

public class RobotContainer {
    private final SlewRateLimiter Xlimit = new SlewRateLimiter(2);
    private final SlewRateLimiter Ylimit = new SlewRateLimiter(2);
    private final SlewRateLimiter Rotlimit = new SlewRateLimiter(4);
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    public static final CommandXboxController driverXbox = new CommandXboxController(0);
    public static final CommandXboxController operatorXbox = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final HangSubsystem hang = new HangSubsystem();

    private final ManualShootCommand manualShoot;
    private final ManualIntakeCommand manualIntake;
    private final ManualHangCommand manualHang;
    private final TurnChassisToHub turnToHub;
    private final DriveOverBump driveOverBump;
    private final ReturnOverBump returnOverBump;
    private final CreepSideways creep;
    private final DriveBack driveBack;
    private final Spin spin;
    private final AutoPushIntake pushintake;
    private final SequenceShootBalls sequenceShoot;
    private final AutoShootAll shootAll;

    private final SequentialCommandGroup driveBackAuto;
    private final ParallelCommandGroup simplebackAuto;


    private final SendableChooser<Command> autoChooser;


    public RobotContainer() {
        //automode pathplanner commands
        NamedCommands.registerCommand("Shoot 8 Balls", new AutomodeShootBalls(shooter));
        NamedCommands.registerCommand("Extend Intake", new AutomodeRunIntakeShort(intake));
        NamedCommands.registerCommand("Run Intake", new AutoIntake(intake));
        NamedCommands.registerCommand("Shoot All Balls", new AutoShootAll(shooter));
          

        //teleop commands
        manualShoot = new ManualShootCommand(shooter, operatorXbox);
        manualIntake = new ManualIntakeCommand(intake, driverXbox,operatorXbox);
        manualHang = new ManualHangCommand(hang, operatorXbox);
        turnToHub = new TurnChassisToHub(drivetrain, shooter, driverXbox);
        driveOverBump = new DriveOverBump(drivetrain, driverXbox);
        returnOverBump = new ReturnOverBump(drivetrain, driverXbox);
        creep = new CreepSideways(drivetrain, driverXbox);
        pushintake = new AutoPushIntake(intake);
        shootAll = new AutoShootAll(shooter);

        //Test Automode commands
        driveBack = new DriveBack(drivetrain);
        spin = new Spin(drivetrain);
        sequenceShoot = new SequenceShootBalls(shooter);
        driveBackAuto = new SequentialCommandGroup(driveBack, spin, shootAll);
        simplebackAuto = new ParallelCommandGroup(pushintake,driveBackAuto);


        NamedCommands.registerCommand("Hang", new AutoHang(hang));
        NamedCommands.registerCommand("PrepareHang", new PrepHang(hang));
        NamedCommands.registerCommand("Shoot", new AutomodeShootBalls(shooter));

        autoChooser = AutoBuilder.buildAutoChooser("Tests");
        SmartDashboard.putData("Auto Mode", autoChooser);

        configureBindings();

        // Warmup PathPlanner to avoid Java pauses
        CommandScheduler.getInstance().schedule(FollowPathCommand.warmupCommand());
        
        
       
        
    }

    


    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX((Xlimit.calculate(driverXbox.getLeftY())) * MaxSpeed * intake.driveFast()) // Drive forward with negative Y (forward)
                    .withVelocityY((Ylimit.calculate(driverXbox.getLeftX())) * MaxSpeed * intake.driveFast()) // Drive left with negative X (left)
                    .withRotationalRate((Rotlimit.calculate(-driverXbox.getRightX())) * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        shooter.setDefaultCommand(manualShoot);
        intake.setDefaultCommand(manualIntake);
        hang.setDefaultCommand(manualHang);
        

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        //joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driverXbox.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driverXbox.getLeftY(), -driverXbox.getLeftX()))
        ));
       // joystick.x().whileTrue(shooter.run(spinShooter()));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        //joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        //joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        //joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        ///joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on start button press.
        driverXbox.start().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        //driverXbox.b().onTrue(turnToHub);
        driverXbox.x().onTrue(creep);
        driverXbox.y().onTrue(driveOverBump);
        driverXbox.a().onTrue(returnOverBump);


        drivetrain.registerTelemetry(logger::telemeterize);
    }
    

    public Command getAutonomousCommand() {
        /* Run the path selected from the auto chooser */
        //return autoChooser.getSelected();
        return simplebackAuto;
    }
}
