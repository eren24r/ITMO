package Classes;

import Enums.ProductType;
import Enums.StoreType;
import Interfaces.ProductsIn;
import Interfaces.StoreIn;
import Exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Store implements StoreIn {
    private String name;
    private List<Product> products = new ArrayList<>();
    private double salary;
    private Korotishka bigBos;
    private StoreType type;
    private List<Korotishka> workers = new ArrayList<>();
    private long colSalaryProducts;
    Workers worksOfStore;

    public Store(String name, StoreType type, Korotishka bigBos){
        this.name = name;
        this.type = type;
        this.bigBos = bigBos;
        this.salary = 0;
        this.colSalaryProducts = 0;
        worksOfStore = new Workers();
        if(type == StoreType.big){
            for(int i = 0; i < 100; i++){
                List<ProductType> prType = new ArrayList<>();
                products.add(new Product(("Продукт №" + i).toString(), new Random().nextInt(1, 50), ProductType.values()[new Random().nextInt(0, ProductType.values().length)], this));
            }
        }
        if(type == StoreType.small){
            for(int i = 0; i < 50; i++){
                List<ProductType> prType = new ArrayList<>();
                products.add(new Product(("Продукт №" + i).toString(), new Random().nextInt(1, 25), ProductType.values()[new Random().nextInt(0, ProductType.values().length)], this));
            }
        }
    }

    @Override
    public void info() {
        this.describe("Магазин \"" + this.name + " \"");
    }

    public boolean getProducts(){
        for (Product pr: products){
            this.describe(pr.getName());
        }
        return true;
    }

    public String getName(){
        return this.name;
    }

    public double getSalary(){
        return salary;
    }

    public boolean work(){
        if(workers.size() != 0) {
            double s = 0;
            if (this.type == StoreType.big) {
                while (s < 100) {
                    int ss = new Random().nextInt(0, products.size());
                    this.salary = this.salary + products.get(ss).getPrice();
                    s = s + products.get(ss).getPrice();
                    this.colSalaryProducts = this.colSalaryProducts + 1;
                    products.remove(ss);
                }
            }
            if (this.type == StoreType.small) {
                while (s < 75) {
                    int ss = new Random().nextInt(0, products.size());
                    this.salary = this.salary + products.get(ss).getPrice();
                    s = s + products.get(ss).getPrice();
                    this.colSalaryProducts = this.colSalaryProducts + 1;
                    products.remove(ss);
                }
            }
            bigBos.addMoney(s / 100 * 35);
            for(Korotishka w: workers) {
                worksOfStore.getToWorkersSalary(w);
            }
            return true;
        }else{
            this.describe("Нету Работников!!!");
            return true;
        }
    }

    public boolean buyProducts() throws LimitProductsException{
        if(products.size() < 20) {
            if (type == StoreType.big) {
                long s = (colSalaryProducts + 100);
                for (long i = s; i < s + 50; i++) {
                    List<ProductType> prType = new ArrayList<>();
                    Product pr = new Product(("Продукт №" + i).toString(), new Random().nextInt(1, 50), ProductType.values()[new Random().nextInt(0, ProductType.values().length)], this);
                    if(this.salary >= (pr.getPrice() / 100 * 20)) {
                        this.salary = this.salary - (pr.getPrice() / 100 * 20);
                        products.add(pr);
                    }
                }
            }
            if (type == StoreType.small) {
                long s = (colSalaryProducts + 50);
                for (long i = s; i < s+25; i++) {
                    List<ProductType> prType = new ArrayList<>();
                    Product pr = new Product(("Продукт №" + i).toString(), new Random().nextInt(1, 50), ProductType.values()[new Random().nextInt(0, ProductType.values().length)], this);
                    if(this.salary >= (pr.getPrice() / 100 * 20)) {
                        this.salary = this.salary - (pr.getPrice() / 100 * 20);
                        products.add(pr);
                    }
                }
            }
            return true;
        } else throw new LimitProductsException("В данный момент у Магазина есть достаточно продуктов!");
    }

    public boolean minusMoney(double money){
        this.salary = this.salary - money;
        return true;
    }

    public class Workers{

        public boolean addWorker(Korotishka w) {
            if(workers.size() < 4) {
                if (bigBos.money >= 15) {
                    workers.add(w);
                    w.addMoney(15);
                    bigBos.minusMoney(15);
                    describe(w.name + " теперь работает на магазине \"" + name + "\"");
                    return true;
                } else {
                    describe("У Босса не хватает денег, чтобы нанят " + w.name + "-a на работу!");
                    return false;
                }
            }else {
                describe("Место в Магазине заполнено!");
                return false;
            }
        }

        public boolean getWorkers(){
            describe("Работники магазина \"" + name + "\"");
            for(Korotishka w: workers){
                describe(w.name);
            }
            return true;
        }

        public boolean getToWorkersSalary(Korotishka w){
            if(salary >= 15){
                w.addMoney(15);
                minusMoney(15);
                describe("Оплачано!");
                return true;
            }else{
                describe("Ой ой не хватает денег на зарплату Сотрудника " + w.name + "!Теперь он не будет работат!");
                workers.remove(w);
                return false;
            }
        }
    }
}
