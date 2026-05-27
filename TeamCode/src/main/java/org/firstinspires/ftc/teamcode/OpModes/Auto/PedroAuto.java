package org.firstinspires.ftc.teamcode.OpModes.Auto; // make sure this aligns with class location
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
import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.commands.Commands.instant;
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
    Command sartIntake = infinite(() ->  intake.firstStageIntake(Intake.Direction.FORWARD));
    Command shootVolleyAuto = instant(() -> intake.shootVolley()) ;
    Command wait = waitMs(1000);
    Command waitToShooterVolly = waitMs(5000);






    private final Pose startPose = new Pose(20.735202492211844, 120.62694704049845, Math.toRadians(144));
    private final Pose scorePose = new Pose(48.28582554517134, 92.42367601246106, Math.toRadians(144));
    private final Pose pickup1Pose = new Pose(24.975443469809424, 77.56643745359676, Math.toRadians(180));
    private final Pose pickup2Pose = new Pose(25.70034589527151, 46.87305161846252, Math.toRadians(180));
    private final Pose endPose = new Pose(60.64779038520044, 109.44435181204182, Math.toRadians(142));

    private PathChain scorePreload, grabPickup1, scorePickup1,grabPickup2,scorePickup2 ,leave;
    public double preTargetVelocity;
    final int CONST_SHOOTER_SPEED = 1350;


    public void buildPaths() {
        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();


        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(69.39348497478007, 76.27270470810682), pickup1Pose))
                .setConstantHeadingInterpolation(pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(pickup1Pose, new Pose(69.39348497478007, 76.27270470810682), scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, new Pose(68.45948292944279, 43.96339563862927), pickup2Pose))
                .setConstantHeadingInterpolation(pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(pickup2Pose, new Pose(42.433104754681196, 70.94512873824465), scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();


        leave = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, endPose))
                .setConstantHeadingInterpolation(endPose.getHeading())
                .build();
    }


    public Command autoRoutine() {
        return sequential(
                follow(follower, scorePreload),
                waitToShooterVolly,
                shootVolleyAuto,
                wait,
                wait,
                wait,
                follow(follower, grabPickup1, true),
                wait,
                wait,
                follow(follower, scorePickup1, true),
                shootVolleyAuto,
                wait,
                wait,
                follow(follower, grabPickup2, true),
                wait,
                wait,
                follow(follower, scorePickup2, true),
                shootVolleyAuto,
                wait,
                wait,
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
        sartIntake.start();
        schedule(autoRoutine());

    }

    @Override
    public void loop() {
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
