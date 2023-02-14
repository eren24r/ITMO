package Interfaces;

public interface ProductsIn extends Info{
    boolean setPrice(double price);
    double getPrice();
    String getName();
    boolean getInWichStores();
}
