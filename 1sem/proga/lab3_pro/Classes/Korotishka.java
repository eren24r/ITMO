package Classes;

import Enums.StoreType;
import Interfaces.AricmeticksLambda;
import Interfaces.DualArif;
import Interfaces.PersonIn;

import java.util.ArrayList;
import java.util.List;

public class Korotishka implements PersonIn{
    protected String name;
    protected double money;
    protected List<Stock> cp = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();

    public Korotishka(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public double getMoney(){
        return this.money;
    }

    public boolean addMoney(double money){
        if (money > 0) {
            DualArif sumMoney = (n, k) -> (n + k);
            this.money = sumMoney.dualArig(this.money, money);
            this.describe(("Вам начислино " + money + " USD"));
            return true;
        }else{
            return false;
        }
    }

    public boolean minusMoney(double money){
        DualArif minMoney = (n, k) -> (n - Math.abs(k));
        this.money = minMoney.dualArig(this.money, money);
        return true;
    }

    public boolean addStock(Stock cp){
        this.cp.add(cp);
        return true;
    }

    public boolean listOfStocks(){
        String s = "";
        if(!this.cp.isEmpty()){
            for(Stock e: this.cp){
                s = s + (e.getName()) + " ";
            }
            this.describe(s);
            return true;
        }else {
            this.describe("Нету акций у тебя!");
            return false;
        }
    }

    public boolean listOfStores(){
        String s = "";
        if(!this.stores.isEmpty()){
            for(Store e: this.stores){
                s = s + (e.getName()) + " ";
            }
            this.describe(s);
            return true;
        }else {
            this.describe("Нету акций у тебя!");
            return false;
        }
    }

    public Store getStore(int index){
        return stores.get(index);
    }

    public Store getStore(){
        return stores.get(0);
    }

    public int countOfStocks(){
        return this.cp.size();
    }

    public boolean createStore(String storeName, StoreType type) {
        if (this.money >= type.price) {
            Store creaedStore = new Store(storeName, type, this);
            stores.add(creaedStore);
            this.describe(this.name + "создал свой магазин \"" + storeName + "\"");
            this.minusMoney(type.price);
            return true;
        }else{
            this.describe("Не хватит денег!");
            return false;
        }
    }

    public boolean buyStock(Stock c, double price){
        if (this.getMoney() >= price) {
            AricmeticksLambda prosentOf20 = (n) -> ((n / 100) * 20);
            if (prosentOf20.arigmetics(c.getPrice()) > price) {
                c.addPrice(price);
                System.out.println((this.name + " купил акцию " + c.getName()));
                this.minusMoney(price);
                this.addStock(c);
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
    public void info() {
        this.describe("I am a sample korotishka! I want to be a rich!");
    }
}
