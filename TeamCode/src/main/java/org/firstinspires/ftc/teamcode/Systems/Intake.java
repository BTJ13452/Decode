package org.firstinspires.ftc.teamcode.Systems;

import android.util.Size;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

public class Intake {

    public enum Direction {
        FORWARD,
        REVERSE,
        STOP;

        public static int getValue(Direction d) {
            switch (d) {
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

    CRServo rightFirstStageIntakeServo;
    CRServo leftFirstStageIntakeServo;
    CRServo rightSecondStageTransportServo;
    CRServo leftSecondStageTransportServo;
    CRServo rightThirdStageTransportServo;
    CRServo leftThirdStageTransportServo;




    VisionPortal camera;
    PredominantColorProcessor firstCell;
    PredominantColorProcessor secondCell;
    PredominantColorProcessor thirdCell;
    PredominantColorProcessor thirdCell2;

    public Intake(HardwareMap hardwareMap) {
        rightFirstStageIntakeServo = hardwareMap.get(CRServo.class, "1st Stage Right Servo");
        leftFirstStageIntakeServo = hardwareMap.get(CRServo.class, "1st Stage Left Servo");

        rightSecondStageTransportServo = hardwareMap.get(CRServo.class, "2nd Stage Right Servo");
        leftSecondStageTransportServo = hardwareMap.get(CRServo.class, "2nd Stage Left Servo");

        leftThirdStageTransportServo = hardwareMap.get(CRServo.class, "3rd Stage Left Servo");
        rightThirdStageTransportServo = hardwareMap.get(CRServo.class, "3rd Stage Right Servo");

        rightSecondStageTransportServo.setDirection(CRServo.Direction.REVERSE);
        leftSecondStageTransportServo.setDirection(CRServo.Direction.FORWARD);

        rightFirstStageIntakeServo.setDirection(CRServo.Direction.REVERSE);
        leftFirstStageIntakeServo.setDirection(CRServo.Direction.FORWARD);

        leftThirdStageTransportServo.setDirection(CRServo.Direction.FORWARD);
        rightThirdStageTransportServo.setDirection(CRServo.Direction.REVERSE);

        firstCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.60, -0.4, -0.45, -0.6))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
                )
                .build();

        secondCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(0.10, -0.36, 0.20, -0.6))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
                )
                .build();

        thirdCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.625, 0.5, -0.5, 0.4))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
                )
                .build();

        thirdCell2 = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.8, 0.4, -0.40, 0.2))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
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
        rightFirstStageIntakeServo.setPower(IN_POWER * Direction.getValue(d));
        leftFirstStageIntakeServo.setPower(IN_POWER * Direction.getValue(d));
    }

    public void rightSecondStageTransport(Direction d) {
        rightSecondStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
    }

    public void leftSecondStageTransport(Direction d) {
        leftSecondStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
    }

    public void secondStageTransport(Direction d) {
        rightSecondStageTransport(d);
        leftSecondStageTransport(d);
    }

    public void thirdStageTransport(Direction d) {
        leftThirdStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
        rightThirdStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
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
            rightSecondStageTransport(Direction.FORWARD);
            leftSecondStageTransport(Direction.REVERSE);
        } else {
            leftSecondStageTransport(Direction.FORWARD);
            rightSecondStageTransport(Direction.REVERSE);
        }
        thirdStageTransport(Direction.FORWARD);
    }



    public boolean areThreeIn(){
        PredominantColorProcessor.Result firstCellResult = firstCell.getAnalysis();
        PredominantColorProcessor.Result secondCellResult = secondCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult = thirdCell.getAnalysis();
        PredominantColorProcessor.Result thirdCellResult2 = thirdCell.getAnalysis();

        return (firstCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK &&
                secondCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK &&
                thirdCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK);
    }

}