package paintry;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene.*;
import javafx.scene.shape.Line;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;


public class FirstTry extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Slider slide = new Slider(0,10,1); //initiates the slider for line width
        final double[] lineW = {0}; //creates variable for line width

        ColorPicker cPicker = new ColorPicker(); //creates color picker

        ImageView imageView = new ImageView();

        primaryStage.setTitle("Pain(t)");

        ScrollBar sc = new ScrollBar(); //creates and orients the scroll bar
        sc.setOrientation(Orientation.VERTICAL);
        sc.setMin(0);
        sc.setMax(1000);
        sc.setValue(0);
        sc.setVisibleAmount(100);

        Canvas canvas = new Canvas(); //creates drawable canvas
        canvas.setHeight(600);
        canvas.setWidth(900);
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

        Pane pane  = new Pane(imageView, canvas); //organizes elements
        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar, cPicker, slide, pane);//creates basic box for the scene
        HBox hBox = new HBox(vBox, sc);

        slide.setManaged(false);
        slide.setVisible(false);

        Menu fileBtn = new Menu("File"); //declares main menu buttons
        Menu help = new Menu("Help");
        Menu tools = new Menu("Tools");


        MenuItem open = new MenuItem("Open"); //declares sub menu buttons
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As...");
        MenuItem no = new MenuItem("no");
        MenuItem close = new MenuItem("Close Program");
        MenuItem lineWidth = new MenuItem("Line Width");
        MenuItem about = new MenuItem("About");

        fileBtn.getItems().add(open);
        fileBtn.getItems().add(save);
        fileBtn.getItems().add(saveAs);
        help.getItems().add(no);
        fileBtn.getItems().add(close);
        tools.getItems().add(lineWidth);
        help.getItems().add(about);


        menuBar.getMenus().add(fileBtn);
        menuBar.getMenus().add(tools);
        menuBar.getMenus().add(help);

        Scene main = new Scene(hBox, 900, 600); //creates scene

        primaryStage.setScene(main); //adds scene to stage
        primaryStage.show();

        close.setOnAction(e ->{
            System.exit(0);
        });

        open.setOnAction(e ->{ //action listener for "open" button
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Select an Image", "*.jpg", "*.png")); //sets what file types can be chosen
            File image = fileChooser.showOpenDialog(primaryStage); //opens a file chooser window for user
            if(image != null){
                Image image1 = new Image(image.toURI().toString()); //gets location of image and makes image object
                primaryStage.setHeight(image1.getHeight() + menuBar.getHeight() + cPicker.getHeight()); //changes window size to fit image opened
                primaryStage.setWidth(image1.getWidth());
                canvas.setHeight(image1.getHeight());
                canvas.setWidth(image1.getWidth());
                imageView.setImage(image1); //displays image
            }
        });

        sc.valueProperty().addListener(new ChangeListener<Number>() { //allows the scrollbar to changes canvas position
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                canvas.setLayoutY(-new_val.doubleValue());
            }
        });

        about.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){

                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                textArea.setText("This program currently allows you to open an image through File -> Open, Save a file through File -> Save As, and change the line width through Tools -> Line Width. You can draw a line on the screen by clicking and dragging through the workspace.");
                Scene scene = new Scene(textArea ,600,300);
                Stage aboutPage = new Stage();
                aboutPage.setTitle("About");
                aboutPage.setScene(scene);
                aboutPage.setX(primaryStage.getX() + 200);
                aboutPage.setX(primaryStage.getX() + 200);
                aboutPage.show();
            }
        });

        save.setOnAction(e -> {
            Image image1 = imageView.getImage();
            File file = new File(image1.getUrl());
            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
            System.out.println(fileExtension);
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), //writes image selection to files
                            null), fileExtension, file);
                } catch (IOException ex) { //catch for any errors (wrong file type/path, etc.)
                    System.out.println("Could not save image");
                }
            }
        });

        saveAs.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files","*.jpg","*.png"));
            fc.setTitle("Save your work");
            WritableImage iamge = pane.snapshot(new SnapshotParameters(),null); //takes snapshot of the canvas for saving changes
            File file = fc.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(iamge,
                            null), "png", file);
                } catch (IOException ex) {
                    System.out.println("Could not save image");
                }
            }
        });

        canvas.setOnMouseDragged((event) -> {
            lineW[0] =slide.getValue(); //sets line width
            graphicsContext2D.setFill(Color.BLACK); //sets line color
            graphicsContext2D.strokeRect(event.getX(), event.getY(), lineW[0], lineW[0]); //draws line with mouse drag
        });

        lineWidth.setOnAction(e-> { //action listener so slider can determine line width
            if(slide.isManaged()){
                slide.setManaged(false); //if slider is active then gets rid of slider
                slide.setVisible(false);
            } else {
                slide.setMajorTickUnit(1);
                slide.setMinorTickCount(2);
                slide.setSnapToTicks(true);
                slide.setShowTickLabels(true);
                slide.setManaged(true);
                slide.setVisible(true);
            }
            lineW[0] = slide.getValue();
        });

    }

}
