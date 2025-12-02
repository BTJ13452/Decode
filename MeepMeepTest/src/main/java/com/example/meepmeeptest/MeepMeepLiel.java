package com.example.meepmeeptest;


import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepLiel {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 18)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(62, 15, Math.toRadians(180)))
                        .splineToLinearHeading(new Pose2d(54, 15,Math.toRadians(160)), Math.toRadians(160))
                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(62, 62,Math.toRadians(90)), Math.toRadians(90))
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(54, 15,Math.toRadians(160)), Math.toRadians(340))


//                        .splineToLinearHeading(new Pose2d(36, 45,Math.toRadians(90)), Math.toRadians(90))
//                        .setTangent(Math.toRadians(-90))
//                        .splineToLinearHeading(new Pose2d(59, 15,Math.toRadians(160)), Math.toRadians(-20))
//                        .waitSeconds(3)
//                        .setTangent(Math.toRadians(160))
//                        .splineToLinearHeading(new Pose2d(12, 45,Math.toRadians(90)), Math.toRadians(90))
//                        .setTangent(Math.toRadians(-90))
//                        .splineToLinearHeading(new Pose2d(59, 15,Math.toRadians(160)), Math.toRadians(-20))
//                        .waitSeconds(3)
//                        .setTangent(Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(60, 65,Math.toRadians(0)), Math.toRadians(0))
//                        .setTangent(Math.toRadians(-90))
//                        .splineToLinearHeading(new Pose2d(59, 15,Math.toRadians(160)), Math.toRadians(-20))
//                        .waitSeconds(3)
//                        .forward(15)
                        .build());


        Image img = null;
        try { img = ImageIO.read(new File("C:/Users/ftcbt/StudioProjects/BTJ-2025-2026/MeepMeepTest/src/main/java/com/example/meepmeeptest/decode-custom-field-images-meepmeep-compatible-printer-v0-xsjhmvxpoonf1.png")); }
        catch(IOException e) {}

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
