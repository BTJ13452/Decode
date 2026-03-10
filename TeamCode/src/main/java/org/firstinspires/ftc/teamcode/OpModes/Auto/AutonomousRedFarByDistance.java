package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
        shooter.setPower(1);
        sleep(5000);
        shooter.setPower(shooter.SHOOTER_POWERS[shooter.MID_CELL_IN_POWERS][Shooter.voltageFindRange(voltageSensor.getVoltage())] + shooter.powerOffset);
        sleep(1000);
        intake.shootVolley();
        sleep(2000);
        drive.drive(0,0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) < 60) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }
        drive.drive(0,0,0);
        pinpoint.resetPosAndIMU();
        sleep(500);
        drive.drive(0, 0, 0.2);
        while (pinpoint.getHeading(AngleUnit.DEGREES) > -90) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("R=",pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(500);
        intake.firstStageIntake(Intake.Direction.FORWARD);
        intake.secondStageTransport(Intake.Direction.REVERSE);
        intake.thirdStageTransport(Intake.Direction.REVERSE);
        drive.drive(0,0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) < 95) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }
        drive.drive(0,0,0);
        pinpoint.resetPosAndIMU();
        sleep(100);
        drive.drive(0,-0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) > -85) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(500);
        drive.drive(0, 0, -0.2);
        shooter.setPower(0.6);
        while (pinpoint.getHeading(AngleUnit.DEGREES) < 117) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("R=",pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(100);
        drive.drive(0,-0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) > -45) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        sleep(1000);
        intake.shootVolley();
        sleep(1500);
        pinpoint.resetPosAndIMU();
        sleep(1000);
        drive.drive(0,0.4,0);
        sleep(1000);
        drive.drive(0,0,0);
    }
}
//       drive.drive(0,0.3,0);
//        while (pinpoint.getPosX(DistanceUnit.CM) < 50){
//            pinpoint.update();
//        }
//        drive.drive(0,0,0);
