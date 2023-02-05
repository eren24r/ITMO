import java.util.Random;
class firstlab {
    public static void main(String[] args) {
        long[] c = new long[5];
        float[] x = new float[14];
        double[][] ar = new double[5][14];
        c[0] = 15;
        Random r = new Random();

        for (int i = 1; i < c.length; i++) {
            c[i] = c[i - 1] - 2;
        }
        for (int i = 0; i < x.length; i++) {
            //x[i] = 15 + r.nextFloat() * (16 + 13);
            //x[i] = (float)Math.random() * (16 + 13) - 13;
            x[i] = (r.nextFloat(-13, 15));
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 14; j++) {
                double xx = x[j];
                double cc = c[i];
                if (cc == 15) {
                    ar[i][j] = (double) Math.atan(1 / Math.pow(Math.E, Math.abs(xx)));
                }
                if (cc == 9 || cc == 13) {
                    ar[i][j] = (double) Math.pow(Math.pow((Math.pow(Math.E, xx)), (Math.pow(xx, (1 / (2 * xx))) / (Math.pow(xx,  (double)(1 / 3)) - (double) (2 / 3)))), (double) (1 / 3));
                } else {
                    ar[i][j] = (double) Math.sin(Math.pow(Math.tan((double) Math.asin((double) ((double)(xx + 1) / 28))), (double) (1 / (Math.pow(Math.E, (xx * xx)) * 3))));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 14; j++) {
                System.out.printf("%.4f", ar[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}