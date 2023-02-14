package Enums;

public enum StoreType {
    small(100),
    big(300);

    public double price;
    StoreType(double price){
        this.price = price;
    }
}
