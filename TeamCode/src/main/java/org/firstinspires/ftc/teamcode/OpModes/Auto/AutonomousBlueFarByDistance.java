package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import static android.os.SystemClock.sleep;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;
    @Autonomous
public class AutonomousBlueFarByDistance extends OpMode {

    Drive drive;
    GoBildaPinpointDriver pinpoint;
    Intake intake;
    Shooter shooter;

    @Override
    public void init() {
        drive = new Drive(hardwareMap,0,false);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "Pinpoint");
        pinpoint.resetPosAndIMU();
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
    }
    @Override
    public void loop() {
        telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
        telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
        telemetry.update();
        pinpoint.update();
    }

    @Override
    public void start() {
        shooter.setPower(1);
        sleep(4500);
        shooter.setPower(0.85);
        sleep(4000);
        intake.shootVolley();
        sleep(1000);
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
        drive.drive(0,0,-0.2);
        while (pinpoint.getHeading(AngleUnit.DEGREES) < 80) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("R=",pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
        drive.drive(0,0,0);
        pinpoint.resetPosAndIMU();
        sleep(500);
        intake.firstStageIntake(Intake.Direction.FORWARD);
        intake.secondStageTransport(Intake.Direction.REVERSE);
        intake.thirdStageTransport(Intake.Direction.REVERSE);
        drive.drive(0,0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) < 100) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }
        drive.drive(0,0,0);
        pinpoint.resetPosAndIMU();
        sleep(100);
        drive.drive(0,-0.4,0);
        while (pinpoint.getPosX(DistanceUnit.CM) > -100) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        pinpoint.resetPosAndIMU();
        sleep(500);
        drive.drive(0, 0, 0.2);
        while (pinpoint.getHeading(AngleUnit.DEGREES) > -40) {
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
        while (pinpoint.getPosX(DistanceUnit.CM) > -35) {
            pinpoint.update();
            telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.update();
        }
        drive.drive(0, 0, 0);
        sleep(100);
        intake.shootVolley();
        sleep(5000);
        pinpoint.resetPosAndIMU();
        sleep(10000);
        drive.drive(0,1,0);
       }
}
//       drive.drive(0,0.3,0);
//        while (pinpoint.getPosX(DistanceUnit.CM) < 50){
//            pinpoint.update();
//        }
//        drive.drive(0,0,0);
