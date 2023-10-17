package settingsUI;

import clientMng.ClientMngUI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LongStringConverter;
import objectResAns.CsvReader;
import objectResAns.ObjectResAns;
import ui.clientui.MainUI;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Table extends Tab {

    public TableView<Organization> mainMenu(ResourceBundle bundle) {
        TableView<Organization> table = new TableView<>();

        TableColumn<Organization, Integer> id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Organization, String> name = new TableColumn(bundle.getString("Organization.name"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Organization, LocalDateTime> creationDate = new TableColumn(bundle.getString("Organization.creationDate"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        TableColumn<Organization, Coordinates> cor = new TableColumn(bundle.getString("Organization.cordinates"));

        TableColumn<Organization, Long> corX = new TableColumn(bundle.getString("Organization.x"));
        corX.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getX()));

        TableColumn<Organization, Float> corY = new TableColumn(bundle.getString("Organization.y"));
        corY.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getY()));

        TableColumn<Organization, Float> annual = new TableColumn(bundle.getString("Organization.annualTurnover"));
        annual.setCellValueFactory(new PropertyValueFactory<>("annualTurnover"));

        TableColumn<Organization, OrganizationType> type = new TableColumn(bundle.getString("Organization.type"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Organization, Address> address = new TableColumn(bundle.getString("Organization.addres"));

        TableColumn<Organization, String> street = new TableColumn(bundle.getString("Organization.street"));
        street.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPostalAddress().getStreet()));

        TableColumn<Organization, String> zpCode = new TableColumn(bundle.getString("Organization.zipCode"));
        zpCode.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPostalAddress().getZipCode()));

        TableColumn<Organization, Integer> usrId = new TableColumn(bundle.getString("Organization.userId"));
        usrId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            Organization o = event.getRowValue();
            o.setName(event.getNewValue());
            requestChange(o);
        });

        corX.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        corX.setOnEditCommit(event -> {
            Organization o = event.getRowValue();
            o.getCoordinates().setX(event.getNewValue());
            requestChange(o);
        });

        corY.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        corY.setOnEditCommit(event -> {
            Organization o = event.getRowValue();
            o.getCoordinates().setY(event.getNewValue());
            requestChange(o);
        });

        annual.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        annual.setOnEditCommit(event -> {
            Organization o = event.getRowValue();
            o.setAnnualTurnover(event.getNewValue());
            requestChange(o);
        });

        type.setCellFactory(ComboBoxTableCell.forTableColumn(OrganizationType.values()));
        type.setOnEditCommit(event -> {
            Organization o = event.getRowValue();
            o.setType(event.getNewValue());
            requestChange(o);
        });
        //annual.setOnEditCommit(event -> requestChange(event.getRowValue()));

        street.setCellFactory(TextFieldTableCell.forTableColumn());
        street.setOnEditCommit(event -> {
            Organization o = event.getRowValue();
            o.getPostalAddress().setStreet(event.getNewValue());
            requestChange(o);
        });

        zpCode.setCellFactory(TextFieldTableCell.forTableColumn());
        zpCode.setOnEditCommit(event -> {
            Organization o = event.getRowValue();
            o.getPostalAddress().setZipCode(event.getNewValue());
            requestChange(o);
        });

        table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                Organization selectedItem = table.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    reqToRemove(selectedItem);
                }
            }
        });

        table.setEditable(true);

        table.getColumns().addAll(id, name, creationDate, cor, annual, type, address, usrId);
        cor.getColumns().addAll(corX, corY);
        address.getColumns().addAll(street, zpCode);
        return table;
    }

    public void reqToRemove(Organization o){
        ClientMngUI ser = new ClientMngUI();
        String line = "remove_by_id " + o.getId();
        ObjectResAns res = null;
        MainUI mn = new MainUI();

        try {
            res = ser.sendToServer(line);
            if(res.isResAns()) {
                mn.msgBox(MainUI.bundle.getString("OrganizationTitle") + " " + o.getName() + " " + MainUI.bundle.getString("deleted") + "!");
                MainUI.tbView.update(MainUI.mainTable);
            }else{
                mn.msgErorBox(MainUI.bundle.getString("accesUser"));
                MainUI.tbView.update(MainUI.mainTable);
            }
        }catch (Exception exq){
            mn.msgErorBox(MainUI.bundle.getString("serverError"));
            MainUI.tbView.update(MainUI.mainTable);
        }
    }
    public void requestChange(Organization o){
        ClientMngUI ser = new ClientMngUI();
        String line = "update \"" + o.getId() + "\",\"" + o.getName() + "\",\"" + o.getCoordinates().getX() + "\",\"" + o.getCoordinates().getY() + "\",\"" + o.getAnnualTurnover() + "\",\"" + o.getType().name() + "\",\"" + o.getPostalAddress().getStreet() + "\",\"" + o.getPostalAddress().getZipCode() + "\"";
        ObjectResAns res = null;
        MainUI mn = new MainUI();

        try {
            res = ser.sendToServer(line);
            if(res.isResAns()) {
                mn.msgBox(MainUI.bundle.getString("OrganizationTitle") + " " + o.getName() + " " + MainUI.bundle.getString("edited") + "!");
                MainUI.tbView.update(MainUI.mainTable);
            }else{
                mn.msgErorBox(MainUI.bundle.getString("accesUser"));
                MainUI.tbView.update(MainUI.mainTable);
            }
        }catch (Exception exq){
            mn.msgErorBox(MainUI.bundle.getString("serverError"));
            MainUI.tbView.update(MainUI.mainTable);
        }
    }

    public void updateLan(TableView<Organization> table){
        table.getColumns().get(1).setText(MainUI.bundle.getString("Organization.name"));
        table.getColumns().get(2).setText(MainUI.bundle.getString("Organization.creationDate"));
        table.getColumns().get(3).setText(MainUI.bundle.getString("Organization.cordinates"));
        table.getColumns().get(3).getColumns().get(0).setText(MainUI.bundle.getString("Organization.x"));
        table.getColumns().get(3).getColumns().get(1).setText(MainUI.bundle.getString("Organization.y"));
        table.getColumns().get(4).setText(MainUI.bundle.getString("Organization.annualTurnover"));
        table.getColumns().get(5).setText(MainUI.bundle.getString("Organization.type"));
        table.getColumns().get(6).setText(MainUI.bundle.getString("Organization.addres"));
        table.getColumns().get(6).getColumns().get(0).setText(MainUI.bundle.getString("Organization.street"));
        table.getColumns().get(6).getColumns().get(1).setText(MainUI.bundle.getString("Organization.zipCode"));
        table.getColumns().get(7).setText(MainUI.bundle.getString("Organization.userId"));
    }
    public void update(TableView<Organization> table) {
        ObservableList<Organization> organizationList = FXCollections.observableArrayList();
        CsvReader alDa = new CsvReader();
        ClientMngUI ser = new ClientMngUI();

        try {
            ObjectResAns r = ser.sendToServer("show");
            organizationList = alDa.getOrganizationFromCsv(r.getResTesxt());
        } catch (IOException e) {
            System.out.println("Error show");
        }
        table.setItems(organizationList);
        MainUI.updateOnlyInfo();
    }
}