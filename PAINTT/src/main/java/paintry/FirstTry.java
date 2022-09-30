package paintry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FirstTry extends Application {
    private final int CANVAS_WIDTH = 1750;
    private final int CANVAS_HEIGHT = 950;
    private ScrollPane scrollPane;
    private VBox root;
    protected TabPane tabPane;
    protected Tab tab;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pain(t)");

        CanvasBuild cBuild = new CanvasBuild(CANVAS_WIDTH,CANVAS_HEIGHT);

        FileClass filer = new FileClass(cBuild, primaryStage);

        MenuBuild mBuild = new MenuBuild(cBuild, filer);

        cBuild.setColorPicker(mBuild.colorPicker);

        root = new VBox(mBuild.menuBar, mBuild.toolBar);

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.getChildren().add(mBuild.tabPane);

        Scene scene = new Scene(root, cBuild.gc.getCanvas().getWidth(), cBuild.gc.getCanvas().getHeight());

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    /**
     * launches the program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
