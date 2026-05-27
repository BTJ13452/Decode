package org.firstinspires.ftc.teamcode; // make sure this aligns with class location
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import static com.pedropathing.ivy.Scheduler.*;
import static com.pedropathing.ivy.commands.Commands.onInterrupt;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.pedro.PedroCommands.*;
import static com.pedropathing.ivy.groups.Groups.*;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import org.firstinspires.ftc.teamcode.Systems.AutoAline;
import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Parking;
import org.firstinspires.ftc.teamcode.Systems.RGBController;
import org.firstinspires.ftc.teamcode.Systems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous
public class PedroAuto extends OpMode {

    VoltageSensor voltageSensor;
    Drive drive;
    Intake intake;
    Shooter shooter;
    Parking parking;
    RGBController LEDs;
    AutoAline autoAline;
    LLResult llResult;
    Limelight3A limelight;
    Command shootVolleyAuto = onInterrupt(() -> intake.shootVolley());




    private final Pose startPose = new Pose(20.718068535825545, 121.66355140186916, Math.toRadians(142));
    private final Pose scorePose = new Pose(53.778816199376934, 88.16199376947041, Math.toRadians(142));
    private final Pose pickup1Pose = new Pose(24.024143302180686, 83.04080996884736, Math.toRadians(180));
    private final Pose endPose = new Pose(62.22340479420247, 100.26600752051756);

    private PathChain scorePreload, grabPickup1, scorePickup1, leave;

    public void buildPaths() {
        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();


        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, pickup1Pose))
                .addPath(new BezierCurve(scorePose, new Pose(18.954828660436135, 84.72936137071652), pickup1Pose))

                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup1Pose, scorePose))
                .addPath(new BezierCurve(pickup1Pose, new Pose(41.069025510355154, 82.12202558740627), scorePose))
                .build();

        leave = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, endPose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
    }

    public double preTargetVelocity;
    final int CONST_SHOOTER_SPEED = 1300;


    public Command autoRoutine() {
        return sequential(
                follow(follower, scorePreload),
                follow(follower, grabPickup1, true),
                shootVolleyAuto,
                follow(follower, scorePickup1, true),
                follow(follower, leave, true)
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
        shooter.runByPidf();
        shooter.setVelocity(CONST_SHOOTER_SPEED);
        intake.firstStageIntake(Intake.Direction.FORWARD);

        follower.update();
        follower.update();
        Scheduler.execute();

        // Feedback to Driver Hub for debugging
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
