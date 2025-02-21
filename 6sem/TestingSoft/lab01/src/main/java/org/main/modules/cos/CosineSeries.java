package org.main.modules.cos;

public class CosineSeries {
    public static double cos(double x, int terms) {
        double sum = 0;
        for (int n = 0; n < terms; n++) {
            sum += Math.pow(-1, n) * Math.pow(x, 2 * n) / factorial(2 * n);
        }
        return sum;
    }

    private static double factorial(int n) {
        double fact = 1;
        for (int i = 1; i <= n; i++) fact *= i;
        return fact;
    }
}