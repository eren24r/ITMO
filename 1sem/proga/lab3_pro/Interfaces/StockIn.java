package Interfaces;

public interface StockIn extends Info{
    boolean setPrice(double Price);
    double getPrice();
    boolean addPrice(double n);
}
