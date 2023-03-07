package statics;

import сlasses.OrganizationType;

import java.time.format.DateTimeFormatter;

public  class Static {
    public static OrganizationType orTp = OrganizationType.PUBLIC;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static String fileName = "Datas/";

    public static void txt(String s){
        System.out.println(s);
    }

    public static void nl(){
        System.out.println();
    }
}