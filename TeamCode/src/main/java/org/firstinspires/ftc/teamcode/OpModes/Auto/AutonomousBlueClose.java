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
public class AutonomousBlueClose extends OpMode {

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


    private final Pose startPose = new Pose(20.735202492211844, 120.62694704049845, Math.toRadians(142));
    private final Pose scorePose = new Pose(48.28582554517134, 92.42367601246106, Math.toRadians(138));
    private final Pose pickup1Pose = new Pose(22.550988641148997, 75.36238760936001, Math.toRadians(180));
    private final Pose pickup2Pose = new Pose(18.4, 48.3551401869159, Math.toRadians(180));
    private final Pose intakeFromGate = new Pose(17, 54.00000000000001, Math.toRadians(165));
    private final Pose endPose = new Pose(60.64779038520044, 109.44435181204182, Math.toRadians(160));


    private PathChain scorePreload, grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3, scorePickup4;
    public double preTargetVelocity;
    final int CONST_SHOOTER_SPEED = 1300;


    public void buildPaths() {
        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();


        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(52.20189618973337, 74.06865486387001), pickup1Pose))
                .setConstantHeadingInterpolation(pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup1Pose, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(62.0677383811562, 46.4467589313804), pickup2Pose))
                .setConstantHeadingInterpolation(pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(pickup2Pose,new Pose(47.68851229921899,59.0369838498961), scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(47.23566315723767, 59.1180433125762), intakeFromGate))
                .setConstantHeadingInterpolation(intakeFromGate.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(intakeFromGate, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
        scorePickup4 = follower.pathBuilder()
                .addPath(new BezierLine(intakeFromGate, endPose))
                .setConstantHeadingInterpolation(endPose.getHeading())
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
                follow(follower, scorePickup1, true),
                shootVolleyAuto,
                waitToShooterVolly,
                follow(follower, grabPickup2, true),
                shortWait,
                follow(follower, scorePickup2, true),
                shootVolleyAuto,
                waitToShooterVolly,
                follow(follower, grabPickup3, true),
                waitToShooterVolly,
                wait,
                shortWait,
                follow(follower, scorePickup3, true),
                shootVolleyAuto,
                waitToShooterVolly,
                follow(follower, grabPickup3, true),
                wait,
                shortWait,
                follow(follower, scorePickup4, true),
                shootVolleyAuto,
                waitToShooterVolly
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
