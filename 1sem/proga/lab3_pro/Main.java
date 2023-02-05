import Classes.Client;
import Classes.Corp;
import Classes.Stock;
import Obj.*;

import java.awt.image.CropImageFilter;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Corp big = new ArbuzCorp();
        System.out.println(big.getName() + " is a bigger Corp");
        System.out.println(big.getPrice() + " USD");
        System.out.println(Stock.pricetoRUB(big.getPrice()) + " RUB");
        System.out.println(big.getType()); nl();
        Corp[] korotishka = new Corp[25];

        for (int i = 0; i <= 24; i++) {
            String name = "Коротышка " + i;
            korotishka[i] = new Corp(name, new Random().nextInt(1, 50));
            //System.out.println(korotishka[i].getPrice());
        }

        int[] fivesmallbig = new int[5];
        int s = 0;
        while (s < 5) {
            int ii = new Random().nextInt(0, 24);
            korotishka[ii].addPrice((new Random().nextInt(20, 50)));
            if (s > 0 && fivesmallbig[s - 1] != ii && korotishka[ii].getPrice() > 100) {
                fivesmallbig[s] = ii;
                s = s + 1;
            }
            if (s == 0 && korotishka[ii].getPrice() > 100) {
                fivesmallbig[s] = ii;
                s = s + 1;
            }
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(korotishka[fivesmallbig[i]].getName() + " залез на вершину арбуза");
        }
        nl();
        Cucumber[] cucumbers = new Cucumber[8];
        for (int i = 0; i < 8; i++) {
            String name = "Cucumber " + i;
            cucumbers[i] = new Cucumber(name);
            cucumbers[i].info();
        }

        nl();
        Neznayka neznayka = new Neznayka();
        neznayka.info();
        nl();

        Stock.buyStock(neznayka, cucumbers[0], 10);
        System.out.println(cucumbers[0].getPrice()); nl();
        neznayka.stocks(); nl();
        neznayka.inScafandara();
        neznayka.gotoKosmos(); nl();
        Sedenkiy sed = new Sedenkiy();
        while (sed.countOfStocks() < 10) {
            if (new Random().nextInt(1, 2) == 1) {
                int ii = new Random().nextInt(0, 24);
                int price = new Random().nextInt(1, 20);
                Stock.buyStock(sed, korotishka[ii], price);
            } else {
                int ii = new Random().nextInt(0, 8);
                int price = new Random().nextInt(1, 20);
                Stock.buyStock(sed, cucumbers[ii], price);
            }
        }

        nl();
        sed.stocks();
        sed.addMoney(1);
    }

    public static void nl() {
        System.out.println();
    }
}