package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import static android.os.SystemClock.sleep;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;
@Autonomous

public class AutonomousRedFarByDistance extends BTJAuto {
    public void runPath() {
        shooter.setVelocity(1570);
        sleep(5000);
        //the firset shoot
        intake.shootVolley();
        sleep(3000);

        drive.drive(0,0,0.3);
        while (pinpoint.getHeading(AngleUnit.DEGREES) > -55) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("R=",pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        intake.firstStageIntake(Intake.Direction.FORWARD);
        intake.secondStageTransport(Intake.Direction.REVERSE);
        intake.thirdStageTransport(Intake.Direction.REVERSE);

        //drive horizontally for first intake
        drive.drive(0.4, 0, 0);
        sleep(1000);

        drive.drive(0,0,0);
        pinpoint.resetPosAndIMU();
        sleep(500);
        //go to intake
        drive.drive(0, 0.3, 0);
        sleep(3500);

        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(500);


        drive.drive(-0.3, 0, 0);
        sleep(500);

        drive.drive(0.3, 0, 0);
        sleep(700);

        drive.drive(0, 0.3, 0);
        sleep(500);

            drive.drive(0,0,0);
            pinpoint.resetPosAndIMU();
            sleep(300);

        drive.drive(0,-0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) > -105) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }

        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(300);

        drive.drive(0, 0, -0.2);
        while (pinpoint.getHeading(AngleUnit.DEGREES) < 94.9) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("R=",pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }

        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(300);

        intake.shootVolley();
        sleep(3000);
        pinpoint.resetPosAndIMU();

        drive.drive(0,0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) < 20) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("R=",pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }

        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(100);
    }
}
