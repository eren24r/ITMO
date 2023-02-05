package Interfaces;

import Classes.Corp;

public interface ClientIn extends Info{
    double getMoney();
    boolean addMoney(double money);
    boolean minusMoney(double money);
    boolean addStock(Corp cp);
    boolean stocks();
    int countOfStocks();
}
