package Classes;

import Enums.ProductType;
import Interfaces.ProductsIn;

import java.util.ArrayList;
import java.util.List;

public class Product implements ProductsIn {
    private double price;
    private String name;
    private ProductType type;
    private List<Store> inWichStores = new ArrayList<>();

    public Product(String name, double price, ProductType type){
        this.name = name;
        this.price = price;
        this.type = type;
    }
    public Product(String name, double price, ProductType type, Store s){
        this.name = name;
        this.price = price;
        this.type = type;
        this.inWichStores.add(s);
    }
    public boolean setPrice(double price){
        this.price = price;
        return true;
    }
    public double getPrice(){
        ///this.describe("Стоимость " + this.name + " " +  this.price + " Фертинг!");
        return this.price;
    }
    public String getName(){
        return this.name;
    }

    public boolean getInWichStores(){
        for(Store s: inWichStores){
            this.describe(s.getName());
        }
        return true;
    }

    @Override
    public void info() {
        this.describe("Продукт: " + name);
    }
}
