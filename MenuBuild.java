package paintry;

import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuBuild {
    private final double NOTES_WINDOW_WIDTH = 1000;
    private final double NOTES_WINDOW_HEIGHT = 750;
    private final double TOOLS_WINDOW_WIDTH = 750;
    private final double TOOLS_WINDOW_HEIGHT = 550;
    private ToggleGroup group;
    private KeyCombination keyS;
    private KeyCombination keyO;
    private KeyCombination keySa;
    protected ColorPicker colorPicker = new ColorPicker(Color.ORANGE);
    protected MenuBar menuBar;
    protected Label timer;
    protected ToolBar toolBar;
    private CanvasBuild cBuild;
    protected TextArea textA;
    private final int NEW_WINDOW_WIDTH = 400;
    private final int NEW_WINDOW_HEIGHT = 150;
    private FileClass filers;
    private FirstTry fTry;
    protected TabPane tabPane;
    protected Tab tab;

    private final int POLY_WINDOW_WIDTH = 450;
    private final int POLY_WINDOW_HEIGHT = 150;
    private int polygonSides;

    private final int CLOSING_WINDOW_WIDTH = 200;
    private final int CLOSING_WINDOW_HEIGHT = 100;

    private Thread thread;
    private double TIMER_WINDOW_HEIGHT = 150;
    private double TIMER_WINDOW_WIDTH = 450;
    private ImageView imageView;


    public MenuBuild(CanvasBuild canvas, FileClass filer){
        fTry = new FirstTry();

        cBuild = canvas;

        filers = filer;

        timer = new Label();

        Label time = new Label();
        Timer timer = new Timer(filer, time);
        thread = new Thread(timer);
        time.textProperty().bind(timer.getTime());

        tabPane = new TabPane();
        tab = new Tab();
        tab.setContent(cBuild.canvasScroll);
        tab.setText("Pain(t)");
        tabPane.getTabs().add(tab);

        menuBar = new MenuBar(); //creates a menu bar

        //Menu is the button to click for drop down in menu bar
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");//These are all menu bar options for program
        Menu view = new Menu("View");
        Menu help = new Menu("Help");
        Menu clockLabel = new Menu("", time);

        menuBar.getMenus().addAll(file, edit, view, help, clockLabel); //adds Menus to the menu bar

        MenuItem about = new MenuItem("About");
        help.getItems().add(about);

        MenuItem cResize = new MenuItem("Resize Canvas");
        edit.getItems().add(cResize);

        //creates a menu item, which are the menu options under the menu "file"
        MenuItem newTab = new MenuItem("New");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As..");
        MenuItem open = new MenuItem("Open..");
        MenuItem exit = new MenuItem("Exit..");
        file.getItems().addAll(newTab, save,saveAs,open,exit);

        newTab.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN)); //creates keyboard shortcut for Open = Ctrl + O
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)); //creates keyboard shortcut for Save = Crtl + S
        saveAs.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN)); //creates keyboard shortcut for Save As = Ctrl + A
        exit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN)); //creates keyboard shortcut for Exit = Ctrl + E

        toolBar = new ToolBar(); //creates a toolbar

        MenuButton width = new MenuButton("Brush Width");
        width.setTooltip(new Tooltip("Change the Brush Width"));
        //creates the width options for under the BrushWidth button
        MenuItem onePx = new MenuItem("1 Px");
        MenuItem twoPx = new MenuItem("2 Px");
        MenuItem fourPx = new MenuItem("4 Px");
        MenuItem eightPx = new MenuItem("8 Px");
        MenuItem twelvePx = new MenuItem("12 Px");
        //Adds the width choices under the toolbar menu item
        width.getItems().addAll(onePx, twoPx, fourPx, eightPx, twelvePx);

        MenuItem zoomI = new MenuItem("Zoom In");
        MenuItem zoomO = new MenuItem("Zoom Out");
        view.getItems().addAll(zoomI,zoomO);

        MenuItem clear = new MenuItem("Clear Canvas");
        Menu rotateC = new Menu("Rotate Canvas");
        MenuItem r90 = new MenuItem("90 Degrees");
        MenuItem r180 = new MenuItem("180 Degrees");
        MenuItem r270 = new MenuItem("270 Degrees");
        edit.getItems().addAll(clear, rotateC);  //adds crop and stamp under edit
        rotateC.getItems().addAll(r90,r180,r270);

        Button undo = new Button("Undo", new ImageView("undoI.png"));  //makes undo button
        undo.setTooltip(new Tooltip("Undo your last few actions")); //makes tooltip for undo button
        Button redo = new Button("Redo", new ImageView("redoI.png"));  //makes the redo button
        redo.setTooltip(new Tooltip("Redo anything you undid"));

        //Makes the Pencil Tool Button
        ToggleButton pencilT = new ToggleButton("Pencil Tool");
        pencilT.setGraphic(new ImageView("pencil.png"));
        pencilT.setTooltip(new Tooltip("Free Draw"));

        //Creates dropper tool button
        ToggleButton dropper = new ToggleButton("Dropper", new ImageView("dropperi.png"));
        dropper.setTooltip(new Tooltip("Select any color on the canvas to copy it"));

        //Makes all the Shape toggle buttons
        ToggleButton rectangle = new ToggleButton("Rectangle", new ImageView("rectI.png"));
        rectangle.setTooltip(new Tooltip("Draw a Rectangle")); //sets tooltips for rectangle tool
        ToggleButton circle = new ToggleButton("Circle", new ImageView("circleI.png"));
        circle.setTooltip(new Tooltip("Draw a circle"));//sets tooltips for circle tool
        ToggleButton elipse = new ToggleButton("Ellipse", new ImageView("ovalI.png"));
        elipse.setTooltip(new Tooltip("Draw an Oval"));//sets tooltips for oval tool
        ToggleButton square = new ToggleButton("Square", new ImageView("squareI.png"));
        square.setTooltip(new Tooltip("Draw a square"));//sets tooltips for square tool
        ToggleButton polygon = new ToggleButton("Polygon");
        polygon.setTooltip(new Tooltip("Enter a number and draw any normal sided polygon"));
        ToggleButton triangle = new ToggleButton("Triangle", new ImageView("triangleI.png"));
        triangle.setTooltip(new Tooltip("Draw a triangle"));//sets tooltips for triabgle tool

        //Creates the eraser tool
        ToggleButton eraser = new ToggleButton("Eraser", new ImageView("eraserI.png"));
        eraser.setTooltip(new Tooltip("Erase your drawings"));

        //Makes the line tool button a toggle button
        ToggleButton lineT = new ToggleButton("Line Tool", new ImageView("lineI.png"));
        lineT.setTooltip(new Tooltip("Draw a straight line"));
        ToggleButton lineD = new ToggleButton("Dashed Line", new ImageView("DLineI.png"));
        lineD.setTooltip(new Tooltip("Draw a dashed line"));

        //creates the status bar Label
        Label statusBar = new Label("Tool Selected");

        textA = new TextArea();  //Makes Text Area
        textA.setWrapText(true);
        textA.setPrefHeight(50);  //Changes the width of textArea
        textA.setPrefWidth(105);  //Changes the height of textArea


        //adds all the items to the toolbar
        toolBar.getItems().addAll(colorPicker, new Separator(), undo, redo,
                new Separator(), dropper, new Separator(), eraser, pencilT,
                lineT, lineD, new Separator(),width, new Separator(), rectangle, square,
                circle, elipse, triangle, polygon, new Separator(), statusBar);

        group = new ToggleGroup();
        pencilT.setToggleGroup(group);  //Adds the pencil Tool to the group
        lineT.setToggleGroup(group);  //adds the Line tool to the group
        rectangle.setToggleGroup(group);  //adds rectangle to the group
        circle.setToggleGroup(group);  //adds rectangle to the group
        elipse.setToggleGroup(group);  //adds rectangle to the group
        dropper.setToggleGroup(group);  //adds dropper to the group
        eraser.setToggleGroup(group);  //adds eraser to the group
        lineD.setToggleGroup(group); //adds dashed line to the group
        polygon.setToggleGroup(group); //adds polygon button to the group
        triangle.setToggleGroup(group); //adds triangle button to the group

        onePx.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                cBuild.gc.setLineWidth(1);  //sets width of strokes to 1 px
            }
        });

        twoPx.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                cBuild.gc.setLineWidth(2);  //sets width of strokes to 2 px
            }
        });

        fourPx.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                cBuild.gc.setLineWidth(4);  //sets width of strokes to 4 px
            }
        });

        eightPx.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                cBuild.gc.setLineWidth(8);  //sets width of strokes to 8 px
            }
        });

        twelvePx.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                cBuild.gc.setLineWidth(12);  //sets width of strokes to 12 px
            }
        });

        //This allows the user to choose when they want to use the Pencil Tool
        pencilT.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 0;
                statusBar.setText("Pencil Tool");
            }
        });

        //This allows the user to choose when they want to use the Line Tool
        lineT.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 1;
                statusBar.setText("Line Tool");
            }
        });

        lineD.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                cBuild.mode = 7;
                statusBar.setText("Dashed Line");
            }
        });

        //This allows the user to choose when they want to use the Rectangle Tool
        rectangle.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 2;
                statusBar.setText("Rectangle Tool");
            }
        });

        circle.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 3;
                statusBar.setText("Circle Tool");
            }
        });

        elipse.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 4;
                statusBar.setText("Oval Tool");
            }
        });

        square.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                cBuild.mode = 8;
                statusBar.setText("Square Tool");
            }
        });

        polygon.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                polyWindow();
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 9;
                statusBar.setText("Polygon Tool");
            }
        });

        triangle.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                cBuild.mode = 14;
                statusBar.setText("Triangle Tool");
            }
        });

        dropper.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 5;
                statusBar.setText("Dropper Tool");
            }
        });

        eraser.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                /*when user chooses their tool it changes the int variable to
                this number*/
                cBuild.mode = 6;
                statusBar.setText("Eraser Tool");
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Button continueB = new Button("Yes, continue");
                Button secondSave = new Button("Cancel");

                VBox thirdLayout = new VBox();
                thirdLayout.setPadding(new Insets(10, 10, 10, 10));
                thirdLayout.setSpacing(15);
                thirdLayout.getChildren().add(continueB);
                thirdLayout.getChildren().add(secondSave);

                Scene thirdScene = new Scene(thirdLayout, CLOSING_WINDOW_WIDTH, CLOSING_WINDOW_HEIGHT);

                Stage closingWindow = new Stage();  //creates the new window (stage)
                closingWindow.setTitle("Are you sure?");
                closingWindow.setScene(thirdScene);

                closingWindow.show();

                continueB.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        cBuild.gc.setFill(Color.WHITE);
                        cBuild.gc.fillRect(0,0,cBuild.gc.getCanvas().getWidth(),cBuild.gc.getCanvas().getHeight());
                        cBuild.addToStack(cBuild.screenshot());
                        closingWindow.close();
                    }
                });

                secondSave.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        closingWindow.close();  //closes the closing window
                    }
                });
            }
        });

        about.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event){
                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                textArea.setText("This program currently allows you to open an image through File -> Open, Save a file through File -> Save As, and change the line width through Tools -> Line Width. You can draw a line on the screen by clicking and dragging through the workspace.");
                Scene scene = new Scene(textArea ,600,300);
                Stage aboutPage = new Stage();
                aboutPage.setTitle("About");
                aboutPage.setScene(scene);
                aboutPage.show();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        r90.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                cBuild.canvas.setRotate(cBuild.canvas.getRotate()+90); //Gets the current rotation of the canvas and adds 90 degrees to it
            }
        });

        r180.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                cBuild.canvas.setRotate(cBuild.canvas.getRotate()+180); //Gets the current rotation of the canvas and adds 180 degrees to it
            }
        });

        r270.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                cBuild.canvas.setRotate(cBuild.canvas.getRotate()+270); //Gets the current rotation of the canvas and adds 270 degrees to it
            }
        });

        zoomI.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                ZoomIn();
            }
        });

        zoomO.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                ZoomOut();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*When "open" is clicked, the FileChooser will allow the user to choose
        a file off of their computer*/
        open.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                filers.open();  //Method that opens
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                filers.save();  //Method that saves the canvas
            }
        });

        saveAs.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                filer.saveAs();
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                filer.exit();
            }
        });
        newTab.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Tab tabNew = new Tab();
                tabNew.setContent(cBuild.canvasScroll);
                tabNew.setText("Pain(t)");
                tabPane.getTabs().add(tabNew);
            }
        });
        cResize.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                TextField rWidth = new TextField("Enter Width");
                TextField rHeight = new TextField("Enter Height");
                Button cConfirm = new Button("Resize");
                HBox hBox = new HBox(rWidth, rHeight, cConfirm);
                Scene scene = new Scene(hBox ,600,300);
                Stage resizePage = new Stage();
                resizePage.setTitle("Resize Canvas");
                resizePage.setScene(scene);
                resizePage.show();

                cConfirm.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        double cWidth = Double.parseDouble(rWidth.getText());
                        double cHeigth = Double.parseDouble(rHeight.getText());
                        cBuild.canvas.setHeight(cHeigth);
                        cBuild.canvas.setWidth(cWidth);
                        resizePage.close();
                    }
                });
            }
        });



        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        undo.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                //if stack of images is not empty
                if (cBuild.actionsU.isEmpty() == false)
                {
                        Image tempImage = canvas.actionsU.pop();
                        /*pushes the popped off image from the undo stack to the
                        redo stack*/
                        cBuild.actionsR.push(tempImage);

                    if(cBuild.actionsU.isEmpty() == false)
                    {
                        /*displays the second most recent screenshot of the
                        canvas on the canvas*/
                        cBuild.gc.drawImage(cBuild.actionsU.peek(), 0, 0);
                    }
                }

                else
                    System.out.println("Can't undo anymore");
            }
        });

        redo.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                if(cBuild.actionsR.isEmpty() == false)
                {
                        /*displays the most recent screenshot of the canvas on
                        the canvas from the redo stack*/
                    cBuild.gc.drawImage(cBuild.actionsR.peek(), 0, 0);

                    //if stack of images is not empty
                    if (cBuild.actionsR.isEmpty() == false)
                    {
                        //gets rid of most recent snapshot of screen
                        Image tempImage = canvas.actionsR.pop();
                                    /*pushes the popped off image from the redo
                                    stack back onto the undo Stack in order to
                                    alternate back and forth*/
                        cBuild.actionsU.push(tempImage);
                    }
                }
                else
                    System.out.println("Can't redo anymore");
            }
        });
    }
    /**
     * Creates a window for the user to enter the amount of sides they want on
     * the polygon
     */
    public void polyWindow()
    {
        Button enter = new Button("Enter");
        //creates Label for textField
        Label sides = new Label("Number of Sides: ");

        //creates text field for user to input their number
        TextField input = new TextField();
        input.setPromptText("Number of Sides");  //adds gint for text field

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(sides, 0, 1);  //adds label to grid
        grid.add(input, 1, 1);  //adds text field to grid
        grid.add(enter, 0, 2);  //adds button to grid

        //adds the grid to the window
        Scene polyScene = new Scene(grid, POLY_WINDOW_WIDTH, POLY_WINDOW_HEIGHT);

        Stage polyWindow = new Stage();  //creates the new window (stage)

        polyWindow.setTitle("Polygon Sides");
        polyWindow.setScene(polyScene);
        polyWindow.show();

        enter.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                //sets universal int variable polygon sides to users input
                polygonSides = Integer.parseInt(input.getText());
                cBuild.polygonSides = polygonSides;
                polyWindow.close();
            }
        });

        cBuild.mode = 9;  //when user chooses their tool it changes the int
        //variable to this number
    }

    /**
     * Zooms into the canvas by multiplying the canvas dimensions
     */
    public void ZoomIn()
    {
        cBuild.canvas.setWidth(cBuild.canvas.getWidth() * 2);
        cBuild.canvas.setHeight(cBuild.canvas.getHeight() * 2);
        //dp.setPrefSize(canvas.getWidth() + 100 , canvas.getHeight()+100);
        cBuild.gc.drawImage(cBuild.actionsU.peek(), 0, 0,
                cBuild.canvas.getWidth(), cBuild.canvas.getHeight());
        cBuild.actionsR.clear();
    }

    /**
     * Zooms out of the canvas by dividing the canvas dimensions
     */
    public void ZoomOut()
    {
        cBuild.canvas.setWidth(cBuild.canvas.getWidth() / 2);
        cBuild.canvas.setHeight(cBuild.canvas.getHeight() / 2);
        //dp.setPrefSize(canvas.getWidth() + 100 , canvas.getHeight()+100);
        cBuild.gc.drawImage(cBuild.actionsU.peek(), 0, 0,
                cBuild.canvas.getWidth(), cBuild.canvas.getHeight());
        cBuild.actionsR.clear();
    }
}
