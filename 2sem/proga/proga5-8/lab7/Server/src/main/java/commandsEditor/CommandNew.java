package commandsEditor;

import commands.NewCommand;
import statics.Static;

import java.io.BufferedReader;
import java.io.FileReader;

public class CommandNew {
    public boolean newCommadsReader() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Static.cmdFileName));
            String line;
            int sz = 0;
            while (((line = reader.readLine()) != null)) {
                Static.listOfCommand.put(new NewCommand(line).getName(), new NewCommand(line));
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
