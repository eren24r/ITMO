package clientMng;

import commands.ExecuteScriptCommand;
import commands.OrganizationAddCommand;
import commands.UpdateByIdCommand;
import objectResAns.ObjectResAns;
import statics.Static;
import ui.clientui.MainUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class ClientMngUI {

    boolean b = false;
    Socket socket = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    Scanner snr = null;
    int tmp = 0;
    int port = 3909;

    public void giveAnsOfEdit() throws IOException{
        new Thread(() -> {
            boolean bb = false;
            int port = 3909;
            while (!bb) {
                try {
                    // Устанавливаем соединение с сервером
                    socket = new Socket("localhost", port);
                    //System.out.println("Я подключилься к серверу " + socket.getInetAddress());

                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    ObjectResAns serverResponse = (ObjectResAns) in.readObject();
                    System.out.println(serverResponse.getResTesxt());

                    bb = true;
                } catch (Exception e) {
                    System.err.println("Ошибка Сервера! Повторное подключение...");
                }
            }
            while (bb) {
                try {
                ObjectResAns serverResponse = null;
                // Читаем ответ от сервера
                serverResponse = (ObjectResAns) in.readObject();
                //System.out.println("Ответ от сервера: " + serverResponse.getResTesxt());
                System.out.println(serverResponse.getResTesxt());
                if(serverResponse.isResAns()){
                    MainUI.tbView.update(MainUI.mainTable);
                }
            }catch(Exception e){
                bb = false;
            }
        }
        }).start();
    }
    public ObjectResAns sendToServer(String line) throws IOException {

        try {
            // Устанавливаем соединение с сервером
            socket = new Socket("localhost", port);
            System.out.println("Я подключилься к серверу " + socket.getInetAddress());

            // Получаем потоки ввода-вывода для обмена данными с сервером
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            ObjectResAns serverResponse = (ObjectResAns) in.readObject();
            System.out.println(serverResponse.getResTesxt());

            b = true;
        }catch (Exception e){
            System.err.println("Ошибка Сервера! Повторное подключение...");
            socket.close();
            return new ObjectResAns("ServerError", false, Static.user);
        }

        try {
            ObjectResAns response = null;
            if(line.split(" ")[0].equals("execute_script")){
                Static.execute = new HashSet<>();
                Static.execute.add(line.split(" ")[1]);
                ExecuteScriptCommand ex = new ExecuteScriptCommand();
                response = ex.doo(null, line, Static.user);
                System.out.println(response.getResTesxt());
            }else{
                response = new ObjectResAns(line, true, Static.user);
            }
            // Создаем и отправляем объект Res на сервер
            out.writeObject(response);

            ObjectResAns serverResponse = null;
            // Читаем ответ от сервера
            serverResponse = (ObjectResAns) in.readObject();
            //System.out.println("Ответ от сервера: " + serverResponse.getResTesxt());
            Static.user = serverResponse.getUser();
            System.out.println(serverResponse.getResTesxt());
            socket.close();
            return serverResponse;
        }catch (Exception e){
            socket.close();
            return new ObjectResAns("ServerError", false, Static.user);
        }
    }
}
