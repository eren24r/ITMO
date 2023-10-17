package mainProgram;

import commandsEditor.CommandNew;
import startMain.Stating;

import java.io.*;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Программа
 */
public class Main {
    public void allCmd() throws IOException {

        CommandNew cmdEd = new CommandNew();
        cmdEd.newCommadsReader();

        Stating st = new Stating();
        st.saveDate();

    }
}