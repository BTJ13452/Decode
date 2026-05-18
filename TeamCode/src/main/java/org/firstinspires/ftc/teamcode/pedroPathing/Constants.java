package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.pedropathing.control.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class Constants {

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(14.1);
//            .forwardZeroPowerAcceleration(-34.657157291566286)
//            .lateralZeroPowerAcceleration(-69.86924777259544)
//            .useSecondaryTranslationalPIDF(false)
//            .useSecondaryHeadingPIDF(false)
//            .useSecondaryDrivePIDF(false)
//            .centripetalScaling(0.0008)
//            .translationalPIDFCoefficients(new PIDFCoefficients(0.05, 0.0, 0.02, 0))
//            .headingPIDFCoefficients(new PIDFCoefficients(2, 0, 0.1, 0))
//            .drivePIDFCoefficients(
//                    new FilteredPIDFCoefficients(0.00792, 0.0, 0.000001, 1.7, 0)
//                    //i=0535
//            );


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .leftFrontMotorName("Front left")
            .leftRearMotorName("Back left")
            .rightFrontMotorName("Front right")
            .rightRearMotorName("Back right")

            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
//            .xVelocity(59.33493162140134)
//            .yVelocity(47.812744621216794)

            ;

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-101.29)
            .strafePodX(-132.36)
            .distanceUnit(DistanceUnit.MM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(
            0.995,
            500,
            0.65,
            1
    );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .mecanumDrivetrain(driveConstants)
                .pathConstraints(pathConstraints)
                .build();
    }
}