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
    public class AutonomousRedCloseByDistance extends BTJAuto {
        @SuppressLint("SuspiciousIndentation")
        public void runPath() {
            shooter.setPower(1);
            sleep(3000);
            shooter.setPower(shooter.SHOOTER_POWERS[shooter.CLOSE_CELL_IN_POWERS][Shooter.voltageFindRange(voltageSensor.getVoltage())] + shooter.powerOffset);
            sleep(500);
            drive.drive(0, -0.4, 0);
            while (pinpoint.getPosX(DistanceUnit.CM) > -105) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(1000);
            intake.shootVolley();
            sleep(2500);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0, 0, 0.2);
            while (pinpoint.getHeading(AngleUnit.DEGREES) > -25) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.addData("R=", pinpoint.getHeading(AngleUnit.DEGREES));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0.8, 0, 0);
            while (pinpoint.getPosY(DistanceUnit.CM) > -20)

                shooter.setPower(0.6);
            intake.firstStageIntake(Intake.Direction.FORWARD);
            intake.secondStageTransport(Intake.Direction.REVERSE);
            intake.thirdStageTransport(Intake.Direction.REVERSE);
            drive.drive(0, 0.4, 0);
            while (pinpoint.getPosX(DistanceUnit.CM) < 90) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.update();
            }
            sleep(200);
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0, -0.3, 0);
            while (pinpoint.getPosX(DistanceUnit.CM) > -70) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0, 0, -0.5);
            while (pinpoint.getHeading(AngleUnit.DEGREES) < 40

            ) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.addData("R=", pinpoint.getHeading(AngleUnit.DEGREES));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            sleep(1000);
            intake.shootVolley();
            sleep(2500);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0, 0, 0.2);
            while (pinpoint.getHeading(AngleUnit.DEGREES) > -48) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.addData("R=", pinpoint.getHeading(AngleUnit.DEGREES));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0, 0, 0);
            while (pinpoint.getPosY(DistanceUnit.CM) < 20) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.update();
            }
            intake.firstStageIntake(Intake.Direction.FORWARD);
            intake.secondStageTransport(Intake.Direction.REVERSE);
            intake.thirdStageTransport(Intake.Direction.REVERSE);
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0, 0.5, 0);
            while (pinpoint.getPosX(DistanceUnit.CM) < 130) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0, -0.6, 0);
            while (pinpoint.getPosX(DistanceUnit.CM) > -95) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            drive.drive(0.8, 0, 0);
            while (pinpoint.getPosY(DistanceUnit.CM) < -7

            ) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.update();
            }
            drive.drive(0, 0, 0);
            pinpoint.resetPosAndIMU();
            sleep(300);
            shooter.setPower(0.8);
            drive.drive(0, 0, -0.4);
            while (pinpoint.getHeading(AngleUnit.DEGREES) < 35) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.addData("R=", pinpoint.getHeading(AngleUnit.DEGREES));
                telemetry.update();
            }
            drive.drive(0.8, 0, 0);
            while (pinpoint.getHeading(AngleUnit.DEGREES) < 20) {
                pinpoint.update();
                telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
                telemetry.addData("R=", pinpoint.getHeading(AngleUnit.DEGREES));
                telemetry.update();

                drive.drive(0, 0, 0);
                pinpoint.resetPosAndIMU();
                sleep(1000);
                intake.shootVolley();
            }
        }
    }