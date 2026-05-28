package org.firstinspires.ftc.teamcode.OpModes.Auto; // make sure this aligns with class location

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

@Autonomous
public class AutonomusFarBlue extends OpMode {

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
    Command shortWait = waitMs(400);

    Command waitToShooterVolly = waitMs(1000);
    Command waitToShooterSpeedUp = waitMs(2800);

    public double preTargetVelocity;
    final int CONST_SHOOTER_SPEED = 1600;

    private final Pose startPose = new Pose(83.99143302180688, 6.897975077881609, Math.toRadians(4));
    private final Pose scorePose = new Pose(79.1, 13.675003727729067, Math.toRadians(65));
    private final Pose pickup1Pose = new Pose(116.61137071651095, 24.53894080996885, Math.toRadians(0));
    private final Pose pickup2Pose = new Pose(125.69710243910922, 8.280366334204876, Math.toRadians(0));
    private final Pose endPose = new Pose(60.64779038520044, 109.44435181204182, Math.toRadians(0));


    private PathChain scorePreload, grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3, scorePickup4;


    public void buildPaths() {
        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();


        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(76.84462616822435, 22.8), pickup1Pose))
                .setConstantHeadingInterpolation(pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup1Pose, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(104.84426773045809, 3.964949522242616), pickup2Pose))
                .setConstantHeadingInterpolation(pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(pickup2Pose, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();


    }


    public Command autoRoutine() {
        return sequential(
                follow(follower, scorePreload),
                waitToShooterSpeedUp,
                shootVolleyAuto,
                waitToShooterVolly,
                wait,
                follow(follower, grabPickup1, true),
                shortWait,
                follow(follower, scorePickup1, true)
        );
    }


    @Override
    public void init() {


        follower = Constants.createFollower(hardwareMap);

        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        drive = new Drive(hardwareMap, 0, false);
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
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
