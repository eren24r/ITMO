package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class OrganizationAddCommand implements Command {
    private String name = "add";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        String nm;
        Long crX = 0L;
        float crY = 0f;
        Float annualTurnover = 0F;
        String street = "", zipCode = "";
        boolean isPr = false;
        if (Static.isPrint == 0) {
            isPr = true;
            Static.isPrint = 1;
        } else {
            isPr = false;
        }

        Scanner snr = new Scanner(System.in);
        String[] dt = s.split(" ");
        if (dt.length == 2) {
            nm = dt[1];
            if (nm.indexOf('\"') < 0) {
                Static.txt("Создание объекта " + dt[1], Attribute.BOLD());
            } else {
                boolean isNm = false;
                Static.txt("Founded \". Try without \".", Attribute.RED_TEXT());
                while (!isNm) {
                    String xY = snr.nextLine();
                    if (xY.indexOf('"') < 0) {
                        isNm = true;
                        nm = xY;
                        Static.txt("Создание объекта " + nm, Attribute.BOLD());
                    }
                }
            }
            //cordinates
            while (crX == 0L) {
                Static.txt("Кординаты Огранизации(x,y через пробел):", Attribute.BOLD());
                String[] xY = snr.nextLine().split(" ");
                try {
                    if (Long.parseLong(xY[0]) >= (-811) && Float.parseFloat(xY[1]) != Math.E) {
                        crX = Long.parseLong(xY[0]);
                        crY = Float.parseFloat(xY[1]);
                        Static.txt("Кординаты добавлены!", Attribute.BOLD());
                    } else {
                        Static.txt("Кордината X должно быть больше -811", Attribute.RED_TEXT());
                    }
                } catch (Exception e) {
                    Static.txt("Ошибка формата!", Attribute.RED_TEXT());
                }
            }

            //annualTurnover
            while (annualTurnover == 0L) {
                Static.txt("Годовой оборот:", Attribute.BOLD());
                String an = snr.nextLine();
                try {
                    if (Float.parseFloat(an) > 0) {
                        annualTurnover = Float.parseFloat(an);
                        Static.txt("Годовой оборот добавлен!", Attribute.BOLD());
                    } else {
                        Static.txt("Годовой оборот должно быть больше 0", Attribute.RED_TEXT());
                    }
                } catch (Exception e) {
                    Static.txt("Ошибка формата!", Attribute.RED_TEXT());
                }
            }

            //Type
            int tp = 0;
            while (tp == 0L) {
                Static.txt("Тип Огранизации:", Attribute.BOLD());
                Static.txt("1 - PUBLIC", Attribute.BOLD());
                Static.txt("2 - GOVERNMENT", Attribute.BOLD());
                Static.txt("3 - TRUST", Attribute.BOLD());
                String tptmp = snr.nextLine();
                try {
                    if (Integer.parseInt(tptmp) > 0 && Integer.parseInt(tptmp) <= 3) {
                        tp = Integer.parseInt(tptmp);
                        Static.txt("Тип Огранизации добавленa!", Attribute.BOLD());
                    } else {
                        Static.txt("Тип огранизации введена некорректно!", Attribute.RED_TEXT());
                    }
                } catch (Exception e) {
                    Static.txt("Ошибка формата!", Attribute.RED_TEXT());
                }
            }

            //Street
            while (street.length() == 0) {
                Static.txt("Адрес. Название улицы: ", Attribute.BOLD());
                String strna = snr.nextLine();
                if (strna.length() != 0 && strna.length() <= 67 && strna.indexOf("\"") < 0) {
                    street = strna;
                    Static.txt("Название улицы добавленa!", Attribute.BOLD());
                } else {
                    Static.txt("Длина строки не должна быть больше 67 и не должна содержать в себе \"!", Attribute.RED_TEXT());
                }
            }
            //zipCode

            while (zipCode.length() == 0) {
                Static.txt("Zip-Code: ", Attribute.BOLD());
                String zp = snr.nextLine();
                if (zp.length() != 0 && zp.indexOf("\"") < 0) {
                    zipCode = zp;
                    Static.txt("Zip-Code добавлен!", Attribute.BOLD());
                } else {
                    Static.txt("Founded \". Try without \".", Attribute.RED_TEXT());
                }
            }

            Organization tmpOr = null;
            try {
                tmpOr = new Organization(nm, new Coordinates(crX, crY), annualTurnover, Static.orTp.getTypeById(tp), new Address(street, zipCode));
                mySet.add(tmpOr);
                if (isPr) {
                    Static.isPrint = 0;
                }
                Static.txt("Организация " + nm + " добавлена!", Attribute.BOLD());
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (dt.length == 8) {
            String nameNew = dt[1];
            Coordinates cr = new Coordinates(Long.parseLong(dt[2]), Float.parseFloat(dt[3]));
            /*OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt.get(6)));*/
            Float anT = Float.parseFloat(dt[4]);
            OrganizationType ortp = OrganizationType.PUBLIC;
            OrganizationType o_type = ortp.getTypeByName(dt[5].toUpperCase());
            Address ad = new Address(dt[6], dt[7]);
            Organization data = null;
            try {
                data = new Organization(nameNew, cr, anT, o_type, ad);
                mySet.add(data);
                if (isPr) {
                    Static.isPrint = 0;
                }
                Static.txt("Организация " + nameNew + " добавлена!", Attribute.BOLD());
                return true;
            } catch (IOException e) {
                Static.txt("Данные Организации Некорректно!", Attribute.RED_TEXT());
                return false;
            }
        } else {
            Static.txt("Название Организации Некорректно!", Attribute.RED_TEXT());
            if (isPr) {
                Static.isPrint = 0;
            }
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
