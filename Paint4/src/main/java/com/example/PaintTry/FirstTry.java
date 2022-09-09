package com.example.PaintTry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class FirstTry extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageView imageView = new ImageView();
        primaryStage.setTitle("Pain(t)");

        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar, imageView); //creates basic box for the scene

        Menu file = new Menu("File"); //declares main menu buttons
        Menu help = new Menu("Help");

        MenuItem open = new MenuItem("Open"); //declares sub menu buttons
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As...");
        MenuItem no = new MenuItem("no");

        file.getItems().add(open);
        file.getItems().add(save);
        file.getItems().add(saveAs);
        help.getItems().add(no);

        menuBar.getMenus().add(file);
        menuBar.getMenus().add(help);

        Scene main = new Scene(vBox, 900,600); //creates scene

        primaryStage.setScene(main); //adds scene to stage
        primaryStage.show();

        open.setOnAction(e ->{ //action listener for "open" button
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Select an Image", "*jpg", "*.png")); //sets what file types can be chosen
            File image = fileChooser.showOpenDialog(primaryStage); //opens a file chooser window for user
            if(image != null){
                Image image1 = new Image(image.toURI().toString()); //gets location of image and makes image object
                primaryStage.setHeight(image1.getHeight()); //changes window size to fit image opened
                primaryStage.setWidth(image1.getWidth());
                imageView.setImage(image1); //displays image
            }
        });

        save.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files","*.jpg"));
            fc.setTitle("Save your work");
            fc.showSaveDialog(primaryStage); //shows a save dialogue for user
            if (file != null) {
                try {
                    ImageIO.write(fromFXImage(imageView.getImage(), //writes image selection to files
                            null), "jpg", file);
                } catch (IOException ex) { //catch for any errors (wrong file type, etc.)
                    System.out.println("Could not save image");
                }
            }
        });

        saveAs.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files","*.jpg","*.png"));
            fc.setTitle("Save your work");
            fc.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    ImageIO.write(fromFXImage(imageView.getImage(),
                            null), "png", file);
                } catch (IOException ex) {
                    System.out.println("Could not save image");
                }
            }
        });
    }
    public static void main(String[] args) {
        Application.launch(args);
    }

}
