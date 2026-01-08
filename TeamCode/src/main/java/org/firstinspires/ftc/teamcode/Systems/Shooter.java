package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class Shooter {

    public enum Distance {
        CLOSE,
        MID,
        FAR;

        public static int getValue(Distance d) {
            switch (d) {
                case CLOSE:
                    return 0;
                case MID:
                    return 1;
                case FAR:
                    return 2;
            }
            return -1;
        }
    }
    double[][] SHOOTER_POWERS =
            { {0.7/*close*/,                                                                       0.655, 0.65, 0.65},
             {0.8/*mid*/,                                                                         0.68, 0.66, 0.64},
               {0.9 /*far*/,                                                                      0.855, 0.8, 0.78}};


    public double powerOffset;
    DcMotor ShooterMotorR;
    DcMotor ShooterMotorL;

    public Shooter(HardwareMap hardwareMap) {
        ShooterMotorR = hardwareMap.dcMotor.get("ShooterMotorR");
        ShooterMotorL = hardwareMap.dcMotor.get("ShooterMotorL");
        ShooterMotorR.setDirection(DcMotorSimple.Direction.REVERSE);
        ShooterMotorL.setDirection(DcMotorSimple.Direction.FORWARD);
        powerOffset = 0;

        reset();
        addPoint(1.0297,0.61);
        addPoint(0.6385,0.65);
        addPoint(2.135,0.65);
        addPoint(0.2450,0.79);
        addPoint(0.345,0.71);
    }


    // points_array = [(x0,y0), (x1,y1), ...]
    private List<double[]> pointsArray = new ArrayList<>();

    // c_array = [c0, c1, c2, ...]
    private List<Double> cArray = new ArrayList<>();

    // p(i, x)
    private double p(int i, double x) {
        if (i == 0) {
            return cArray.get(0); // p0(x) = c0
        }

        double product = 1.0;
        for (int j = 0; j < i; j++) {
            product *= (x - pointsArray.get(j)[0]);
        }

        double temp = cArray.get(i) * product;
        return p(i - 1, x) + temp;
    }

    // c(i)
    private double c(int i) {
        double xi = pointsArray.get(i)[0];
        double yi = pointsArray.get(i)[1];

        if (i == 0) {
            return yi; // c0 = y0
        }

        double numerator = yi - p(i - 1, xi);

        double denominator = 1.0;
        for (int j = 0; j < i; j++) {
            denominator *= (xi - pointsArray.get(j)[0]);
        }

        return numerator / denominator;
    }

    // addPoint(x, y)
    public void addPoint(double x, double y) {
        pointsArray.add(new double[]{x, y});
        cArray.add(c(cArray.size()));
    }

    // resetP()
    public void reset() {
        pointsArray.clear();
        cArray.clear();
    }

    // pNuton(x)
    public double pNewton(double x) {
        return p(cArray.size() - 1, x);
    }

    public void setPower(double power) {
        ShooterMotorR.setPower(power);
        ShooterMotorL.setPower(power);
    }

    public double getPower() {
        return ShooterMotorL.getPower();
    }

    public void autoSpeed(Distance d, double voltage) {
        double power = SHOOTER_POWERS[Distance.getValue(d)][0];
        setPower(power + powerOffset);
    }
    public int voltageFindRange(double voltage) {
        if (voltage > 11 && voltage <= 12)
            return 0;

        if (voltage > 12 && voltage <= 13)
            return 1;

        if (voltage > 13 && voltage <= 14)
            return 2;

        if (voltage > 14)
            return 3;

        return -1;
    }

}



