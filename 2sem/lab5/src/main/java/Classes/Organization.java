package Classes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

import Datas.ParseIng;
import MainProgram.*;

public class Organization {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле не может быть null

    public Organization(int id, String name, Coordinates coordinates, LocalDateTime creationDate, Float annualTurnover, OrganizationType type, Address postalAddress){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
        this.creationDate = creationDate;
    }

    public Organization(String name, Coordinates coordinates, Float annualTurnover, OrganizationType type, Address postalAddress){
        this.id = ParseIng.getSize();
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
        this.creationDate = LocalDateTime.now();
    }

    public String getCreationDate(){
        return this.creationDate.format(ParseIng.dateTimeFormatter);
    }

    public String toStringCSV() {
        return (String) (this.id + "," + this.name + "," + this.coordinates.getX() + "," + this.coordinates.getY() + "," + this.getCreationDate() + "," + this.annualTurnover + "," + this.type.getId() + "," + this.postalAddress.getStreet() + "," + this.postalAddress.getZipCode());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

