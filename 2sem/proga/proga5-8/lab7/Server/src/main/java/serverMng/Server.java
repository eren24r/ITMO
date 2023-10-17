package serverMng;

import commands.*;
import dataParseIng.BDReader;
import mainProgram.Main;
import objectResAns.ObjectResAns;
import org.apache.groovy.json.internal.IO;
import statics.Static;
import сlasses.Organization;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.*;

public class Server {
    private static int port = 3909;
    public static void main(String[] args) throws IOException, SQLException {
        Map<String, Command> listCommand = new LinkedHashMap<String, Command>();
        listCommand.put(new InfoCommands().getName(), new InfoCommands());
        Main ker = new Main();

        boolean b = false;
        Socket socket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        ServerSocket serverSocket = null;
        HashSet<Organization> mySet = null;
        String allRes = null;
        ker.allCmd();
        BDReader bdReader = new BDReader();
        mySet = bdReader.getAllData();
        System.out.println("Все данные загружены с базы!");
        allRes = "Все данные загружены с базы!\n";
        Commands cmd = new Commands();
        HashSet<Organization> finalMySet = mySet;
        ArrayList<Socket> listSc = new ArrayList<>();

        new Thread(() -> {
            Scanner s = new Scanner(System.in);
            if(s.nextLine().equals("exit")){
                System.out.println("Выход!...");
                System.exit(0);
            }
        }).start();

        try {
            // Создаем серверный сокет, прослушивающий порт
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен.");
            System.out.println("Ожидание подключения клиента...");

        } catch (IOException e) {
            System.err.println("Ошибка при работе сервера: " + e.getMessage());
        }

        while (true) {
            try {
                // Ожидаем подключение клиента
                if (!b) {
                    socket = serverSocket.accept();
                    listSc.add(socket);
                    System.out.println("Клиент " + socket.getInetAddress() + " подключился.");
                    // Получаем потоки ввода-вывода для обмена данными с клиентом
                    in = new ObjectInputStream(socket.getInputStream());
                    out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectResAns response = new ObjectResAns(Static.txt(allRes), true, null);
                    out.writeObject(response);
                    b = true;
                }
            }catch (IOException e) {
                continue;
            }
            try {
                while (b) {
                    ObjectResAns clientRequest = null;
                    // Читаем запрос от клиента
                    clientRequest = (ObjectResAns) in.readObject();
                    System.out.println("Запрос от клиента: " + clientRequest.getResTesxt());
                    ObjectResAns response = null;

                    // Создаем и отправляем объект Res в ответ клиенту
                    try {
                        response = cmd.commandsEditor(mySet, clientRequest.getResTesxt(), clientRequest.getUser());
                        mySet = bdReader.getAllData();
                    }catch (Exception e){
                        response = new ObjectResAns("Ошибка команды\n", false, clientRequest.getUser());
                    }
                    //ObjectResAns response = new ObjectResAns(clientRequest.getResTesxt(), true);
                    out.writeObject(response);
                }
            }catch (Exception e){
                System.err.println("Клиент: " + socket.getInetAddress() + " отключилься!");
                listSc.remove(socket);
                socket.close();
                b = false;
            }
        }
    }
}
