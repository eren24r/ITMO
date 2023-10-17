package ui.clientui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import сlasses.Organization;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;

public class Cnvs {
    private Canvas canvas;
    private HashSet<Organization> objects;
    private HashMap<Integer, Color> userColorMap = new HashMap<>();
    private Random rand = new Random();
    private Image houseImage;
    private TableView<Organization> table;

    public Cnvs(HashSet<Organization> objects, TableView<Organization> table, int width, int height) {
        this.canvas = new Canvas(width, height);
        this.table = table;
        this.objects = objects;
        setupObjectListeners();
        loadHouseImage();
        drawObjects();
    }

    private void loadHouseImage() {
        try {
            File imageFile = new File("pict/House.png");
            String absolutePath = imageFile.getAbsolutePath();
            houseImage = new Image(new FileInputStream(absolutePath));
            //houseImage = new Image(houseImage.getUrl(), 1, 1, true, true);
        } catch (Exception e) {
            System.out.println("Failed to load house image: " + e.getMessage());
        }
    }

    private void drawObjects() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw grid lines
        int gridSize = 40;
        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x < canvas.getWidth(); x += gridSize) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }
        for (int y = 0; y < canvas.getHeight(); y += gridSize) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }

        // Draw X and Y axis lines
        gc.setStroke(Color.GRAY);
        gc.strokeLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2); // X axis
        gc.strokeLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight()); // Y axis

        // Draw objects
        for (Organization object : objects) {
            double x = canvas.getWidth() / 2 + object.getCoordinates().getX();
            double y = canvas.getHeight() / 2 - object.getCoordinates().getY();
            Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
            gc.save(); // Save the current state of the graphics context
            gc.setGlobalAlpha(0.5); // Set the opacity to 0.5
            gc.setFill(color);
            gc.drawImage(houseImage, x, y, 30,30);
            gc.restore(); // Restore the previous state of the graphics context
        }
    }

    private void setupObjectListeners() {
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            double x = event.getX();
            double y = event.getY();
            for (Organization object : objects) {
                double objX = canvas.getWidth() / 2 + object.getCoordinates().getX();
                double objY = canvas.getHeight() / 2 - object.getCoordinates().getY();
                double houseWidth = 30;
                double houseHeight = 30;
                if (x >= objX - (houseWidth / 2) && x <= objX + (houseWidth / 2)
                        && y >= objY - houseHeight && y <= objY) {
                    showObjectInfo(object, table);
                    break;
                }
            }
        });
    }

    private void showObjectInfo(Organization object, TableView<Organization> table) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(MainUI.bundle.getString("Object_Information"));
        dialog.setHeaderText(object.getName());

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        StringBuilder content = new StringBuilder();
        content.append("ID: ").append(object.getId()).append("\n")
                .append(MainUI.bundle.getString("Organization.userId") + ": ").append(object.getUserId()).append("\n")
                .append(MainUI.bundle.getString("Organization.cordinates") + ": (").append(object.getCoordinates().getX()).append(", ")
                .append(object.getCoordinates().getY()).append(")\n")
                .append(MainUI.bundle.getString("Organization.name") + ": ").append(object.getName()).append("\n")
                .append(MainUI.bundle.getString("Organization.annualTurnover") + ": ").append(object.getAnnualTurnover()).append("\n")
                .append(MainUI.bundle.getString("Organization.creationDate") + ": ").append(object.getCreationDate()).append("\n")
                .append(MainUI.bundle.getString("Organization.type") + ": ").append(object.getType()).append("\n")
                .append(MainUI.bundle.getString("Organization.street") + ": ").append(object.getPostalAddress().getStreet()).append("\n")
                .append(MainUI.bundle.getString("Organization.zipCode") + ": ").append(object.getPostalAddress().getZipCode()).append("\n");

        Label contentLabel = new Label(content.toString());

        Button selectButton = new Button("Edit");
        selectButton.setOnAction(event -> {
            table.getSelectionModel().select(object);
            table.scrollTo(object);
            dialog.close();
        });

        dialogPane.setContent(new VBox(contentLabel, selectButton));

        dialogPane.getScene().getWindow().setOnCloseRequest(e -> {
            table.getSelectionModel().clearSelection();
        });

        dialog.showAndWait();
    }
    public Canvas getCanvas() {
        return canvas;
    }
}