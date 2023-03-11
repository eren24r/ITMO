package commands;

/**
 * Команды
 */


public class Commands {
/*
    HelpCommand hpCmd = new HelpCommand();
    InfoCommands inCmd = new InfoCommands();
    OrganizationAddCommand orAddCmd = new OrganizationAddCommand();
    PrintDescendingCommand prDesCmd = new PrintDescendingCommand();
    RemoveByIdCommand rmIdCmd = new RemoveByIdCommand();
    ShowInfoCommand shInCmd = new ShowInfoCommand();
    SumCommand smCmd = new SumCommand();
    UpdateByIdCommand upIdCmd = new UpdateByIdCommand();

    public boolean commandsEditor(HashSet<Organization> mySet, String line, int isCsv) throws IOException {
        if (line.equals("help")) {
            hpCmd.help();
            Static.nl();
        }
        else if(line.equals("Serialization_type")){
            if(isCsv == 1){
                System.out.println("CSV");
            }
            else if(isCsv == 0){
                System.out.println("JSON");
            }
        }
        else if (line.equals("info")) {
            inCmd.info(mySet);
            Static.nl();
        }
        else if (line.equals("show")) {
            shInCmd.show(mySet);
            Static.nl();
        }
        else if (line.indexOf("add") != -1) {
            mySet.add(orAddCmd.add(line));
            Static.nl();
        }
        else if (line.contains("update")){
            upIdCmd.updateById(mySet, line);
            Static.nl();
        }
        else if (line.contains("remove_by_id")){
            rmIdCmd.remove_by_id(mySet, line);
            Static.nl();
        }
        else if (line.equals("clear")) {
            mySet.clear();
            System.out.println("Коллекция успешно удалено!");
            Static.nl();
        }
        else if(line.equals("sum_of_annual_turnover")){
            Static.txt("sum of annual turnover: " + smCmd.sum_of_annual_turnover(mySet));
            Static.nl();
        }
        else if(line.equals("average_of_annual_turnover")){
            Static.txt("average of annual turnover: " + smCmd.average_of_annual_turnover(mySet));
            Static.nl();
        }
        else if(line.equals("print_descending")){
            prDesCmd.print_descending(mySet);
        }
        return true;
    }
    */
}
