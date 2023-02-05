package Classes;

import Interfaces.AricmeticksLambda;
import Interfaces.ClientIn;
import Interfaces.DualArif;

import java.util.ArrayList;
import java.util.List;

public class Client implements ClientIn {
    protected String name;
    protected double money = 0;
    protected List<Corp> cp = new ArrayList<>();

    public Client(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public double getMoney(){
        return this.money;
    }

    public boolean addMoney(double money){
        if (money > 0) {
            DualArif i = (n, k) -> (n + k);
            this.money = i.dualArig(this.money, money);
            this.describe(("Вам начислино " + money + " USD"));
            return true;
        }else{
            return false;
        }
    }

    public boolean minusMoney(double money){
        DualArif i = (n, k) -> (n - Math.abs(k));
        this.money = i.dualArig(this.money, money);
        return true;
    }

    public boolean addStock(Corp cp){
        this.cp.add(cp);
        return true;
    }

    public boolean stocks(){
        String s = "";
        if(!this.cp.isEmpty()){
            for(Corp e: this.cp){
                s = s + (e.getName()) + " ";
            }
            this.describe(s);
            return true;
        }else {
            this.describe("Нету акций у тебя!");
            return false;
        }
    }

    public int countOfStocks(){
        return this.cp.size();
    }

    @Override
    public void info() {
        this.describe("I am a sample people! I want to be a rich!");
    }
}
