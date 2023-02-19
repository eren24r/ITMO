package Classes;

import Datas.ParseIng;
import MainProgram.Main;
import com.diogonunes.jcolor.Attribute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.diogonunes.jcolor.Ansi.colorize;

//todo change_serialization_type JSON|CSV
/**
 * Команды
 */
public class Commands {
    public static int sizeOfSetNotSaved = 0;
    public static void help(){
        String s = "help : справка по доступным командам\ninfo : информация о коллекции\nshow : все элементы коллекции в строковом представлении\nadd element_name : добавить новый элемент в коллекцию\nupdate id new_element : обновить значение элемента коллекции\nremove_by_id id : удалить элемент из коллекции по его id\nclear : очистить коллекцию\nsave : сохранить коллекцию в файл\nexecute_script file_name : считать и исполнить скрипт из указанного файла\nsum_of_annual_turnover : сумма значений поля annualTurnover для всех элементов коллекции\naverage_of_annual_turnover : среднее значение поля annualTurnover для всех элементов коллекции\nprint_descending : вывести элементы коллекции в порядке убывания\n\nexit : завершить программу (без сохранения в файл)";
        txt((colorize(s, Attribute.GREEN_TEXT())));
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

        txt(colorize("Количество элементов Колекции: " + s.size(), Attribute.GREEN_TEXT()));
    }

    public static void show(HashSet<Organization> s){
        for(Organization o: s){
            System.out.println(colorize(o.toString(), Attribute.BLUE_TEXT()));
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
            txt(colorize("Создание объекта " + dt[1], Attribute.BOLD()));
            //cordinates
            while (crX == 0L) {
                txt(colorize("Кординаты Огранизации(x,y через пробел):", Attribute.BOLD()));
                String[] xY = s.nextLine().split(" ");
                try {
                    if (Long.parseLong(xY[0]) >= (-811) && Float.parseFloat(xY[1]) != Math.E) {
                        crX = Long.parseLong(xY[0]);
                        crY = Float.parseFloat(xY[1]);
                        txt(colorize("Кординаты добавлены!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                    } else {
                        txt(colorize("Кордината X должно быть больше -811", Attribute.RED_TEXT()));
                    }
                } catch (Exception e) {
                    txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
                }
            }

            //annualTurnover
            while (annualTurnover == 0L) {
                txt(colorize("Годовой оборот:", Attribute.BOLD()));
                String an = s.nextLine();
                try {
                    if (Float.parseFloat(an) > 0) {
                        annualTurnover = Float.parseFloat(an);
                        txt(colorize("Годовой оборот добавлен!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                    } else {
                        txt(colorize("Годовой оборот должно быть больше 0", Attribute.RED_TEXT()));
                    }
                } catch (Exception e) {
                    txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
                }
            }

            //Type
            int tp = 0;
            while (tp == 0L) {
                txt(colorize("Тип Огранизации:\n1 - PUBLIC\n2 - GOVERNMENT\n3 - TRUST", Attribute.BOLD()));
                String tptmp = s.nextLine();
                try {
                    if (Integer.parseInt(tptmp) > 0 && Integer.parseInt(tptmp) <= 3) {
                        tp = Integer.parseInt(tptmp);
                        txt(colorize("Тип Огранизации добавленa!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                    } else {
                        txt(colorize("Тип огранизации введена некорректно!", Attribute.RED_TEXT()));
                    }
                } catch (Exception e) {
                    txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
                }
            }

            //Street
            while (street.length() == 0) {
                txt(colorize("Адрес. Название улицы: ",Attribute.BOLD()));
                String strna = s.nextLine();
                if (strna.length() != 0 && strna.length() <= 67) {
                    street = strna;
                    txt(colorize("Название улицы добавленa!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                } else {
                    txt(colorize("Длина строки не должна быть больше 67!", Attribute.RED_TEXT()));
                }
            }
            //zipCode
            txt(colorize("Zip-Code: ",Attribute.BOLD()));
            zipCode = s.nextLine();
            txt(colorize("Zip-Code добавлен!",Attribute.GREEN_TEXT(), Attribute.BOLD())); Main.nl();

            txt(colorize("Организация " + nm + " добавлена!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            sizeOfSetNotSaved = sizeOfSetNotSaved + 1;
            Organization tmpOr = new Organization(nm, new Coordinates(crX, crY), annualTurnover, OrganizationType.getTypeById(tp), new Address(street, zipCode));
            return tmpOr;
        }else {
            txt(colorize("Название Организации Некорректно!", Attribute.RED_TEXT()));
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
                txt(colorize("Объект изменено!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            }
        } catch (Exception e) {
            txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
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
                txt(colorize("Объект удалено!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            }
        } catch (Exception e) {
            txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
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
        txt(colorize("Коллекция успешно сохранено!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
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
                    System.out.println(colorize("Ошибка в файле или неправильный путь!", Attribute.RED_TEXT()));
                }
            }
        } catch (Exception e) {
            txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
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
