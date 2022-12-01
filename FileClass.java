package paintry;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FileClass {
    private File selectedFile;
    private File savFile;
    private Image image;
    private CanvasBuild cBuild;
    private Stage mainStage;
    private final int CLOSING_WINDOW_WIDTH = 350;
    private final int CLOSING_WINDOW_HEIGHT = 60;
    private final int CANVAS_WIDTH = 1750;
    private final int CANVAS_HEIGHT = 950;

    private double TIMER_WINDOW_HEIGHT = 150;
    private double TIMER_WINDOW_WIDTH = 450;

    /**
     * This is the constructor of the Filers class that instantiates a DCanvas
     * and Stage object in order to help perform file oriented tasks
     * @param canvas
     * @param stage
     */
    public FileClass(CanvasBuild canvas, Stage stage)
    {
        cBuild = canvas;

        mainStage = stage;
    }

    /**
     * Brings up a window that asks the user if they wish to see the autosave
     * timer or not see the timer
     */
    public void timer()
    {
        Button yes = new Button("Yes");
        Button no = new Button("No");

        //creates Label to ask user question on timer
        Label question = new Label("Would you like an automated save timer?");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(question, 1, 0);  //adds label to grid
        grid.add(yes, 0, 1);
        grid.add(no, 2, 1);

        Scene timerScene = new Scene(grid, TIMER_WINDOW_WIDTH,
                TIMER_WINDOW_HEIGHT);  //adds the grid to the window

        Stage timerWindow = new Stage();  //creates the new window (stage)

        timerWindow.setTitle("Timer");
        timerWindow.setScene(timerScene);
        timerWindow.show();

        yes.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {

                timerWindow.close();
            }
        });

        no.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {

                timerWindow.close();
            }
        });
    }

    /**
     * Method saves the canvasBuild to the original file location the user originally saved the
     * canvas when they "Saved As"
     * If the save file is null then it won't save
     * if save file is not null then the method makes a writable image and
     * makes it a snapshot of the canvas and saves that over the former
     * destination where the user originally saved canvas first
     */
    public void save() {
        if(savFile != null)
        {
            try /*no file chooser, therefore it uses the address of where they
                first saved their canvas and just reuses that address here
                without asking the user if they want to make a new address*/
            {
                WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT); //Constructs an empty image the size of the canvas
                cBuild.canvas.snapshot(null, writableImage); //Takes a snapshot of the canvas and everything on it, and applies it to the writable image
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null); //Renders the writable image
                ImageIO.write(renderedImage, "png", savFile); //Saves the render
            }
            catch (IOException ex)
            {
                Logger.getLogger(FirstTry.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

        }
    }

    /**
     * Brings up a window to ask user if they prefer to continue and exit
     * or save once again in the same spot they originally saved and then
     * close the program
     */
    public void exit(){
        Button continueB = new Button("Yes, continue");
        Button secondSave = new Button("save");

        VBox thirdLayout = new VBox();
        thirdLayout.getChildren().add(continueB);
        thirdLayout.getChildren().add(secondSave);

        Scene thirdScene = new Scene(thirdLayout, CLOSING_WINDOW_WIDTH,
                CLOSING_WINDOW_HEIGHT);

        Stage closingWindow = new Stage();  //creates the new window (stage)
        closingWindow.setTitle("Are you sure?");
        closingWindow.setScene(thirdScene);

        closingWindow.show();

        secondSave.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                if(savFile != null)
                {
                    try /*no file chooser, therefore it uses the address of where they
                first saved their canvas and just reuses that address here
                without asking the user if they want to make a new address*/
                    {
                        WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT); //Constructs an empty image the size of the canvas
                        cBuild.canvas.snapshot(null, writableImage); //Takes a snapshot of the canvas and everything on it, and applies it to the writable image
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null); //Renders the writable image
                        ImageIO.write(renderedImage, "png", savFile); //Saves the render
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(FirstTry.class.getName()).log(Level.SEVERE,
                                null, ex);
                    }

                }
            }
        });


        continueB.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                closingWindow.close();  //closes the closing window
                mainStage.close();  //closes the program
            }
        });
    }

    public void ChangeCanvas(double width , double height)
    {
        cBuild.canvas.setHeight(height);
        cBuild.canvas.setWidth(width);
    }

    public void saveAs(){
        FileChooser fileChooser2 = new FileChooser();
        //Allows user to filter between png's and jpeg's
        fileChooser2.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TIF File", "*.tif"),
                new FileChooser.ExtensionFilter("JPG File", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG File", "*png"));

        savFile = fileChooser2.showSaveDialog(mainStage);

        if(savFile != null)
        {
            try
            {
                WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT); //Constructs an empty image the size of the canvas
                cBuild.canvas.snapshot(null, writableImage); //Takes a snapshot of the canvas and everything on it, and applies it to the writable image
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null); //Renders the writable image
                ImageIO.write(renderedImage, "png", savFile); //Saves the render
            }
            catch (IOException ex)
            {
                Logger.getLogger(FirstTry.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }

    public void open(){
        FileChooser fileChooser = new FileChooser();//allows user to choose file

        fileChooser.getExtensionFilters().addAll( //Allows user to filter between png's, jpeg's, and .tif's
                new FileChooser.ExtensionFilter("PNG File", "*.png"),
                new FileChooser.ExtensionFilter("JPG File", "*.jpg"),
                new FileChooser.ExtensionFilter("TIF File", "*tif")
                );

        selectedFile = fileChooser.showOpenDialog(mainStage);
        try
        {
            FileInputStream input = new FileInputStream(selectedFile); //takes the address of file and assigns it to the "input" variable
            image = new Image(input); //grab the image from the "input" address and then allows for the ability to shows it with "imageView"

            double x = image.getWidth();
            double y = image.getHeight();
            /*Sends the dimensions of the photo to a method that will change the
            canvas dimensions to the dimensions of the picture*/
            ChangeCanvas(x , y);
            //This makes it so that the picture is as big as the variables defined above
            cBuild.gc.drawImage(image, 0, 0, x, y);
        }

        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FirstTry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pushes an image onto a stack
     * @param i
     */
    public void addToMainStack(Image i)
    {
        cBuild.mainPicture.push(i);
    }

}
