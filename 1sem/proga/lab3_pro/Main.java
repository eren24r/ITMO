import Classes.Korotishka;
import Classes.Stock;
import Classes.Store;
import Enums.StockType;
import Enums.StoreType;
import Interfaces.Info;
import Obj.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Stock big = new ArbuzCorp();
        System.out.println(big.getName() + " is a bigger Corp");
        System.out.println(big.getPrice() + " USD");
        System.out.println(big.pricetoRUB() + " RUB");
        System.out.println(big.getType()); nl();
        Stock[] korotishka = new Stock[25];

        for (int i = 0; i <= 24; i++) {
            String name = "Коротышка " + i;
            korotishka[i] = new Stock(name, new Random().nextInt(1, 50));
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

        neznayka.buyStock(cucumbers[0], 10);
        System.out.println(cucumbers[0].getPrice()); nl();
        Stock nulStock = new Stock("Nul Stock", 0);
        System.out.println(nulStock.getPrice());
        neznayka.buyStock(nulStock, 10);
        neznayka.listOfStocks(); nl();
        neznayka.inScafandara();
        neznayka.gotoKosmos(); nl();
        Sedenkiy sed = new Sedenkiy();
        while (sed.countOfStocks() < 10) {
            if (new Random().nextInt(1, 2) == 1) {
                int ii = new Random().nextInt(0, 24);
                int price = new Random().nextInt(1, 20);
                sed.buyStock(korotishka[ii], price);
            } else {
                int ii = new Random().nextInt(0, 8);
                int price = new Random().nextInt(1, 20);
                sed.buyStock(cucumbers[ii], price);
            }
        }

        nl();
        sed.listOfStocks();
        sed.addMoney(1);

        Korotishka Dzulio = new Korotishka("Жулио", 500);
        Dzulio.createStore("DZ Store", StoreType.small);
        Dzulio.getStore().getProducts();
        Store.Workers DzulioWorkers = Dzulio.getStore().new Workers();
        Korotishka workers1 = new Korotishka("Незнайка", 45);
        Korotishka workers2 = new Korotishka("Козлик", 55);
        DzulioWorkers.addWorker(workers1);
        DzulioWorkers.addWorker(workers2);
        System.out.println(Dzulio.getMoney());
        System.out.println("Day1:");
        Dzulio.getStore().work();
        System.out.println(Dzulio.getStore().getSalary());
        System.out.println(Dzulio.getMoney()); nl();
        System.out.println("Day2:");
        Dzulio.getStore().work();
        System.out.println(Dzulio.getStore().getSalary());
        System.out.println(Dzulio.getMoney());nl();
    }
    public static void nl() {
        System.out.println();
    }
}