package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import static android.os.SystemClock.sleep;

import android.annotation.SuppressLint;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;

@Autonomous
public class AutonomousBlueCloseByDistance extends BTJAuto {
    @SuppressLint("SuspiciousIndentation")
    public void runPath() {
        //acceleration
        shooter.setVelocity(1330);
        sleep(1000);

        //drive to shooting position
        pinpoint.resetPosAndIMU();
        drive.drive(0, -0.3, 0);
        while (pinpoint.getPosX(DistanceUnit.CM) > -105) {
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("r =", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        sleep(300);

        //first shooting
        intake.shootVolley();
        sleep(3000);

        shooter.setVelocity(1300);

        //rotate to first intake
        pinpoint.resetPosAndIMU();
        drive.drive(0, 0, -0.2);
        while (pinpoint.getHeading(AngleUnit.DEGREES) < 23) {
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("r =", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        sleep(300);


        //drive horizontally for first intake
        pinpoint.resetPosAndIMU();
        drive.drive(-0.4, 0, 0);
        while (pinpoint.getPosY(DistanceUnit.CM) < 27 ) {
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("r =", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        sleep(300);


        //activate intake
        intake.firstStageIntake(Intake.Direction.FORWARD);
        intake.secondStageTransport(Intake.Direction.REVERSE);
        intake.thirdStageTransport(Intake.Direction.REVERSE);

        //drive to intake artifact
        pinpoint.resetPosAndIMU();
        drive.drive(0, 0.3, 0);
        sleep(3000);
        drive.drive(0, 0, 0);
        sleep(200);

        //reverse driving for second shooting
        pinpoint.resetPosAndIMU();
        drive.drive(0, -0.25, 0);
        while (pinpoint.getPosX(DistanceUnit.CM) > -90 ) {
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("r =", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        sleep(1000);


        //rotate to second shooting
        pinpoint.resetPosAndIMU();
        drive.drive(0, 0, 0.25);
        while (pinpoint.getHeading(AngleUnit.DEGREES) < 29) {
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("r =", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();

        }
        drive.drive(0, 0, 0);
        sleep(300);

        //second shooting
        intake.shootVolley();
        sleep(1000);
        shooter.setPower(1);
        //rotate to leave
        pinpoint.resetPosAndIMU();
        drive.drive(0, 0, -0.25);
        while (pinpoint.getHeading(AngleUnit.DEGREES) < 29)
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("r =", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();

        drive.drive(0, 0, 0);
        sleep(300);
        intake.startAll(Intake.Direction.STOP );
        //leave
        drive.drive(0.8, 0, 0);
        sleep(1000);

        //stop
        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(1000);

//
//        drive.drive(0, 0, 0);
//        pinpoint.resetPosAndIMU();
//        sleep(300);
//        drive.drive(0.8, 0, 0);
//        while (pinpoint.getPosY(DistanceUnit.CM) < 20) {
//            pinpoint.update();
//            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
//            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
//            telemetry.update();
//        }


//        intake.firstStageIntake(Intake.Direction.FORWARD);
//        intake.secondStageTransport(Intake.Direction.REVERSE);
//        intake.thirdStageTransport(Intake.Direction.REVERSE);
//        drive.drive(0, 0, 0);
//        pinpoint.resetPosAndIMU();
//        sleep(300);
//        drive.drive(0, 0.5, 0);
//        while (pinpoint.getPosX(DistanceUnit.CM) < 130) {
//            pinpoint.update();
//            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
//            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
//            telemetry.update();
//        }
//        drive.drive(0, 0, 0);
//        pinpoint.resetPosAndIMU();
//        sleep(300);
//        drive.drive(0, -0.6, 0);
//        while (pinpoint.getPosX(DistanceUnit.CM) > -95) {
//            pinpoint.update();
//            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
//            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
//            telemetry.update();
//        }
//        drive.drive(0, 0, 0);
//        pinpoint.resetPosAndIMU();
//        sleep(300);
//        drive.drive(0.8, 0, 0);
//        while (pinpoint.getPosY(DistanceUnit.CM) < -7
//
//        ) {
//            pinpoint.update();
//            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
//            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
//            telemetry.update();
//        }
//        drive.drive(0, 0, 0);
//        pinpoint.resetPosAndIMU();
//        sleep(300);
//        shooter.setPower(0.8);
//        drive.drive(0, 0, -0.4);
//        while (pinpoint.getHeading(AngleUnit.DEGREES) < 35) {
//            pinpoint.update();
//            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
//            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
//            telemetry.addData("R=", pinpoint.getHeading(AngleUnit.DEGREES));
//            telemetry.update();


    }
}
