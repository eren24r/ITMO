package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;

public class OrganizationAddCommand implements Command {
    private String name = "add";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        String nm;
        Long crX = 0L;
        float crY = 0f;
        Float annualTurnover = 0F;
        String street = "", zipCode = "";

        Scanner snr = new Scanner(System.in);
        String[] dt = s.split(" ");
        if(dt.length > 1) {
            nm = dt[1];
            if(nm.indexOf('\"') < 0){
                Static.txt(colorize("Создание объекта " + dt[1], Attribute.BOLD()));
            }else {
                boolean isNm = false;
                Static.txt(colorize("Founded \". Try without \".", Attribute.RED_TEXT()));
                while (!isNm) {
                    String xY = snr.nextLine();
                    if(xY.indexOf('"') < 0){
                        isNm = true;
                        nm = xY;
                        Static.txt(colorize("Создание объекта " + nm, Attribute.BOLD()));
                    }
                }
            }
            //cordinates
            while (crX == 0L) {
                Static.txt(colorize("Кординаты Огранизации(x,y через пробел):", Attribute.BOLD()));
                String[] xY = snr.nextLine().split(" ");
                try {
                    if (Long.parseLong(xY[0]) >= (-811) && Float.parseFloat(xY[1]) != Math.E) {
                        crX = Long.parseLong(xY[0]);
                        crY = Float.parseFloat(xY[1]);
                        Static.txt(colorize("Кординаты добавлены!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                    } else {
                        Static.txt(colorize("Кордината X должно быть больше -811", Attribute.RED_TEXT()));
                    }
                } catch (Exception e) {
                    Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
                }
            }

            //annualTurnover
            while (annualTurnover == 0L) {
                Static.txt(colorize("Годовой оборот:", Attribute.BOLD()));
                String an = snr.nextLine();
                try {
                    if (Float.parseFloat(an) > 0) {
                        annualTurnover = Float.parseFloat(an);
                        Static.txt(colorize("Годовой оборот добавлен!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                    } else {
                        Static.txt(colorize("Годовой оборот должно быть больше 0", Attribute.RED_TEXT()));
                    }
                } catch (Exception e) {
                    Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
                }
            }

            //Type
            int tp = 0;
            while (tp == 0L) {
                Static.txt(colorize("Тип Огранизации:", Attribute.BOLD()));
                Static.txt(colorize("1 - PUBLIC", Attribute.BOLD()));
                Static.txt(colorize("2 - GOVERNMENT", Attribute.BOLD()));
                Static.txt(colorize("3 - TRUST", Attribute.BOLD()));
                String tptmp = snr.nextLine();
                try {
                    if (Integer.parseInt(tptmp) > 0 && Integer.parseInt(tptmp) <= 3) {
                        tp = Integer.parseInt(tptmp);
                        Static.txt(colorize("Тип Огранизации добавленa!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                    } else {
                        Static.txt(colorize("Тип огранизации введена некорректно!", Attribute.RED_TEXT()));
                    }
                } catch (Exception e) {
                    Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
                }
            }

            //Street
            while (street.length() == 0) {
                Static.txt(colorize("Адрес. Название улицы: ",Attribute.BOLD()));
                String strna = snr.nextLine();
                if (strna.length() != 0 && strna.length() <= 67 && strna.indexOf("\"") < 0) {
                    street = strna;
                    Static.txt(colorize("Название улицы добавленa!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                } else {
                    Static.txt(colorize("Длина строки не должна быть больше 67 и не должна содержать в себе \"!", Attribute.RED_TEXT()));
                }
            }
            //zipCode

            while (zipCode.length() == 0){
                Static.txt(colorize("Zip-Code: ", Attribute.BOLD()));
                String zp = snr.nextLine();
                if (zp.length() != 0 && zp.indexOf("\"") < 0) {
                    zipCode = zp;
                    Static.txt(colorize("Zip-Code добавлен!",Attribute.GREEN_TEXT(), Attribute.BOLD()));
                } else {
                    Static.txt(colorize("Founded \". Try without \".", Attribute.RED_TEXT()));
                }
            }

            Static.txt(colorize("Организация " + nm + " добавлена!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            Organization tmpOr = null;
            try {
                tmpOr = new Organization(nm, new Coordinates(crX, crY), annualTurnover, Static.orTp.getTypeById(tp), new Address(street, zipCode));
                mySet.add(tmpOr);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            Static.txt(colorize("Название Организации Некорректно!", Attribute.RED_TEXT()));
            return false;
        }
    }

    @Override
    public String des() {
        return "add element_name : добавить новый элемент в коллекцию";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
