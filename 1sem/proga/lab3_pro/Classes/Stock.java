package Classes;

import Enums.StockType;
import Interfaces.StockIn;
import Interfaces.AricmeticksLambda;
import Interfaces.DualArif;

public class Stock implements StockIn {
    protected double price;
    protected String name;
    protected StockType type = StockType.NORMAL;

    public Stock(String name, double price){
        this.name = name; this.price = price;
    }

    protected void setType(StockType type){
        this.type = type;
    }
    public String getName(){
        return this.name;
    }

    public StockType getType(){
        return this.type;
    }

    public double getPrice(){
        return this.price;
    }

    public double pricetoRUB(){
        AricmeticksLambda priceToRub = (n) -> (n * 60.32);
        return priceToRub.arigmetics(this.price);
    }

    @Override
    public boolean addPrice(double n) {
        DualArif summMoney = (nn, k) -> (nn + k);
        this.price = summMoney.dualArig(this.price, n);
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