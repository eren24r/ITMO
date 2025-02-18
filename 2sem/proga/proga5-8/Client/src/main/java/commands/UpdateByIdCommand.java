package commands;

import com.diogonunes.jcolor.Attribute;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class UpdateByIdCommand{

    private String name = "update";

    public ObjectResAns doo(HashSet<Organization> mySet, String s) {
        boolean b = true;
        int indexOfs = Integer.parseInt(s.split(" ")[1]);

        Long crX = 0L;
        float crY = 0f;
        Float annualTurnover = 0F;
        String street = "", zipCode = "";
        boolean isPr = false;
        String name = "";

        Scanner snr = new Scanner(System.in);
        String[] dt = s.split(" ");
        if (dt.length == 2) {
            while (name.length() == 0) {
                System.out.println("Name: ");
                String nm = snr.nextLine();
                if (nm.length() != 0 && nm.indexOf("\"") < 0) {
                    name = nm;
                    System.out.println("Zip-Code добавлен!");
                } else {
                    System.out.println("Founded \". Try without \".");
                }
            }

            //cordinates
            while (crX == 0L) {
                System.out.println("Кординаты Огранизации(x,y через пробел):");
                String[] xY = snr.nextLine().split(" ");
                try {
                    if (Long.parseLong(xY[0]) >= (-811) && Float.parseFloat(xY[1]) != Math.E) {
                        crX = Long.parseLong(xY[0]);
                        crY = Float.parseFloat(xY[1]);
                        System.out.println("Кординаты добавлены!");
                    } else {
                        System.out.println("Кордината X должно быть больше -811");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка формата!");
                }
            }

            //annualTurnover
            while (annualTurnover == 0L) {
                System.out.println("Годовой оборот:");
                String an = snr.nextLine();
                try {
                    if (Float.parseFloat(an) > 0) {
                        annualTurnover = Float.parseFloat(an);
                        System.out.println("Годовой оборот добавлен!");
                    } else {
                        System.out.println("Годовой оборот должно быть больше 0");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка формата!");
                }
            }

            //Type
            int tp = 0;
            while (tp == 0L) {
                System.out.println("Тип Огранизации:");
                System.out.println("1 - PUBLIC");
                System.out.println("2 - GOVERNMENT");
                System.out.println("3 - TRUST");
                String tptmp = snr.nextLine();
                try {
                    if (Integer.parseInt(tptmp) > 0 && Integer.parseInt(tptmp) <= 3) {
                        tp = Integer.parseInt(tptmp);
                        System.out.println("Тип Огранизации добавленa!");
                    } else {
                        System.out.println("Тип огранизации введена некорректно!");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка формата!");
                }
            }

            //Street
            while (street.length() == 0) {
                System.out.println("Адрес. Название улицы: ");
                String strna = snr.nextLine();
                if (strna.length() != 0 && strna.length() <= 67 && strna.indexOf("\"") < 0) {
                    street = strna;
                    System.out.println("Название улицы добавленa!");
                } else {
                    System.out.println("Длина строки не должна быть больше 67 и не должна содержать в себе \"!");
                }
            }
            //zipCode
            while (zipCode.length() == 0) {
                Static.txt("Zip-Code: ", Attribute.BOLD());
                String zp = snr.nextLine();
                if (zp.length() != 0 && zp.indexOf("\"") < 0) {
                    zipCode = zp;
                    System.out.println("Zip-Code добавлен!");
                } else {
                    System.out.println("Founded \". Try without \".");
                }
            }
            if (isPr) {
                Static.isPrint = 0;
            }
            return new ObjectResAns("update \"" + indexOfs + "\",\"" + name + "\",\"" + crX + "\",\"" + crY + "\",\"" + annualTurnover + "\",\"" + Static.orTp.getTypeById(tp).name() + "\",\"" + street + "\",\"" + zipCode + "\"", true, Static.user);
        } else if (dt.length >= 9) {
            String nameNew = dt[2];
            Coordinates cr = new Coordinates(Long.parseLong(dt[3]), Float.parseFloat(dt[4]));
            /*OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt.get(6)));*/
            Float anT = Float.parseFloat(dt[5]);
            OrganizationType ortp = OrganizationType.PUBLIC;
            OrganizationType o_type = ortp.getTypeByName(dt[6].toUpperCase());
            Address ad = new Address(dt[7], dt[8]);
            if (isPr) {
                Static.isPrint = 0;
            }
            return new ObjectResAns("update \"" + indexOfs + "\",\"" + nameNew + "\",\"" + cr.getX() + "\",\"" + cr.getY() + "\",\"" + anT + "\",\"" + o_type.name() + "\",\"" + ad.getStreet() + "\",\"" + ad.getZipCode() + "\"", true, Static.user);
        }else {
            System.out.println("Название Организации Некорректно!");
            if (isPr) {
                Static.isPrint = 0;
            }
            return new ObjectResAns("Название Организации Некорректно!", false, Static.user);
        }
    }
}
