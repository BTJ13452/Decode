package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Intake;

@TeleOp

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
                    intake.activateEjection();
                    while (gamepad1.x) {
                    }
                    intake.deactivateAllIntake();
                }
            }
        });
    }
;
      @Override
    public void loop() {
        if (gamepad1.xWasPressed()) {
            if (intake.isActive()) {
                intake.deactivateIntake();
            } else {
                intake.activateIntake();
            }


            waitForLongXPress.interrupt();
            waitForLongXPress.start();

        }


            if (gamepad1.rightBumperWasPressed()) {
            if (intake.isMidRightActive()) {
                intake.deMidRightActivateIntake();
                intake.deactivateWheel();


            }
            else {
                intake.rightActivateMidIntake();
                intake.ActivateWheel();

            }

        }


        if (gamepad1.leftBumperWasPressed()){
            if (intake.isMidLeftActive()) {
                intake.deMidLeftActivateIntake();
                intake.deactivateWheel();

            }
            else {
                intake.leftActivateMidIntake();
                intake.ActivateWheel();


            }

        }

    }
}