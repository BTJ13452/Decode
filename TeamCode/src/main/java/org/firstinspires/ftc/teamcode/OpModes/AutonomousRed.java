package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;


import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

    @Autonomous(group = "Autonomous")

public class AutonomousRed extends LinearOpMode {
    public static final double DELAY = 0.2;

    @Override
    public void runOpMode() {


        new TestShooter();
        new TestIntake();
        new TestParking();
        new TestDrive();

        final Pose2d currentPose = new Pose2d(59, 15, Math.toRadians(160));

        final Pose2d FirstTake = new Pose2d(36, 45, Math.toRadians(90));

        final Pose2d FirstShoot = new Pose2d(59, 15, Math.toRadians(160));

        final Pose2d SecondTake = new Pose2d(12, 45, Math.toRadians(90));

        final Pose2d SecondShoot = new Pose2d(59, 15, Math.toRadians(160));

        final Pose2d ThirdTake = new Pose2d(62, 60, Math.toRadians(90));

        final Pose2d ThirdShoot = new Pose2d(59, 15, Math.toRadians(160));




        MecanumDrive drive = new MecanumDrive(hardwareMap, currentPose);

        //Drive Actions
        Action driveToFirstTake = drive.actionBuilder(currentPose)
                .setTangent(Math.toRadians(140))
                .splineToLinearHeading(FirstTake, Math.toRadians(140))
                .build();

        Action driveToFirstShoot = drive.actionBuilder(FirstTake)
                .setTangent(Math.toRadians(50))
                .splineToLinearHeading(FirstShoot, Math.toRadians(90))
                .build();

        Action driveSecondTake = drive.actionBuilder(FirstShoot)
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(SecondTake, Math.toRadians(90))
                .build();

        Action driveSecondShoot = drive.actionBuilder(SecondTake)
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(SecondShoot, Math.toRadians(90))
                .build();

        Action driveThirdTake = drive.actionBuilder(SecondShoot)
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(ThirdTake, Math.toRadians(90))
                .build();

        Action driveThirdShoot = drive.actionBuilder(ThirdTake)
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(ThirdShoot, Math.toRadians(90))
                .build();

    }
}



