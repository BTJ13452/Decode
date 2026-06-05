package org.firstinspires.ftc.teamcode.OpModes.Tests;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Systems.Intake;

@TeleOp
@Disabled


public class TestIntake extends OpMode {

    final int LONG_PRESS_MILLISECONDS = 500;

    Intake intake;

    Thread waitForLongXPress;

    Intake rigthActivateMidIntake;
    Intake leftActivateMidIntake;

    @Override
    public void init() {
        intake = new Intake(hardwareMap);

        waitForLongXPress = new Thread(new Runnable() {
            @Override
            public void run() {
                gamepad1.xWasReleased();
                sleep(LONG_PRESS_MILLISECONDS);
                if (!gamepad1.xWasReleased()) {
                    intake.startAll(Intake.Direction.REVERSE);
                    while (gamepad1.x) {
                    }
                    intake.startAll(Intake.Direction.STOP);
                }
            }
        });
    }
;

    @Override
    public void loop() {
          if (gamepad1.xWasPressed()) {
              if (intake.isIntakeActive()) {
                  intake.startAll(Intake.Direction.STOP);
              } else {
                  intake.firstStageIntake(Intake.Direction.FORWARD);
              }

              waitForLongXPress.interrupt();
              waitForLongXPress.start();

          }

        telemetry.addData("right", intake.whichArtifactInCell(Intake.Cell.RIGHT));
        telemetry.addData("left", intake.whichArtifactInCell(Intake.Cell.LEFT));
        telemetry.addData("middle", intake.whichArtifactInCell(Intake.Cell.MIDDLE));




//            if (gamepad1.right_bumperwasPresed) {
//                intake.deMidRightActivateIntake();
//                intake.leftActivateMidIntake();
//                intake.deactivateWheel();
//            }
//            else {
//                intake.rightActivateMidIntake();
//                intake.deMidLeftActivateIntake();
//                intake.activateWheel();
//
//            }


//        if (gamepad1.leftBumperWasPressed()){
//            if (intake.isMidLeftActive()) {
//                intake.deMidLeftActivateIntake();
//                intake.rightActivateMidIntake();
//                intake.deactivateWheel();
//
//            }
//            else {
//                intake.leftActivateMidIntake();
//                intake.deMidRightActivateIntake();
//                intake.activateWheel();
//
//
//            }
//
//        }

          if (gamepad1.right_bumper) {
              intake.rightSecondStageTransport(Intake.Direction.FORWARD);
              intake.leftSecondStageTransport(Intake.Direction.STOP);
              intake.thirdStageTransport(Intake.Direction.FORWARD);
          }

      }
}