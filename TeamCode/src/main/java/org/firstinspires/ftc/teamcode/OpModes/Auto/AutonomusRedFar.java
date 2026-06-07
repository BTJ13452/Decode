package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import static com.pedropathing.ivy.Scheduler.*;
import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.pedro.PedroCommands.*;
import static com.pedropathing.ivy.groups.Groups.*;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Parking;
import org.firstinspires.ftc.teamcode.Systems.RGBController;
import org.firstinspires.ftc.teamcode.Systems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import static com.pedropathing.ivy.commands.Commands.branch;

@Autonomous
public class AutonomusRedFar extends OpMode {

    VoltageSensor voltageSensor;
    Drive drive;
    Intake intake;
    Shooter shooter;
    Parking parking;
    RGBController LEDs;
    Limelight3A limelight;
    Command startIntake = infinite(() -> intake.firstStageIntake(Intake.Direction.FORWARD));
    Command shootVolleyAuto = instant(() -> intake.shootVolley());
    Command wait = waitMs(600);
    Command shortWait = waitMs(50);
    Command pickup1Timeout = waitMs(3500);
    Command pickup2Timeout = waitMs(6000);
    Command pickup3Timeout = waitMs(4000);
    Command waitToShooterVolly = waitMs(1000);
    Command waitToShooterSpeedUp = waitMs(5500);

    public double preTargetVelocity;
    final int CONST_SHOOTER_SPEED = 1520;

    private final Pose startPose = new Pose(86, 8.1, Math.toRadians(90));
    private final Pose scorePose = new Pose(88, 15, Math.toRadians(53));
    private final Pose score1Pose = new Pose(88, 20, Math.toRadians(65));
    private final Pose score2Pose = new Pose(88, 20, Math.toRadians(61));
    private final Pose score3Pose = new Pose(88, 23, Math.toRadians(67));
    private final Pose pickup1Pose = new Pose(145, 46, Math.toRadians(0));
    private final Pose pickup2Pose = new Pose(144, 15, Math.toRadians(-10));

    private final Pose pickup3Pose = new Pose(144, 18, Math.toRadians(0));
    private final Pose pickup3BackPose = new Pose(130, 18, Math.toRadians(0));

    private final Pose endPose = new Pose(86.5015541874567, 27.072348836558582, Math.toRadians(90));

    private PathChain scorePreload, grabPickup1, scorePickup1, grabPickup2, scorePickup2, GoBackForPickup2, grabPickup3, grabPickup4, BackGrabPickup3, scorePickup3, scorePickup4, park;

    // משתנה עבור נתיב החילוץ הדינמי ל-Score

    public void buildPaths() {
        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(71.05557598958288, 40.16213728597186), pickup1Pose))
                .setConstantHeadingInterpolation(pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup1Pose, score1Pose))
                .setConstantHeadingInterpolation(score1Pose.getHeading())
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(score1Pose, new Pose(71.05557598958288, 40.16213728597186), pickup2Pose))
                .setConstantHeadingInterpolation(pickup2Pose.getHeading())
                .build();



        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(pickup2Pose,score2Pose))
                .setConstantHeadingInterpolation(score2Pose.getHeading())
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(score3Pose, pickup3Pose))
                .setConstantHeadingInterpolation(pickup3Pose.getHeading())
                .build();

        BackGrabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(pickup3Pose, pickup3BackPose))
                .setConstantHeadingInterpolation(pickup3BackPose.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(pickup3BackPose, scorePose))
                .setConstantHeadingInterpolation(score3Pose.getHeading())
                .build();

        park = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, endPose))
                .setConstantHeadingInterpolation(endPose.getHeading())
                .build();
    }

    public Command autoRoutine() {
        return sequential(
                follow(follower, scorePreload, true),
                waitToShooterSpeedUp,
                shootVolleyAuto,
                waitToShooterVolly,
                race(
                        sequential(
                                follow(follower, grabPickup1, true)
                        ),
                        pickup1Timeout
                ),

                follow(follower, scorePickup1, true),
                shootVolleyAuto,
                waitToShooterVolly,

                race(
                        sequential(
                                follow(follower, grabPickup2, true)
                        ),
                        pickup2Timeout
                ),



                follow(follower, scorePickup2, true),
                shootVolleyAuto,
                waitToShooterVolly,

                race(
                        sequential(
                                follow(follower, grabPickup3, true),
                                follow(follower, BackGrabPickup3, true),
                                follow(follower, grabPickup3, true),

                                follow(follower, scorePickup3, true),
                                shootVolleyAuto,
                                waitToShooterVolly
                        ),
                        pickup3Timeout
                ),

                race(
                        sequential(
                                follow(follower, grabPickup3, true),
                                follow(follower, BackGrabPickup3, true),
                                follow(follower, grabPickup3, true),
                                shortWait,
                                follow(follower, scorePickup3, true),
                                shootVolleyAuto,
                                waitToShooterVolly
                        ),
                        pickup3Timeout
                ),
                shootVolleyAuto,
                waitToShooterVolly,

                follow(follower, park, true)
        );
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        parking = new Parking(hardwareMap);
        LEDs = new RGBController(hardwareMap);

        shooter.runByPidf();
        preTargetVelocity = 1300;

        limelight.pipelineSwitch(1);
        limelight.start();

        Scheduler.reset();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }

    public void start() {
        schedule(autoRoutine());
        startIntake.start();
    }

    @Override
    public void loop() {
        startIntake.start();
        shooter.runByPidf();
        shooter.setVelocity(CONST_SHOOTER_SPEED);

        follower.update();
        Scheduler.execute();

        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.update();
    }
}