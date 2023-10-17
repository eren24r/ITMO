package сlasses;

/**
 * Адрес Организации
 */
public class Address {
    private String street; //Длина строки не должна быть больше 67, Поле не может быть null
    private String zipCode; //Поле не может быть null

    public Address(){

    }

    public Address(String street, String zipCode){
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }
    public String getZipCode(){
        return zipCode;
    }
}
