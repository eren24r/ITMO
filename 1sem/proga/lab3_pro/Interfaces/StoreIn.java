package Interfaces;
import Exception.*;

public interface StoreIn extends Info{
    boolean getProducts();
    double getSalary();
    String getName();
    boolean minusMoney(double money);
    boolean work();
    boolean buyProducts() throws LimitProductsException;
}
