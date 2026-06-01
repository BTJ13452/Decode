package org.firstinspires.ftc.teamcode.OpModes.Auto; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
    Command wait = waitMs(500);
    Command shortWait = waitMs(50);

    Command waitToShooterVolly = waitMs(700);
    Command waitToShooterSpeedUp = waitMs(5200);

    public double preTargetVelocity;
    final int CONST_SHOOTER_SPEED = 1550;

    private final Pose startPose = new Pose(86.41588785046733, 7.9999999999999, Math.toRadians(90));
    private final Pose scorePose = new Pose(85.9816199376947, 13.26121495327102, Math.toRadians(55));
    private final Pose pickup1Pose = new Pose(145, 45.982825805423566, Math.toRadians(0));
    private final Pose pickup2Pose = new Pose(142, 16, Math.toRadians(0));
    private final Pose pickup3Pose = new Pose(142, 23, Math.toRadians(0));
    private final Pose pickup3BackPose = new Pose(130, 23, Math.toRadians(0));

    private final Pose wigle1 = new Pose(143, 16, Math.toRadians(10));
    private final Pose wigle2 = new Pose(143, 16, Math.toRadians(-10));

    private final Pose endPose = new Pose(86.5015541874567, 27.072348836558582, Math.toRadians(90));


    private PathChain scorePreload, grabPickup1, scorePickup1, grabPickup2, scorePickup2, wigleToIntakeBall1, wigleToIntakeBall2, backGrabPickup3, grabPickup3, scorePickup3, scorePickup4, park;


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
                .addPath(new BezierLine(pickup1Pose, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(129.3777258566978, 29.313862928348907), pickup2Pose))
                .setConstantHeadingInterpolation(pickup2Pose.getHeading())
                .build();
        wigleToIntakeBall1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup2Pose, wigle1))
                .setConstantHeadingInterpolation(wigle1.getHeading())
                .build();
        wigleToIntakeBall2 = follower.pathBuilder()
                .addPath(new BezierLine(pickup2Pose, wigle2))
                .setConstantHeadingInterpolation(wigle2.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(wigle2, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, pickup3Pose))
                .setConstantHeadingInterpolation(pickup3Pose.getHeading())
                .build();
        backGrabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(pickup3Pose, pickup3BackPose))
                .setConstantHeadingInterpolation(pickup3BackPose.getHeading())
                .build();

        park = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, endPose))
                .setConstantHeadingInterpolation(endPose.getHeading())
                .build();


    }


    public Command autoRoutine() {
        return sequential(
                follow(follower, scorePreload),
                waitToShooterSpeedUp,
                shootVolleyAuto,
                waitToShooterVolly,

                follow(follower, grabPickup1, true),
                shortWait,
                follow(follower, scorePickup1, true),
                shootVolleyAuto,
                waitToShooterVolly,

                follow(follower, grabPickup2, true),
                follow(follower, wigleToIntakeBall1, true),
                shortWait,
                follow(follower, wigleToIntakeBall2, true),
                shortWait,
                follow(follower, scorePickup2, true),
                shootVolleyAuto,
                waitToShooterVolly,

                follow(follower, grabPickup3, true),
                wait,
                follow(follower, backGrabPickup3, true),
                shortWait,
                follow(follower, scorePickup3, true),
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

        //These will run when the OpMode is initiated
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

        // Feedback to Driver Hub for debugging
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.update();
    }
}
