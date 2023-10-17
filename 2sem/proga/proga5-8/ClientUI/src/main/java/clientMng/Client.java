package clientMng;

import commands.ExecuteScriptCommand;
import commands.OrganizationAddCommand;
import commands.UpdateByIdCommand;
import objectResAns.ObjectResAns;
import statics.Static;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class Client {
    private static int port = 3909;
    public static void main(String[] args) throws Exception {
        boolean b = false;
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Scanner snr = null;
        int tmp = 0;
        System.out.println("Подключение к серверу...");

        while (true) {
            if(!b) {
                try {
                    // Устанавливаем соединение с сервером
                    socket = new Socket("localhost", port);
                    System.out.println("Я подключилься к серверу " + socket.getInetAddress());

                    // Получаем потоки ввода-вывода для обмена данными с сервером
                    out = new ObjectOutputStream(socket.getOutputStream());
                    in = new ObjectInputStream(socket.getInputStream());

                    ObjectResAns serverResponse = (ObjectResAns) in.readObject();
                    System.out.println(serverResponse.getResTesxt());

                    snr = new Scanner(System.in);
                    b = true;
                }catch (Exception e){
                    tmp = tmp + 1;
                    if(tmp >= 99999){
                        System.err.println("Ошибка Сервера! Повторное подключение...");
                        tmp = 0;
                    }
                }
            }

            while (b) {
                try {
                    System.out.print(">>> ");
                    String line = snr.nextLine();
                    ObjectResAns response = null;

                    if(line.split(" ")[0].equals("add")){
                        if(Static.user != null) {
                            OrganizationAddCommand exx = new OrganizationAddCommand();
                            response = exx.doo(null, line, Static.user);
                            System.out.println(response.getResTesxt());
                        }else{
                            response = new ObjectResAns(line, false, Static.user);
                            System.out.println("У вас нет доступа!");
                        }
                    }else if(line.split(" ")[0].equals("execute_script")){
                        Static.execute = new HashSet<>();
                        Static.execute.add(line.split(" ")[1]);
                        ExecuteScriptCommand ex = new ExecuteScriptCommand();
                        response = ex.doo(null, line, Static.user);
                        System.out.println(response.getResTesxt());
                    }else if(line.split(" ")[0].equals("update")){
                        if(Static.user != null) {
                            UpdateByIdCommand ex2 = new UpdateByIdCommand();
                            response = ex2.doo(null, line);
                        }else{
                            response = new ObjectResAns(line, false, Static.user);
                            System.out.println("У вас нет доступа!");
                        }
                    }else {
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

                    if(line.equals("exit")){
                        System.exit(0);
                    }
                }catch (Exception e){
                    System.err.println("Ошибка Сервера! Повторное подключение...");
                    socket.close();
                    b = false;
                }
            }
        }
    }
}