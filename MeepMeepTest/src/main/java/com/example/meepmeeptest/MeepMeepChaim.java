package com.example.meepmeeptest;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepChaim {public static void main(String[] args) {
    MeepMeep meepMeep = new MeepMeep(600);

    RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
            // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            //נסיעה ראשונה
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
            .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-63 , 9, Math.toRadians(0)))
                    .waitSeconds(1)
                    .setTangent(Math.toRadians(0))
                    .splineToLinearHeading(new Pose2d(-12, 44,Math.toRadians(83
                    )), Math.toRadians(90))
                  //שאיבה
                    .setTangent(Math.toRadians(-90))
                  .splineToLinearHeading(new Pose2d(-14, 14,Math.toRadians(135)), Math.toRadians(-90))
                    .waitSeconds(1)
                   .setTangent(Math.toRadians(135))
                    //נסיעה שניה
                    .waitSeconds(1)
                    .setTangent(Math.toRadians(0))
                    .splineToLinearHeading(new Pose2d(13, 44,Math.toRadians(83)), Math.toRadians(90))
                    //שאיבה
                    .setTangent(Math.toRadians(-90))
                    .splineToLinearHeading(new Pose2d(-14, 14,Math.toRadians(135)), Math.toRadians(-90))
                    .waitSeconds(1)
                    .setTangent(Math.toRadians(135))
                     //נסיעה שלישית
                    .waitSeconds(1)
                    .setTangent(Math.toRadians(0))
                     .splineToLinearHeading(new Pose2d(36.5, 44,Math.toRadians(83)), Math.toRadians(90))
                    //שאיבה
                     .setTangent(Math.toRadians(-90))
                    .splineToLinearHeading(new Pose2d(-14, 14,Math.toRadians(135)), Math.toRadians(-90))
                     .waitSeconds(1)
                    .setTangent(Math.toRadians(135))





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
