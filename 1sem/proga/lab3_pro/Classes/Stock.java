package Classes;

import Interfaces.CorpIn;
import Interfaces.AricmeticksLambda;
import Interfaces.DualArif;

public class Stock implements CorpIn {
    protected double price;

    public Stock(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

    public static double pricetoRUB(double price){
        AricmeticksLambda i = (n) -> (n * 60.32);
        return i.arigmetics(price);
    }

    public static boolean buyStock(Client cl, Corp c, double price){
        if (cl.getMoney() >= price) {
            AricmeticksLambda i = (n) -> ((n / 100) * 20);
            if (i.arigmetics(c.getPrice()) > price) {
                c.addPrice(price);
                System.out.println((cl.name + " купил акцию " + c.getName()));
                cl.minusMoney(price);
                cl.addStock(c);
                return true;
            } else {
                System.out.println("Не получиться купить акции " + c.getName());
                return false;
            }
        }else{
            System.out.println("Не достаточно денег!");
            return false;
        }
    }
    @Override
    public boolean addPrice(double n) {
        DualArif i = (nn, k) -> (nn + k);
        this.price = i.dualArig(this.price, n);
        return true;
    }

    @Override
    public boolean setPrice(double price){
        this.price = price;
        return true;
    }

    @Override
    public void info() {
        this.describe("Stock!");
    }
}
