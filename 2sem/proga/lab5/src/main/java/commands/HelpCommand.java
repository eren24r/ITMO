package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import static com.diogonunes.jcolor.Ansi.colorize;

public class HelpCommand {
    public boolean help(){
        String s = "help : справка по доступным командам\ninfo : информация о коллекции\nshow : все элементы коллекции в строковом представлении\nadd element_name : добавить новый элемент в коллекцию\nupdate id new_element : обновить значение элемента коллекции\nremove_by_id id : удалить элемент из коллекции по его id\nclear : очистить коллекцию\nsave : сохранить коллекцию в файл\nexecute_script file_name : считать и исполнить скрипт из указанного файла\nsum_of_annual_turnover : сумма значений поля annualTurnover для всех элементов коллекции\naverage_of_annual_turnover : среднее значение поля annualTurnover для всех элементов коллекции\nprint_descending : вывести элементы коллекции в порядке убывания\nchange_serialization_type JSON|CSV\nSerialization_type\n\nexit : завершить программу (без сохранения в файл)";
        Static.txt((colorize(s, Attribute.GREEN_TEXT())));
        return true;
    }
}
