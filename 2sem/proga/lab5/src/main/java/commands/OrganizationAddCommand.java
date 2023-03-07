package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;

import java.io.IOException;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;

public class OrganizationAddCommand {
    public Organization add(String name) throws IOException {
        String nm;
        Long crX = 0L;
        float crY = 0f;
        Float annualTurnover = 0F;
        String street = "", zipCode = "";

        Scanner s = new Scanner(System.in);
        String[] dt = name.split(" ");
        if(dt.length > 1) {
            nm = dt[1];
            Static.txt(colorize("Создание объекта " + dt[1], Attribute.BOLD()));
            //cordinates
            while (crX == 0L) {
                Static.txt(colorize("Кординаты Огранизации(x,y через пробел):", Attribute.BOLD()));
                String[] xY = s.nextLine().split(" ");
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
                String an = s.nextLine();
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
                String tptmp = s.nextLine();
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
                String strna = s.nextLine();
                if (strna.length() != 0 && strna.length() <= 67) {
                    street = strna;
                    Static.txt(colorize("Название улицы добавленa!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
                } else {
                    Static.txt(colorize("Длина строки не должна быть больше 67!", Attribute.RED_TEXT()));
                }
            }
            //zipCode
            Static.txt(colorize("Zip-Code: ",Attribute.BOLD()));
            zipCode = s.nextLine();
            Static.txt(colorize("Zip-Code добавлен!",Attribute.GREEN_TEXT(), Attribute.BOLD())); Static.nl();

            Static.txt(colorize("Организация " + nm + " добавлена!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            Organization tmpOr = new Organization(nm, new Coordinates(crX, crY), annualTurnover, Static.orTp.getTypeById(tp), new Address(street, zipCode));
            return tmpOr;
        }else {
            Static.txt(colorize("Название Организации Некорректно!", Attribute.RED_TEXT()));
            return null;
        }
    }
}
