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
            return 0;
        }
    }


    double[][] SHOOTER_POWERS = {
            {0.7, 0.655, 0.65, 0.65},   // CLOSE
            {0.8, 0.68, 0.66, 0.64},   // MID
            {0.9, 0.855, 0.8, 0.78}    // FAR
    };


    DcMotor ShooterMotorR;
    DcMotor ShooterMotorL;

    public double powerOffset = 0;


    // pointsArray[range] = [(x0,y0), (x1,y1), ...]
    private final List<double[]>[] pointsArray = new ArrayList[6];

    // cArray[range] = [c0, c1, c2, ...]
    private final List<Double>[] cArray = new ArrayList[6];


    public Shooter(HardwareMap hardwareMap) {

//        ShooterMotorR = hardwareMap.dcMotor.get("ShooterMotorR");
        ShooterMotorL = hardwareMap.dcMotor.get("ShooterMotorL");

//        ShooterMotorR.setDirection(DcMotorSimple.Direction.REVERSE);
        ShooterMotorL.setDirection(DcMotorSimple.Direction.FORWARD);

        // init arrays
        for (int i = 0; i < 6; i++) {
            pointsArray[i] = new ArrayList<>();
            cArray[i] = new ArrayList<>();
        }

        reset();

        // sample points
//        addPoint(1.0297, 0.61, 12.5);
//        addPoint(0.6385, 0.65, 12.5);
//        addPoint(2.135,  0.65, 12.5);
//        addPoint(0.2450, 0.79, 12.5);
//        addPoint(0.345,  0.71, 12.5);
//    //עד כאן לשנות
//        addPoint(0.24,   0.8,  12.5);
        //12.5 volt
//        addPoint(0.24,0.73,12.5);
//        addPoint(0.62,0.65,12.5);
//        addPoint(2.6,0.68,12.5);
        addPoint(2.6353, 0.69, 12.402);
        addPoint(0.2383, 0.74, 12.740);
        addPoint(0.3293, 0.72, 12.593);
        addPoint(0.6688, 0.66, 12.585);
        addPoint(0.6302, 0.69, 12.557);
        addPoint(0.6788, 0.66, 12.638);
        addPoint(0.23, 0.74, 12.742);
        addPoint(0.24895, 0.74, 12.893);


//                     //12 volt
//        addPoint(2.6,0.67,12);


        //11.5 volt

        //11 volt

        //10.5 volt

    }


    // p(i, x)
    private double p(int i, double x, int range) {
        if (i < 0) return 0;
        if (i == 0) return cArray[range].get(0);

        double product = 1.0;
        for (int j = 0; j < i; j++) {
            product *= (x - pointsArray[range].get(j)[0]);
        }

        return p(i - 1, x, range) + cArray[range].get(i) * product;
    }

    // c(i)
    private double c(int i, int range) {
        double xi = pointsArray[range].get(i)[0];
        double yi = pointsArray[range].get(i)[1];

        if (i == 0) return yi;

        double numerator = yi - p(i - 1, xi, range);

        double denominator = 1.0;
        for (int j = 0; j < i; j++) {
            denominator *= (xi - pointsArray[range].get(j)[0]);
        }

        return numerator / denominator;
    }


    public void addPoint(double x, double y, double voltage) {
        int range = voltageFindRange(voltage);
        if (range < 0) return;

        pointsArray[range].add(new double[]{x, y});
        cArray[range].add(c(cArray[range].size(), range));
    }

    public void reset() {
        for (int i = 0; i < 6; i++) {
            pointsArray[i].clear();
            cArray[i].clear();
        }
    }

    // pNewton(x)
//    public double calculatePowerByDistance(double ta, double voltage) {
//        int range = voltageFindRange(voltage);
//        if (range < 0 || cArray[range].isEmpty()) return 0;
//
//        return p(cArray[range].size() - 1, ta, range);
//    }


    public void setPower(double power) {
        ShooterMotorR.setPower(power);
        ShooterMotorL.setPower(power);
    }

    public double getPower() {
        return ShooterMotorL.getPower();
    }

    public void autoSpeed(Distance d, double voltage) {
        double basePower = SHOOTER_POWERS[Distance.getValue(d)][0];
        setPower(basePower + powerOffset);
    }

    public boolean isActive() {
        return getPower() != 0;
    }

//    public void setPowerByDistance(double d, double v) {
//        setPower(calculatePowerByDistance(d, v));
//    }

    public void setPowerByDistance(double d, double v) {
        setPower(calculatePowerWithDistance(d, v));
    }

    public double calculatePowerWithDistance(double d, double v) {


        return (-0.0741301 * d * d * d + 0.345369 * d * d - 0.448185 * d +0.82909);


    }


    public int voltageFindRange(double voltage) {

        if (Double.isNaN(voltage)) return 0;

        if (voltage <= 9) return 0;
        if (voltage <= 10) return 1;
        if (voltage <= 11) return 2;
        if (voltage <= 12) return 3;
        if (voltage <= 13) return 4;

        return 5;
    }
}
