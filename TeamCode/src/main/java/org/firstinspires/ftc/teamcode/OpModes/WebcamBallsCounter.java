package org.firstinspires.ftc.teamcode.OpModes;

import android.graphics.Color;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Systems.RGBController;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

import java.security.PermissionCollection;

@TeleOp

public class WebcamBallsCounter extends LinearOpMode {

    RGBController rgbController;

    @Override
    public void runOpMode() throws InterruptedException {

        rgbController = new RGBController(hardwareMap);

        PredominantColorProcessor firstCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.60, -0.4, -0.45, -0.6))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
                )
                .build();

        PredominantColorProcessor secondCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(0.10, -0.36, 0.20, -0.6))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
                )
                .build();

        PredominantColorProcessor thirdCell = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.625, 0.5, -0.5, 0.4))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
                )
                .build();

        PredominantColorProcessor thirdCell2 = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.8, 0.4, -0.40, 0.2))
                .setSwatches(
                        PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                        PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                        PredominantColorProcessor.Swatch.BLACK
                )
                .build();


        VisionPortal portal = new VisionPortal.Builder()
                .addProcessor(firstCell)
                .addProcessor(secondCell)
                .addProcessor(thirdCell)
                .addProcessor(thirdCell2)

                .setCameraResolution(new Size(1280, 720))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        telemetry.setMsTransmissionInterval(100);
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        while (opModeIsActive() || opModeInInit()) {
            PredominantColorProcessor.Result firstCellResult = firstCell.getAnalysis();

            // Display the Color Sensor result.
            telemetry.addData("Best Match", firstCellResult.closestSwatch);
            telemetry.addLine(String.format("RGB   (%3d, %3d, %3d)",
                    firstCellResult.RGB[0], firstCellResult.RGB[1], firstCellResult.RGB[2]));


            PredominantColorProcessor.Result secondCellResult = secondCell.getAnalysis();

            // Display the Color Sensor result.
            telemetry.addData("Best Match", secondCellResult.closestSwatch);
            telemetry.addLine(String.format("RGB   (%3d, %3d, %3d)",
                    secondCellResult.RGB[0], secondCellResult.RGB[1], secondCellResult.RGB[2]));


            PredominantColorProcessor.Result thirdCellResult = thirdCell.getAnalysis();

            // Display the Color Sensor result.
            telemetry.addData("Best Match", thirdCellResult.closestSwatch);
            telemetry.addLine(String.format("RGB   (%3d, %3d, %3d)",
                    thirdCellResult.RGB[0], thirdCellResult.RGB[1], thirdCellResult.RGB[2]));

            PredominantColorProcessor.Result thirdCellResult2 = thirdCell.getAnalysis();

            // Display the Color Sensor result.
            telemetry.addData("Best Match", thirdCellResult2.closestSwatch);
            telemetry.addLine(String.format("RGB   (%3d, %3d, %3d)",
                    thirdCellResult2.RGB[0], thirdCellResult2.RGB[1], thirdCellResult2.RGB[2]));
            telemetry.update();


            if (firstCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK &&
                    secondCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK &&
                    thirdCellResult.closestSwatch != PredominantColorProcessor.Swatch.BLACK) {
                rgbController.setGreen();
            } else {
                rgbController.setOff();
            }


        }

    }
}
