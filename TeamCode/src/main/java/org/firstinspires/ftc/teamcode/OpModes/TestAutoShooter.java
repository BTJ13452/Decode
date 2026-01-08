package org.firstinspires.ftc.teamcode.OpModes;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp

public class TestAutoShooter extends OpMode {

    private AprilTagProcessor aprilTag;

    VoltageSensor voltageSensor;

    final double delta = 0.05;

    final double alfa = 0.01;

    double motorPower = 0.5;

    private DcMotor shooter;

    boolean yIsPressed = false;

    boolean aIsPressed = false;

    boolean bIsPressed = false;

    boolean xIsPressed = false;

    @Override
    public void init() {
        shooter = hardwareMap.dcMotor.get("Shooter");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        voltageSensor = hardwareMap.voltageSensor.iterator().next();

        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Choose a name"))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(aprilTag)
                .build();
    }

    @Override
    public void loop() {
        double adapter = 0;
        if (voltageSensor.getVoltage() > 13) {
            adapter = -0.065;

        } else if (voltageSensor.getVoltage() > 12.5) {
            adapter = -0.055;
        } else if (voltageSensor.getVoltage() > 12) {
            adapter = -0.04;
        } else if (voltageSensor.getVoltage() > 11) {
            adapter = 0.02;
        }
        telemetry.addData("volt", voltageSensor.getVoltage());

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == 24) {
                telemetry.addData("distance", detection.ftcPose.y);
                shooter.setPower(p(detection.ftcPose.y) + adapter);
            }
        }

        telemetry.addData("power", shooter.getPower());
        telemetry.update();
    }

    static final double a = 2.549790517774111e-6;
    static final double b = -0.002401417848971357;
    static final double c = 0.7517715960347462;
    static final double d = -77.29541900481152;

      // Evaluate polynomial using Horner's method
    public static double p(double x) {
        return ((a * x + b) * x + c) * x + d;
    }
}
