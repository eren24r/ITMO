package сlasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import statics.Static;

import java.io.*;
import java.time.LocalDateTime;

/**
 * Организация
 */
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

    public Organization(String name, Coordinates coordinates, Float annualTurnover, OrganizationType type, Address postalAddress) throws IOException {
        this.id = this.sizeOffDb() + 1;
        this.saveSizeOfNewDb();
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
        this.creationDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getCreationDate(){
        return this.creationDate.format(Static.dateTimeFormatter);
    }

    public String toStringCSV() {
        return (String) ("\"" + this.id + "\"," + "\"" + this.name + "\"," + "\"" + this.coordinates.getX() + "\"," + "\"" + this.coordinates.getY() + "\"," + "\"" + this.getCreationDate() + "\"," + "\"" + this.annualTurnover + "\"," + "\"" + this.type.name() + "\"," + "\"" + this.postalAddress.getStreet() + "\"," + "\"" + this.postalAddress.getZipCode() + "\"");
    }

    public String toStringJson() {
        ObjectMapper objmp = new ObjectMapper();
        try {
            return objmp.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateName(String s){
        this.name = s;
        return true;
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

    public int sizeOffDb() throws IOException {
        int sz = 0;
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(".tmp/files/bd.txt"));
        String line;
        line = reader.readLine();
        sz = Integer.parseInt(line.split(" ")[0]);
        return sz;
    }

    public boolean saveSizeOfNewDb(){
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(".tmp/files/bd.txt"))) {
            writter.write(String.valueOf(this.id));
        } catch (IOException e) {
            System.out.println("Ошибка!");
            return false;
        }
        return true;
    }

}

