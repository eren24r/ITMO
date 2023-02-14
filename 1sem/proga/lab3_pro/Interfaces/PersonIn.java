package Interfaces;

import Classes.Stock;
import Classes.Store;
import Enums.StoreType;

public interface PersonIn extends Info {
    double getMoney();
    boolean addMoney(double money);
    boolean minusMoney(double money);
    boolean addStock(Stock cp);
    boolean listOfStocks();
    int countOfStocks();
    boolean createStore(String storeName,StoreType type);
    boolean listOfStores();
    Store getStore(int index);
    Store getStore();
}
