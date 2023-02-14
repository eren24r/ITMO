package Classes;

import Datas.ParseIng;
import MainProgram.Main;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Команды
 */
public class Commands {
    public static int sizeOfSetNotSaved = 0;
    public static void help(){
        String s = "help : справка по доступным командам\ninfo : информация о коллекции\nshow : все элементы коллекции в строковом представлении\nadd element_name : добавить новый элемент в коллекцию\nupdate id new_element : обновить значение элемента коллекции\nremove_by_id id : удалить элемент из коллекции по его id\nclear : очистить коллекцию\nsave : сохранить коллекцию в файл\nexecute_script file_name : считать и исполнить скрипт из указанного файла\nsum_of_annual_turnover : сумма значений поля annualTurnover для всех элементов коллекции\naverage_of_annual_turnover : среднее значение поля annualTurnover для всех элементов коллекции\nprint_descending : вывести элементы коллекции в порядке убывания\n\nexit : завершить программу (без сохранения в файл)";
        txt(s);
    }

    public static void info(Set<Organization> s){
        txt("Класс:    Organization");
        txt("id - identification number");
        txt("name - Название Огранизации");
        txt("coordinates - кординаты огранизации");
        txt("creationDate - дата создания");
        txt("annualTurnover - годовой оборот");
        txt("type - тип организации");
        txt("postalAddress - адрес");

        txt("Количество элементов Колекции: " + s.size());
    }

    public static void show(HashSet<Organization> s){
        for(Organization o: s){
            System.out.println(o.toString());
        }
    }

    public static Organization add(String name){
        String nm;
        Long crX = 0L;
        float crY = 0f;
        Float annualTurnover = 0F;
        String street = "", zipCode = "";

        Scanner s = new Scanner(System.in);
        String[] dt = name.split(" ");
        nm = dt[1];
        if(nm.length() > 0) {
            txt("Создание объекта " + dt[1]);
            //cordinates
            while (crX == 0L) {
                txt("Кординаты Огранизации(x,y через пробел):");
                String[] xY = s.nextLine().split(" ");
                try {
                    if (Long.parseLong(xY[0]) >= (-811) && Float.parseFloat(xY[1]) != Math.E) {
                        crX = Long.parseLong(xY[0]);
                        crY = Float.parseFloat(xY[1]);
                        txt("Кординаты добавлены!");
                    } else {
                        txt("Кордината X должно быть больше -811");
                    }
                } catch (Exception e) {
                    txt("Ошибка формата!");
                }
            }

            //annualTurnover
            while (annualTurnover == 0L) {
                txt("Годовой оборот:");
                String an = s.nextLine();
                try {
                    if (Float.parseFloat(an) > 0) {
                        annualTurnover = Float.parseFloat(an);
                        txt("Годовой оборот добавлен!");
                    } else {
                        txt("Годовой оборот должно быть больше 0");
                    }
                } catch (Exception e) {
                    txt("Ошибка формата!");
                }
            }

            //Type
            int tp = 0;
            while (tp == 0L) {
                txt("Тип Огранизации:\n1 - PUBLIC\n2 - GOVERNMENT\n3 - TRUST");
                String tptmp = s.nextLine();
                try {
                    if (Integer.parseInt(tptmp) > 0 && Integer.parseInt(tptmp) <= 3) {
                        tp = Integer.parseInt(tptmp);
                        txt("Тип Огранизации добавленa!");
                    } else {
                        txt("Тип огранизации введена некорректно!");
                    }
                } catch (Exception e) {
                    txt("Ошибка формата!");
                }
            }

            //Street
            while (street.length() == 0L) {
                txt("Адрес. Название улицы: ");
                String strna = s.nextLine();
                if (strna.length() <= 67) {
                    street = strna;
                    txt("Название улицы добавленa!");
                } else {
                    txt("Длина строки не должна быть больше 67!");
                }
            }
            //zipCode
            txt("Zip-Code: ");
            zipCode = s.nextLine();
            txt("Zip-Code добавлен!"); Main.nl();

            txt("Организация " + nm + " добавлена!");
            sizeOfSetNotSaved = sizeOfSetNotSaved + 1;
            Organization tmpOr = new Organization(nm, new Coordinates(crX, crY), annualTurnover, OrganizationType.getTypeById(tp), new Address(street, zipCode));
            return tmpOr;
        }else {
            txt("Название Организации Некорректно!");
            return null;
        }
    }

    public static void updateById(HashSet<Organization> set, String s){
        Scanner scr = new Scanner(System.in);
        String[] xY = s.split(" ");
        boolean b = false;
        try {
            if (Integer.parseInt(xY[1]) >= 0 && xY[2].length() != 0) {
                for(Organization o: set){
                    if(o.getId() == Integer.parseInt(xY[1])){
                        b = o.updateName(xY[2]);
                        break;
                    }
                }
            }
            if(b == true){
                txt("Объект изменено!");
            }
        } catch (Exception e) {
            txt("Ошибка формата!");
        }
    }

    public static void remove_by_id(HashSet<Organization> set, String s){
        String[] xY = s.split(" ");
        Object bb = null;
        boolean b = false;
        try {
            if (Integer.parseInt(xY[1]) >= 0) {
                for(Organization o: set){
                    if(o.getId() == Integer.parseInt(xY[1])){
                        b = true;
                        bb = o;
                        break;
                    }
                }
            }
            if(b == true){
                set.remove(bb);
                txt("Объект удалено!");
            }
        } catch (Exception e) {
            txt("Ошибка формата!");
        }
    }

    public static Organization getById(HashSet<Organization> s, int i){
        Organization k = null;
        for(Organization o: s){
            if(o.getId() == i){
                k = o;
            }
        }
        return k;
    }

    public static void save(HashSet<Organization> set){
        ParseIng.dataEraser();
        HashMap<Integer, Organization> al = Commands.sort(set);
        for(Organization o: al.values()){
            ParseIng.csvWriter(o.toStringCSV());
        }
        txt("Коллекция успешно сохранено!");
    }

    public static void execute_script(HashSet<Organization> mySet, String s){
        String[] xY = s.split(" ");
        boolean b = false;
        boolean isScript = true;
        try {
            if (xY[1].length()!=0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(("Scripts/" + xY[1])))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Commands.commandsEditor(mySet, line, isScript);
                        if (line.equals("save")){
                            Commands.save(mySet);
                            mySet = ParseIng.getOrganizationFromCsv();
                            Commands.sizeOfSetNotSaved = 0;
                            nl();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка в файле или неправильный путь!");
                }
            }
        } catch (Exception e) {
            txt("Ошибка формата!");
        }
    }

    public static Float sum_of_annual_turnover(HashSet<Organization> set){
        Float s = 0F;
        for(Organization o: set){
            s = s + o.getAnnualTurnover();
        }
        return s;
    }
    public static Float average_of_annual_turnover(HashSet<Organization> set){
        if(set.size() > 0) {
            return Commands.sum_of_annual_turnover(set) / set.size();
        }
        return null;
    }

    public static HashMap<Integer, Organization> sort(HashSet<Organization> set){
        HashMap<Integer, Organization> al = new HashMap<>();
        for (Organization o: set){
            al.put(o.getId(), o);
        }
        return al;
    }

    public static void print_descending(HashSet<Organization> set){
        HashMap<Integer, Organization> al = Commands.sort(set);
        String s = "";
        for(Organization o: al.values()){
            s = o.getName() + "\n" + s;
        }
        txt(s);
    }

    public static int getSizeofSet(Set<Organization> s){
        return s.size();
    }

    public static void txt(String s){
        System.out.println(s);
    }

    public static void nl(){
        System.out.println();
    }

    public static void commandsEditor(HashSet<Organization> mySet, String line, boolean IsScript){
        if (line.equals("help")) {
            Commands.help();
            nl();
        }
        if (line.equals("info")) {
            Commands.info(mySet);
            nl();
        }
        if (line.equals("show")) {
            Commands.show(mySet);
            nl();
        }
        if (!IsScript && line.indexOf("add") != -1) {
            mySet.add(Commands.add(line));
            nl();
        }
        if (line.indexOf("update") != -1){
            Commands.updateById(mySet, line);nl();
        }
        if (line.indexOf("remove_by_id") != -1){
            Commands.remove_by_id(mySet, line);nl();
        }
        if (line.equals("clear")) {
            mySet.clear();
            System.out.println("Коллекция успешно удалено!");
            nl();
        }
        if(!IsScript && line.indexOf("execute_script") != -1){
            Commands.execute_script(mySet, line);
            nl();
        }
        if(line.equals("sum_of_annual_turnover")){
            txt("sum of annual turnover: " + Commands.sum_of_annual_turnover(mySet)); nl();
        }
        if(line.equals("average_of_annual_turnover")){
            txt("average of annual turnover: " + Commands.average_of_annual_turnover(mySet)); nl();
        }
        if(line.equals("print_descending")){
            Commands.print_descending(mySet);
        }
    }
}
