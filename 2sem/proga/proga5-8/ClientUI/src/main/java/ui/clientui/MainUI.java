package ui.clientui;

import clientMng.ClientMngUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objectResAns.CsvReader;
import objectResAns.ObjectResAns;
import settingsUI.*;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainUI extends Application {

    private static String username;
    private static String password;
    private Stage primaryStage;
    Stage newWindow = new Stage();
    public static ResourceBundle bundle = new Language().setLanguage("Russian"); // Set the default language;
    private Label messageLabel;
    private Label usernameLabel;
    private Label passwordLabel;
    private Button loginButton;
    private Button helpBtn;
    private Button sumAnnualBtn;
    private Button avgAnnualBtn;
    private Button excScrBtn;
    private Button addBtn;
    private Button logOut;
    private Button visualize;
    TextField orNameField = new TextField();
    private Stage addStage = new Stage();
    TitledPane btnCmd = new TitledPane();
    private Button addUserBtn;
    public static TitledPane infoTittled = new TitledPane();
    TitledPane titledPane = new TitledPane();
    Label orName = new Label();
    public ClientMngUI ser = new ClientMngUI();
    public static Table tbView = new Table();
    public static TableView<Organization> mainTable = new TableView<>();
    public CsvReader csv = new CsvReader();
    Label corX = new Label();
    Label corY = new Label();
    Label annualLabel = new Label();
    Label o_typeLabel = new Label();
    Label streetLabel = new Label();
    Label zipCodeLabel = new Label();
    Button addButton = new Button();


    @Override
    public void start(Stage stage) throws IOException {

        this.primaryStage = stage;

        primaryStage.setTitle(bundle.getString("authorizationTitle"));

        GridPane grid = createGridPane();
        addUIControls(grid);

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(80));
        grid.setVgap(40);
        grid.setHgap(40);
        grid.setAlignment(Pos.CENTER);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Устанавливает максимальные размеры GridPane
        return grid;
    }

    private void addUIControls(GridPane grid) {
        usernameLabel = new Label(bundle.getString("usernameLabel"));
        TextField usernameField = new TextField();
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        passwordLabel = new Label(bundle.getString("passwordLabel"));
        PasswordField passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        loginButton = new Button(bundle.getString("loginButton"));
        loginButton.setOnAction(e -> {
            username = usernameField.getText();
            password = passwordField.getText();
            sendAuthenticationRequest();
        });
        grid.add(loginButton, 1, 2);

        addUserBtn = new Button(bundle.getString("addUser"));
        addUserBtn.setOnAction(event -> {
            username = usernameField.getText();
            password = passwordField.getText();
            sendAddUserRe();
        });
        grid.add(addUserBtn, 2,2);

        messageLabel = new Label();
        grid.add(messageLabel, 0, 3, 2, 1);

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll(
                "Russian",
                "German",
                "Latvian",
                "Spanish"
        );
        languageComboBox.setPromptText("Language");
        languageComboBox.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage != null) {
                bundle = new Language().setLanguage(selectedLanguage);
                primaryStage.setTitle(bundle.getString("authorizationTitle"));
                updateUIControls();
            }
        });
        grid.add(languageComboBox, 0, 2);
    }

    private void sendAddUserRe(){
        ObjectResAns res = null;
        try {
            res = ser.sendToServer("addUser " + username + " " + password);
            boolean isAuthenticated = res.isResAns();

            if (isAuthenticated) {
                Static.user = username;
                messageLabel.setText(bundle.getString("user") + " " + username + " " + bundle.getString("added") + "!");
                System.out.println(bundle.getString("user") + " " + username + " " + bundle.getString("added") + "!");
            } else {
                System.out.println(bundle.getString("userAlreadyAdded"));
                messageLabel.setText(bundle.getString("userAlreadyAdded"));
            }
        } catch (Exception e) {
            msgErorBox(bundle.getString("serverError"));
        }
    }

    private void sendAuthenticationRequest() {
        //boolean isAuthenticated = serverCommunicator.executeCommand("authentication", new Argument("", serverCommunicator, username, password, primaryStage, bundle)).isSuccess();
        ObjectResAns res = null;
        try {
            res = ser.sendToServer("Login " + username + " " + password);
            boolean isAuthenticated = res.isResAns();

            if (isAuthenticated) {
                Static.user = res.user;
                messageLabel.setText(bundle.getString("loginSuccessful"));
                System.out.println(bundle.getString("loginSuccessful"));
                primaryStage.close();
                launchTabbedInterface();
            } else {
                if(!res.getResTesxt().equals("ServerError")) {
                    System.out.println(bundle.getString("incorrectUsernamePassword"));
                    messageLabel.setText(bundle.getString("incorrectUsernamePassword"));
                }else{
                    messageLabel.setText(bundle.getString("serverError"));
                }
            }
        } catch (Exception e) {
            msgErorBox(bundle.getString("serverError"));
        }
    }

    private void launchTabbedInterface() {
        primaryStage.setTitle(bundle.getString("OrganizationTitle"));

        //User
        titledPane.setText(bundle.getString("user"));

        Label content = new Label(Static.user);
        titledPane.setContent(content);
        titledPane.setCollapsible(false);

        //info
        infoTittled.setText(bundle.getString("infoOrganization"));
        updateInfo(infoTittled);

        //cmdButtons
        btnCmd.setText(bundle.getString("commands"));
        btnCmd(btnCmd);

        //box
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        mainTable = tbView.mainMenu(bundle);
        tbView.update(mainTable);

        titledPane.setMinWidth(300);
        titledPane.setMaxWidth(300);
        infoTittled.setMinWidth(300);
        infoTittled.setMaxWidth(300);
        btnCmd.setMinWidth(300);
        btnCmd.setMaxWidth(300);
        mainTable.setMinWidth(1400);
        mainTable.setMaxWidth(1400);

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll(
                "Russian",
                "German",
                "Latvian",
                "Spanish"
        );
        languageComboBox.setPromptText("Language");
        languageComboBox.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage != null) {
                bundle = new Language().setLanguage(selectedLanguage);
                primaryStage.setTitle(bundle.getString("OrganizationTitle"));
                updateUIControls();
            }
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().addAll(titledPane, infoTittled, btnCmd, languageComboBox);

        hbox.getChildren().addAll(vbox, mainTable);

        Scene scene = new Scene(hbox, 1400, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void btnCmd(TitledPane btnCmd){
        addBtn = new Button(bundle.getString("add_element_name"));
        addBtn.setOnAction(event -> {
            tbView.update(mainTable);
            updateInfo(infoTittled);
            addElemantCnt();
        });

        helpBtn = new Button(bundle.getString("help"));
        helpBtn.setOnAction(event -> {
            tbView.update(mainTable);
            updateInfo(infoTittled);

            // Создание всплывающего окна сообщения
            ObjectResAns res = null;
            try {
                res = ser.sendToServer("help");
                msgBox(res.getResTesxt());
            }catch (Exception e){
                msgErorBox(bundle.getString("serverError"));
            }
        });

        sumAnnualBtn = new Button(bundle.getString("sum_of_annual_turnover"));
        sumAnnualBtn.setOnAction(event -> {
            tbView.update(mainTable);
            updateInfo(infoTittled);
            // Создание всплывающего окна сообщения
            ObjectResAns res = null;
            try {
                res = ser.sendToServer("sum_of_annual_turnover");
                msgBox(bundle.getString("sum_of_annual_turnover") + " " + res.getResTesxt());
            }catch (Exception e){
                msgErorBox(bundle.getString("serverError"));
            }
        });

        avgAnnualBtn = new Button(bundle.getString("average_of_annual_turnover"));
        avgAnnualBtn.setOnAction(event -> {
            tbView.update(mainTable);
            updateInfo(infoTittled);
            // Создание всплывающего окна сообщения
            ObjectResAns res = null;
            try {
                res = ser.sendToServer("average_of_annual_turnover");
                msgBox(bundle.getString("average_of_annual_turnover") + " " + res.getResTesxt());
            }catch (Exception e){
                msgErorBox(bundle.getString("serverError"));
            }
        });

        excScrBtn = new Button(bundle.getString("execute_script_file_name"));

        visualize = new Button(bundle.getString("Visualize"));
        visualize.setOnAction(event -> {
            tbView.update(mainTable);
            updateInfo(infoTittled);

            HashSet<Organization> objects = null;
            try {
                objects = csv.getOrganizationFromCsvToOb(ser.sendToServer("show").getResTesxt());
            } catch (IOException e) {
                System.out.println("Error");
            }
            Cnvs canvasManager = new Cnvs(objects, mainTable, 1400, 800);
            VBox vbox = new VBox(canvasManager.getCanvas());
            newWindow = new Stage();
            newWindow.setTitle(bundle.getString("Visualize"));

            Scene secondScene = new Scene(vbox, 1400, 800);
            newWindow.setScene(secondScene);
            newWindow.initModality(Modality.NONE);
            //newWindow.setMaximized(true);
            newWindow.show();
        });

        logOut = new Button(bundle.getString("logout"));
        logOut.setOnAction(event -> {
            Static.user = null;

            try {
                addStage.close();
            }catch (Exception e){
                System.out.println("Close Error!");
            }
            try {
                start(primaryStage);
            } catch (IOException e) {
                System.out.println("Error Loading!");
            }
        });

        VBox vb = new VBox(addBtn, helpBtn, sumAnnualBtn, avgAnnualBtn, excScrBtn, visualize, logOut);
        vb.setSpacing(10);
        //vb.getChildren().addAll(addBtn, helpBtn, sumAnnualBtn, avgAnnualBtn, excScrBtn);
        btnCmd.setContent(vb);
    }

    private void updateUIControls() {
        usernameLabel.setText(bundle.getString("usernameLabel"));
        passwordLabel.setText(bundle.getString("passwordLabel"));
        loginButton.setText(bundle.getString("loginButton"));
        addUserBtn.setText(bundle.getString("addUser"));
        titledPane.setText(bundle.getString("user"));
        infoTittled.setText(bundle.getString("infoOrganization"));
        updateInfo(infoTittled);
        btnCmd.setText(bundle.getString("commands"));
        addBtn.setText(bundle.getString("add_element_name"));
        helpBtn.setText(bundle.getString("help"));
        sumAnnualBtn.setText(bundle.getString("sum_of_annual_turnover"));
        avgAnnualBtn.setText(bundle.getString("average_of_annual_turnover"));
        excScrBtn.setText(bundle.getString("execute_script_file_name"));
        visualize.setText(bundle.getString("Visualize"));
        logOut.setText(bundle.getString("logout"));
        messageLabel.setText("");
        tbView.updateLan(mainTable);
        orName.setText(bundle.getString("Organization.name"));
        corX.setText(bundle.getString("Organization.x"));
        corY.setText(bundle.getString("Organization.y"));
        annualLabel.setText(bundle.getString("Organization.annualTurnover"));
        o_typeLabel.setText(bundle.getString("Organization.type"));
        streetLabel.setText(bundle.getString("Organization.street"));
        zipCodeLabel.setText(bundle.getString("Organization.zipCode"));
        addButton.setText(bundle.getString("add"));
        newWindow.setTitle(bundle.getString("Visualize"));
    }

    public void updateInfo(TitledPane infoTittled){
        ObjectResAns res = null;
        try {
            res = ser.sendToServer("info");
        } catch (IOException e) {
            System.out.println("Server Error!");
        }

        Label content = new Label(bundle.getString("object") + ": " + "Organization\n" +
                "id: " + bundle.getString("id") + "\n" +
                "name: " + bundle.getString("name") + "\n" +
                "cordinates: " + bundle.getString("coordinates") + "\n" +
                "creationDate: " + bundle.getString("creationDate") + "\n" +
                "annualTurnover: " + bundle.getString("annualTurnover") + "\n" +
                "type: " + bundle.getString("type") + "\n" +
                "postalAddress: " + bundle.getString("postalAddress") + "\n\n" +
                bundle.getString("size") + ": " + res.getResTesxt());
        infoTittled.setContent(content);
    }

    public void msgBox(String txt){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("msg"));
        alert.setHeaderText(null);
        alert.setContentText(txt);
        // Отображение всплывающего окна
        alert.showAndWait();
    }

    public void msgErorBox(String txt){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(bundle.getString("msg"));
        alert.setHeaderText(null);
        alert.setContentText(txt);
        // Отображение всплывающего окна
        alert.showAndWait();
    }

    public void addElemantCnt(){
        addStage.setTitle(bundle.getString("add_element_name"));

        GridPane grid = createGridPane();
        addElementUIControls(grid);

        Scene scene = new Scene(grid, 800, 1000);
        addStage.setScene(scene);
        addStage.show();
    }

    private void addElementUIControls(GridPane grid) {
        orName = new Label(bundle.getString("Organization.name"));
        orNameField = new TextField();
        grid.add(orName, 0, 0);
        grid.add(orNameField, 1, 0);

        corX = new Label(bundle.getString("Organization.x"));
        TextField corXfiled = new TextField();
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if(change.getControlNewText().matches("\\d*")){
                return change;
            }
            return null;
        });
        corXfiled.setTextFormatter(textFormatter);
        grid.add(corX, 0, 1);
        grid.add(corXfiled, 1, 1);

        corY = new Label(bundle.getString("Organization.y"));
        TextField corYfiled = new TextField();
        TextFormatter<String> textFormatter1 = new TextFormatter<>(change -> {
            if(change.getControlNewText().matches("-?\\d*(\\.\\d*)?")){
                return change;
            }
            return null;
        });
        corYfiled.setTextFormatter(textFormatter1);
        grid.add(corY, 0, 2);
        grid.add(corYfiled, 1, 2);

        annualLabel = new Label(bundle.getString("Organization.annualTurnover"));
        TextField annual = new TextField();
        TextFormatter<String> textFormatter2 = new TextFormatter<>(change -> {
            if(change.getControlNewText().matches("-?\\d*(\\.\\d*)?")){
                return change;
            }
            return null;
        });
        annual.setTextFormatter(textFormatter2);
        grid.add(annualLabel, 0, 3);
        grid.add(annual, 1, 3);

        o_typeLabel = new Label(bundle.getString("Organization.type"));
        ComboBox<String> comboBoxType = new ComboBox<>();
        comboBoxType.getItems().addAll(
                "PUBLIC",
                "GOVERNMENT",
                "TRUST"
        );
        comboBoxType.setPromptText("Type");

        grid.add(o_typeLabel, 0, 4);
        grid.add(comboBoxType, 1, 4);

        streetLabel = new Label(bundle.getString("Organization.street"));
        TextField streetField = new TextField();
        grid.add(streetLabel, 0, 5);
        grid.add(streetField, 1, 5);

        zipCodeLabel = new Label(bundle.getString("Organization.zipCode"));
        TextField zipCodeField = new TextField();
        grid.add(zipCodeLabel, 0, 6);
        grid.add(zipCodeField, 1, 6);

        addButton = new Button(bundle.getString("add"));
        addButton.setOnAction(e -> {
            try {
                String nameNew = orNameField.getText();
                Coordinates cr = new Coordinates(Long.parseLong(corXfiled.getText()), Float.parseFloat(corYfiled.getText()));
                /*OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt.get(6)));*/
                Float anT = Float.parseFloat(annual.getText());
                OrganizationType ortp = OrganizationType.PUBLIC;
                OrganizationType o_type = ortp.getTypeByName(comboBoxType.getValue());
                Address ad = new Address(streetField.getText(), zipCodeField.getText());
                ObjectResAns res = null;
                String request = "add \"" + nameNew + "\",\"" + cr.getX() + "\",\"" + cr.getY() + "\",\"" + anT + "\",\"" + o_type.name() + "\",\"" + ad.getStreet() + "\",\"" + ad.getZipCode() + "\"";
                res = ser.sendToServer(request);
                if(res.isResAns()) {
                    msgBox(bundle.getString("OrganizationTitle") + " " + nameNew + " " + bundle.getString("added") + "!");
                    tbView.update(mainTable);
                    updateInfo(infoTittled);
                }else{
                    msgErorBox(bundle.getString("pleace") + " " + bundle.getString("edit") + " " + bundle.getString("all") + " " + bundle.getString("cols") + "!");
                }
            }catch (IOException exq){
                msgErorBox(bundle.getString("serverError"));
            } catch (Exception ex){
                msgErorBox(bundle.getString("pleace") + " " + bundle.getString("edit") + " " + bundle.getString("all") + " " + bundle.getString("cols") + "!");
            }
        });
        grid.add(addButton, 1, 7);

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll(
                "Russian",
                "German",
                "Latvian",
                "Spanish"
        );
        languageComboBox.setPromptText("Language");
        languageComboBox.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage != null) {
                bundle = new Language().setLanguage(selectedLanguage);
                updateUIControls();
            }
        });
        grid.add(languageComboBox, 0, 8);
    }

    public static void updateOnlyInfo(){
        ClientMngUI ser = new ClientMngUI();
        ObjectResAns res = null;
        try {
            res = ser.sendToServer("info");
        } catch (IOException e) {
            System.out.println("Server Error!");
        }

        Label content = new Label(bundle.getString("object") + ": " + "Organization\n" +
                "id: " + bundle.getString("id") + "\n" +
                "name: " + bundle.getString("name") + "\n" +
                "cordinates: " + bundle.getString("coordinates") + "\n" +
                "creationDate: " + bundle.getString("creationDate") + "\n" +
                "annualTurnover: " + bundle.getString("annualTurnover") + "\n" +
                "type: " + bundle.getString("type") + "\n" +
                "postalAddress: " + bundle.getString("postalAddress") + "\n\n" +
                bundle.getString("size") + ": " + res.getResTesxt());
        infoTittled.setContent(content);
    }


    public static void main(String[] args) {
        launch();
    }
}