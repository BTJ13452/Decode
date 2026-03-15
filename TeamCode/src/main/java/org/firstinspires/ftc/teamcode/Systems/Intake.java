package org.firstinspires.ftc.teamcode.Systems;

import static android.os.SystemClock.sleep;

import android.util.Size;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

import kotlin.jvm.Synchronized;

public class Intake {




    public enum Direction {
        FORWARD,
        REVERSE,
        STOP;

        public int getValue() {
            switch (this) {
                case FORWARD:
                    return 1;
                case REVERSE:
                    return -1;
                default:
                    return 0;
            }
        }
    }

    public enum Cell {
        RIGHT,
        LEFT;
    }

    final double IN_POWER = 1;
    final int WAIT_BETWEEN_FIRST_BALL = 200;
    final int WAIT_BETWEEN_SECOND_BALL = 300;
    final int WAIT_BETWEEN_THIRD_BALL = 500;
    final int WAIT_BETWEEN_FIRST_BALL_MID = 800;
    final int WAIT_BETWEEN_SECOND_BALL_MID = 1200;
    final int WAIT_BETWEEN_THIRD_BALL_MID = 500;

    CRServo rightFirstStageIntakeServo;
    CRServo leftFirstStageIntakeServo;
    CRServo rightSecondStageTransportServo;
    CRServo leftSecondStageTransportServo;
    DcMotor thirdStageTransportMotor;

    VisionPortal camera;
    PredominantColorProcessor firstCell;
    PredominantColorProcessor secondCell;
    PredominantColorProcessor thirdCell;
    PredominantColorProcessor thirdCell2;


    Thread shootVolley = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (this) {

                transportArtifactToShooter(Cell.RIGHT);
                sleep(WAIT_BETWEEN_FIRST_BALL);
                transportArtifactToShooter(Cell.LEFT);
                sleep(WAIT_BETWEEN_SECOND_BALL);
                startAll(Direction.FORWARD);
                sleep(WAIT_BETWEEN_THIRD_BALL);
                firstStageIntake(Direction.FORWARD);
                secondStageTransport(Direction.REVERSE);
                thirdStageTransport(Direction.REVERSE);
            }
        }
    });
    public Thread shootVolleyMid = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (this) {

                transportArtifactToShooter(Cell.RIGHT);
                sleep(WAIT_BETWEEN_FIRST_BALL_MID);
                transportArtifactToShooter(Cell.LEFT);
                sleep(WAIT_BETWEEN_SECOND_BALL_MID);
                startAll(Direction.FORWARD);
                sleep(WAIT_BETWEEN_THIRD_BALL_MID);
                firstStageIntake(Direction.FORWARD);
                secondStageTransport(Direction.REVERSE);
                thirdStageTransport(Direction.REVERSE);
            }
        }
    });

    public Intake(HardwareMap hardwareMap) {
        rightFirstStageIntakeServo = hardwareMap.get(CRServo.class, "1st Stage Right Servo");
        leftFirstStageIntakeServo = hardwareMap.get(CRServo.class, "1st Stage Left Servo");

        rightSecondStageTransportServo = hardwareMap.get(CRServo.class, "2nd Stage Right Servo");
        leftSecondStageTransportServo = hardwareMap.get(CRServo.class, "2nd Stage Left Servo");

        thirdStageTransportMotor = hardwareMap.dcMotor.get("3rd Stage Motor");

        rightFirstStageIntakeServo.setDirection(CRServo.Direction.FORWARD);
        leftFirstStageIntakeServo.setDirection(CRServo.Direction.REVERSE);

        rightSecondStageTransportServo.setDirection(CRServo.Direction.FORWARD);
        leftSecondStageTransportServo.setDirection(CRServo.Direction.REVERSE);

        thirdStageTransportMotor.setDirection(DcMotor.Direction.FORWARD);


        firstCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.45, -0.55, -0.35, -0.9))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE,
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE
                )
                .build();

        secondCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(0.5, -0.55, 0.65, -0.9))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE,
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE
                )
                .build();

        thirdCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.3, 0.3, -0.12, 0.05))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE,
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE


                )
                .build();

        thirdCell2 = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(0.15, 0.3, 0.25, 0.05))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE,
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE


                )
                .build();


        camera = new VisionPortal.Builder()
                .addProcessor(firstCell)
                .addProcessor(secondCell)
                .addProcessor(thirdCell)
                .addProcessor(thirdCell2)
                .setCameraResolution(new Size(1280, 720))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();
    }


    public void firstStageIntake(Direction d) {
        rightFirstStageIntakeServo.setPower(IN_POWER * d.getValue());
        leftFirstStageIntakeServo.setPower(IN_POWER * d.getValue());
    }

    public void rightSecondStageTransport(Direction d) {
        rightSecondStageTransportServo.setPower(IN_POWER * d.getValue());
    }

    public void leftSecondStageTransport(Direction d) {
        leftSecondStageTransportServo.setPower(IN_POWER * d.getValue());
    }

    public void secondStageTransport(Direction d) {
        rightSecondStageTransport(d);
        leftSecondStageTransport(d);
    }

    public void thirdStageTransport(Direction d) {
        thirdStageTransportMotor.setPower(IN_POWER * d.getValue());
    }

    public void startAll(Direction d) {
        firstStageIntake(d);
        secondStageTransport(d);
        thirdStageTransport(d);
    }

    public boolean isIntakeActive() {
        return !(rightFirstStageIntakeServo.getPower() == 0);
    }

    public void transportArtifactToShooter(Cell cell) {
        firstStageIntake(Direction.FORWARD);
        if (cell == Cell.RIGHT) {
            leftSecondStageTransport(Direction.REVERSE);
            rightSecondStageTransport(Direction.FORWARD);
        } else {
            rightSecondStageTransport(Direction.REVERSE);
            leftSecondStageTransport(Direction.FORWARD);
        }
        thirdStageTransport(Direction.FORWARD);
    }

    public void shootVolley() {
        if (shootVolley.isAlive()) {
            shootVolley.interrupt();
        } else {
            shootVolley.start();
        }
    }
    public void shootVolleyMid() {
        if (shootVolley.isAlive()) {
            shootVolley.interrupt();
        } else {
            shootVolley.start();
        }
    }

    public boolean whatDirectionThePurpleBall() {
        PredominantColorProcessor.Result firstCellResult = firstCell.getAnalysis();
        PredominantColorProcessor.Result secondCellResult = secondCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult = thirdCell.getAnalysis();

        return (firstCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK &&
                firstCellResult.closestSwatch != PredominantColorProcessor.Swatch.GREEN &&
                secondCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK &&
                secondCellResult.closestSwatch != PredominantColorProcessor.Swatch.PURPLE &&
                thirdCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK);
    }

    public boolean areThreeIn() {
        PredominantColorProcessor.Result firstCellResult = firstCell.getAnalysis();
        PredominantColorProcessor.Result secondCellResult = secondCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult = thirdCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult2 = thirdCell2.getAnalysis();

        return ((firstCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                firstCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE) &&
                (secondCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                        secondCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE) &&
                (thirdCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                        thirdCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE ||
                        thirdCellResult2.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE ||
                        thirdCellResult2.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN));
    }

    public boolean areBallStuckR() {
        PredominantColorProcessor.Result firstCellResult = firstCell.getAnalysis();
        PredominantColorProcessor.Result secondCellResult = secondCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult = thirdCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult2 = thirdCell2.getAnalysis();

        return (!(firstCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                firstCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE) &&
                (secondCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                        secondCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE) &&
                (thirdCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                        thirdCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE ||
                        thirdCellResult2.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE ||
                        thirdCellResult2.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN));

    }

    public boolean areBallStuckL() {
        PredominantColorProcessor.Result firstCellResult = firstCell.getAnalysis();
        PredominantColorProcessor.Result secondCellResult = secondCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult = thirdCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult2 = thirdCell2.getAnalysis();

        return ((firstCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                firstCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE) &&
                !(secondCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                        secondCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE) &&
                (thirdCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN ||
                        thirdCellResult.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE ||
                        thirdCellResult2.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_PURPLE ||
                        thirdCellResult2.closestSwatch == PredominantColorProcessor.Swatch.ARTIFACT_GREEN));

    }

    public void stop() {
        if (shootVolley.isAlive()) {
            shootVolley.interrupt();
        }
        camera.close();

        startAll(Direction.STOP);
    }

    public boolean isShootVolleyAlive() {
        return shootVolley.isAlive();
    }
}