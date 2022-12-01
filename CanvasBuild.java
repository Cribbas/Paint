package paintry;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.util.Stack;

public class CanvasBuild {
    protected Canvas canvas;
    protected GraphicsContext gc;
    private Pane drawPane;
    protected final int CANVAS_WIDTH = 1750;
    protected final int CANVAS_HEIGHT = 950;
    private double mouseClickX, mouseClickY;
    private double xClick, startY, startX, yClick ;
    protected int mode;
    private double zI;
    protected ScrollPane canvasScroll;
    private ImageView imageView;
    private PixelReader pixelReader;
    private Line line;
    private Rectangle rect;
    private Circle circle;
    private Ellipse oval;
    private ColorPicker colorPicker = new ColorPicker();
    private Image image;
    protected Tab cTab;
    protected TabPane canvasTab;
    private Polygon poly;
    protected int polygonSides = 0;

    private Rectangle cropRect;
    private Rectangle selectRect;
    private int cropWidth, cropHeight;
    private ImageView cropImage;

    protected Stack<Image> actionsU = new Stack();
    protected Stack<Image> actionsR = new Stack();
    protected Stack<Image> mainPicture = new Stack();


    public CanvasBuild(double x, double y){
        canvas = new Canvas(x,y);

        gc = canvas.getGraphicsContext2D();

        drawPane = new Pane(canvas);
        canvasScroll = new ScrollPane();
        canvasScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasScroll.setContent(drawPane);


        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);

        addToStack(screenshot());

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch(mode){
                    case 0: //free draw
                    {
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorPicker.getValue());
                        gc.stroke();
                        break;
                    }
                    case 1: //line
                    {
                        line = new Line(event.getX(), event.getY(),
                                event.getX(), event.getY());
                        line.setStroke(colorPicker.getValue());
                        line.setStrokeWidth(gc.getLineWidth());
                        line.setFill(colorPicker.getValue());
                        drawPane.getChildren().add(line);
                        gc.beginPath();
                        gc.moveTo(event.getX(),event.getY());
                        gc.setStroke(colorPicker.getValue());
                        gc.setLineWidth(gc.getLineWidth());
                        break;
                    }
                    case 2: //rectangle
                    {
                        rect = new Rectangle(event.getX() , event.getY(), 0 , 0);  //creates rectangle
                        rect.setStroke(colorPicker.getValue());
                        rect.setStrokeWidth(gc.getLineWidth());
                        rect.setFill(colorPicker.getValue());
                        //adds rectangle to drawing pane
                        drawPane.getChildren().add(rect);
                        gc.beginPath();
                        //variables to keep track of first click x
                        startX = event.getX();
                        //variables to keep track of first click y
                        startY = event.getY();
                        //sets starting point of line
                        gc.moveTo(event.getX(), event.getY());
                        //Gets the color chosen by the user for the stroke
                        gc.setStroke(colorPicker.getValue());
                        //sets the width of line to the width chose under the width button
                        gc.setLineWidth(gc.getLineWidth());
                        break;
                    }
                    case 3: //circle
                    {
                        startX = event.getX();
                        startY = event.getY();

                        circle = new Circle(startX, startY, 0); //creates circle object
                        circle.setStroke(colorPicker.getValue());
                        circle.setStrokeWidth(gc.getLineWidth());
                        circle.setFill(colorPicker.getValue());

                        drawPane.getChildren().add(circle); //adds line to drawing pane
                        gc.beginPath();

                        startX = event.getX(); //variables to keep track of first click x
                        startY = event.getY(); //variables to keep track of first click y

                        gc.moveTo(event.getX(), event.getY());//sets starting point of line

                        gc.setStroke(colorPicker.getValue());//Gets the color chosen by the user for the stroke
                        gc.setLineWidth(gc.getLineWidth());
                        break;
                    }
                    case 4: //oval
                    {
                        oval = new Ellipse(event.getX() , event.getY(), 0 , 0);  //creates ellipse
                        oval.setStroke(colorPicker.getValue());
                        oval.setStrokeWidth(gc.getLineWidth());
                        oval.setFill(colorPicker.getValue());

                        drawPane.getChildren().add(oval); //adds oval to drawing pane
                        gc.beginPath();

                        startX = event.getX(); //variables to keep track of first click x
                        startY = event.getY(); //variables to keep track of first click y

                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorPicker.getValue());
                        gc.setLineWidth(gc.getLineWidth());
                        break;
                    }
                    case 5: //dropper
                    {
                        image = screenshot();
                        pixelReader = image.getPixelReader();
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        Color c = pixelReader.getColor(x , y);
                        colorPicker.setValue(c);
                        break;
                    }
                    case 6: //eraser
                    {
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(Color.WHITE);  //Makes color equal color of canvas
                        gc.stroke();
                        break;
                    }
                    case 7: //dashed line
                    {
                        line = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                        line.getStrokeDashArray().addAll(25d, 10d);
                        line.setStroke(colorPicker.getValue());
                        line.setStrokeWidth(gc.getLineWidth());
                        line.setFill(colorPicker.getValue());
                        drawPane.getChildren().add(line);
                        gc.beginPath();
                        gc.moveTo(event.getX(),event.getY());
                        gc.setStroke(colorPicker.getValue());
                        gc.setLineWidth(gc.getLineWidth());
                        break;
                    }
                    case 8: //square
                    {
                        rect = new Rectangle(event.getX() , event.getY() ,
                                0 , 0);  //creates rectangle
                        rect.setStroke(colorPicker.getValue());
                        rect.setStrokeWidth(gc.getLineWidth());
                        rect.setFill(colorPicker.getValue());
                        //adds rectangle to drawing pane
                        drawPane.getChildren().add(rect);
                        gc.beginPath();
                        //variables to keep track of first click x
                        startX = event.getX();
                        //variables to keep track of first click y
                        startY = event.getY();
                        //sets starting point of line
                        gc.moveTo(event.getX(), event.getY());
                        //Gets the color chosen by the user for the stroke
                        gc.setStroke(colorPicker.getValue());
                            /*sets the width of line to the width chose under
                            the width button*/
                        gc.setLineWidth(gc.getLineWidth());
                        break;
                    }
                    case 9: //polygon
                    {
                        poly = new Polygon();
                        drawPane.getChildren().add(poly);
                        startX = event.getX();
                        startY = event.getY();
                        gc.beginPath();
                        //variables to keep track of first click x
                        startX = event.getX();
                        //variables to keep track of first click y
                        startY = event.getY();
                        //sets starting point of line
                        gc.moveTo(event.getX(), event.getY());
                        //Gets the color chosen by the user for the stroke
                        gc.setStroke(colorPicker.getValue());
                            /*sets the width of line to the width chose under
                            the width button*/
                        gc.setLineWidth(gc.getLineWidth());

                        break;
                    }
                    case 10: //cropping
                    {
                        cropRect = new Rectangle(event.getX() , event.getY(),
                                0 , 0);  //creates rectangle
                        cropRect.setStroke(Color.BLACK);
                        cropRect.setFill(Color.TRANSPARENT);
                        cropRect.setStrokeWidth(gc.getLineWidth());
                        //adds rectangle to drawing pane
                        drawPane.getChildren().add(cropRect);
                        gc.beginPath();
                        //variables to keep track of first click x
                        startX = event.getX();
                        //variables to keep track of first click y
                        startY = event.getY();
                        break;
                    }
                    case 11: //dragging crop
                    {
                        gc.beginPath();
                        Paint tempColor = gc.getFill();
                        gc.setFill(Color.WHITE);
                        gc.fillRect(cropRect.getX(), cropRect.getY(),
                                cropRect.getWidth(), cropRect.getHeight());
                        gc.setFill(tempColor);
                        gc.closePath();

                        Image selectedImage = mainPicture.peek();
                        PixelReader pr = selectedImage.getPixelReader();
                        WritableImage newImage = new WritableImage(pr,
                                (int) cropRect.getX(), (int) cropRect.getY(),
                                (int) cropRect.getWidth(),
                                (int) cropRect.getHeight());

                        cropImage = new ImageView(newImage);
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        drawPane.getChildren().add(cropImage);
                        break;
                    }
                    case 12:
                    {
                        cropRect = new Rectangle(event.getX() ,
                                event.getY() , 0 , 0);  //creates rectangle
                        cropRect.setStroke(Color.BLACK);
                        cropRect.setFill(Color.TRANSPARENT);
                        cropRect.setStrokeWidth(gc.getLineWidth());
                        //adds rectangle to drawing pane
                        drawPane.getChildren().add(cropRect);
                        gc.beginPath();
                        //variables to keep track of first click x
                        startX = event.getX();
                        //variables to keep track of first click y
                        startY = event.getY();
                        break;
                    }
                    case 13:
                    {
                        startX = event.getX();
                        startY = event.getY();
                        Image selectedImage = mainPicture.peek();
                        PixelReader pr = selectedImage.getPixelReader();
                        WritableImage newImage = new WritableImage(pr,
                                (int) cropRect.getX(), (int) cropRect.getY(),
                                cropWidth, cropHeight);

                        cropImage = new ImageView(newImage);
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        drawPane.getChildren().add(cropImage);
                        break;
                    }
                    case 14:
                    {
                        poly = new Polygon();
                        drawPane.getChildren().add(poly);
                        startX = event.getX();
                        startY = event.getY();
                        gc.beginPath();
                        //variables to keep track of first click x
                        startX = event.getX();
                        //variables to keep track of first click y
                        startY = event.getY();
                        //sets starting point of line
                        gc.moveTo(event.getX(), event.getY());
                        //Gets the color chosen by the user for the stroke
                        gc.setStroke(colorPicker.getValue());
                            /*sets the width of line to the width chose under
                            the width button*/
                        gc.setLineWidth(gc.getLineWidth());

                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch(mode){
                    case 0: //pencil
                    {
                        gc.lineTo(event.getX(), event.getY()); //creates stroke with the drag
                        gc.setStroke(colorPicker.getValue()); //gets color user has chosen
                        gc.stroke(); //makes drag stroke
                        break;
                    }
                    case 1: //line
                    {
                        //follows the mouse for user to draw shape on a pane
                        line.setEndX(event.getX());
                        line.setEndY(event.getY());
                        break;
                    }
                    case 2: //rectangle
                    {
                        rect.setWidth(event.getX() - startX);
                        rect.setHeight(event.getY() - startY);
                        break;
                    }
                    case 3: //circle
                    {
                        double radius = (event.getY() - startY)/2;
                        circle.setCenterX(startX + radius); //follows the mouse for user to draw shape on a pane
                        circle.setCenterY(startY + radius);
                        circle.setRadius(radius);
                        break;
                    }
                    case 4: //ellipse
                    {
                        double radiusX = (event.getX() -startX)/2;
                        double radiusY = (event.getY() -startY)/2;
                        oval.setRadiusX((event.getX() -startX)/2);
                        oval.setRadiusY((event.getY() -startY)/2);
                        //follows the mouse for user to draw shape on a pane
                        oval.setCenterX(startX - radiusX);
                        oval.setCenterY(startY - radiusY);
                        break;
                    }
                    case 5: //dropper
                    {
                        break;
                    }
                    case 6: //eraser
                    {
                        gc.lineTo(event.getX(), event.getY());
                        gc.setStroke(Color.WHITE);//makes color pathing white (same color as canvas
                        gc.stroke();  //makes drag stroke
                        break;
                    }
                    case 7:
                    {
                        line.setEndX(event.getX());
                        line.setEndY(event.getY());
                        break;
                    }
                    case 8:
                    {
                        rect.setWidth(event.getY() - startY);
                        rect.setHeight(event.getY() - startY);
                        break;
                    }
                    case 9: //polygon
                    {
                        poly.getPoints().addAll(event.getX(), event.getY());
                        final double angleStep = Math.PI * 2 / polygonSides;
                        double radius = Math.sqrt(((event.getX()-startX)*
                                (event.getX()-startX)) +
                                ((event.getY()-startY)*
                                        (event.getY()-startY)));

                        double angle = Math.atan2(event.getY()-startY,
                                event.getX()-startX);

                        for (int i = 0; i < polygonSides; i++,
                                angle += angleStep)
                        {
                            poly.getPoints().addAll(Math.cos(angle) *
                                    radius + startX, Math.sin(angle) *
                                    radius + startY);
                        }

                        poly.setStroke(colorPicker.getValue());
                        poly.setStrokeWidth(gc.getLineWidth());
                        poly.setFill(colorPicker.getValue());

                        break;
                    }
                    case 10: //cropping
                    {
                        //follows the mouse for user to draw shape on a pane
                        cropRect.setWidth(event.getX() - startX);
                        cropRect.setHeight(event.getY() - startY);
                        break;
                    }
                    case 11: //dragging cropped image
                    {
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        break;
                    }
                    case 12:
                    {
                        //follows the mouse for user to draw shape on a pane
                        cropRect.setWidth(event.getX() - startX);
                        cropRect.setHeight(event.getY() - startY);
                        break;
                    }
                    case 13:
                    {
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        break;
                    }
                    case 14:
                    {
                        poly.getPoints().addAll(event.getX(), event.getY());
                        final double angleStep = Math.PI * 2 / 3;
                        double radius = Math.sqrt(((event.getX()-startX)*
                                (event.getX()-startX)) +
                                ((event.getY()-startY)*
                                        (event.getY()-startY)));

                        double angle = Math.atan2(event.getY()-startY,
                                event.getX()-startX);

                        for (int i = 0; i < 3; i++,
                                angle += angleStep)
                        {
                            poly.getPoints().addAll(Math.cos(angle) *
                                    radius + startX, Math.sin(angle) *
                                    radius + startY);
                        }

                        poly.setStroke(colorPicker.getValue());
                        poly.setStrokeWidth(gc.getLineWidth());
                        poly.setFill(colorPicker.getValue());

                        break;
                    }
                }
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch(mode){
                    case 0: //pencil
                    {
                        gc.closePath();
                        addToStack(screenshot());
                        break;
                    }
                    case 1: //line
                    {
                        drawPane.getChildren().remove(line);
                        gc.lineTo(event.getX(), event.getY()); //makes a stroke between the first mouse press and the mouse release*/
                        gc.stroke(); //Makes the stroke between the points
                        gc.closePath();
                        addToStack(screenshot());
                        break;
                    }
                    case 2: //Rectangle
                    {
                        drawPane.getChildren().remove(rect);
                        gc.setFill(colorPicker.getValue()); //sets fill color to color picker
                        gc.rect(startX , startY, event.getX() - startX, event.getY() - startY); //creates a rectangle on the canvas
                        gc.stroke();
                        gc.fill(); //fills the rectangle shape
                        gc.closePath();
                        addToStack(screenshot());
                        break;
                    }
                    case 3: //circle
                    {
                        drawPane.getChildren().remove(circle);

                        gc.setFill(colorPicker.getValue()); //sets fill color to color picker
                        gc.strokeOval(startX, startY, event.getY()-startY, event.getY()-startY); //creates circle by making an oval and treating it the same way it treated the square made from a rectangle
                        gc.fillOval(startX, startY, event.getY()-startY, event.getY()-startY); //fills the entire circle with users color choice
                        gc.stroke();
                        gc.closePath();
                        addToStack(screenshot());
                        break;
                    }
                    case 4: //ellipse
                    {
                        drawPane.getChildren().remove(oval);

                        gc.setFill(colorPicker.getValue());  //sets fill color to color picker
                        gc.strokeOval(startX, startY, event.getX() - startX, event.getY()-startY);
                        gc.fillOval(startX, startY, event.getX()-startX, event.getY()-startY); //fills the entire oval with users color choice
                        gc.stroke();
                        gc.closePath();
                        addToStack(screenshot());
                        break;
                    }
                    case 5: //dropper
                    {
                        break;
                    }
                    case 6: //eraser
                    {
                        gc.closePath();
                        addToStack(screenshot());
                        break;
                    }
                    case 7: //dashed line
                    {
                        drawPane.getChildren().remove(line);
                        gc.setLineDashes(25,10);
                        gc.lineTo(event.getX(), event.getY()); //makes a stroke between the first mouse press and the mouse release*/
                        gc.stroke(); //Makes the stroke between the points
                        gc.closePath();
                        gc.setLineDashes(0,0);
                        addToStack(screenshot());
                        break;
                    }
                    case 8: //square
                    {
                        drawPane.getChildren().remove(rect);
                        //sets fill color to colorpicker
                        gc.setFill(colorPicker.getValue());

                            /*creates a square on canvas by taking the
                            difference between y-value of the unclick and the
                            y-value click and uses thpse as height and width*/
                        gc.rect(startX , startY, event.getY()-startY,
                                event.getY()-startY);
                        gc.stroke();
                        gc.fill();  //fills the square shape
                        gc.closePath();
                        addToStack(screenshot());
                        break;
                    }
                    case 9: //polygon
                    {
                        drawPane.getChildren().remove(poly);
                        //sets fill color to colorpicker
                        gc.setFill(colorPicker.getValue());
                        gc.stroke();
                        gc.fill();  //fills the square shape
                        gc.closePath();
                        drawPolygon(canvas, polygonSides, startX, startY,
                                event.getX(), event.getY());//creates the polygon
                        addToStack(screenshot());
                        break;
                    }

                    case 10:
                    {
                        int width = (int)(event.getX() - startX);
                        int height = (int)(event.getY() - startY);
                        drawPane.getChildren().remove(cropRect);
                        gc.closePath();
                        mode = 11;
                        break;
                    }

                    case 11: //dragging cropped image
                    {
                        drawPane.getChildren().remove(cropImage);
                        gc.drawImage(cropImage.getImage(), event.getX(),
                                event.getY(), cropRect.getWidth(),
                                cropRect.getHeight());
                        mode = 86;
                        addToStack(screenshot());
                        break;
                    }

                    case 12:
                    {
                        cropWidth = (int)(event.getX() - startX);
                        cropHeight = (int)(event.getY() - startY);
                        drawPane.getChildren().remove(cropRect);
                        gc.closePath();
                        mode = 13;
                        break;
                    }

                    case 13:
                    {
                        drawPane.getChildren().remove(cropImage);
                        gc.drawImage(cropImage.getImage(), event.getX(),
                                event.getY(), cropRect.getWidth(),
                                cropRect.getHeight());
                        mode = 86;
                        break;
                    }
                    case 14: //triangle
                    {
                        drawPane.getChildren().remove(poly);
                        //sets fill color to color picker
                        gc.setFill(colorPicker.getValue());
                        gc.stroke();
                        gc.fill();  //fills the square shape
                        gc.closePath();
                        drawPolygon(canvas, 3, startX, startY,
                                event.getX(), event.getY());//creates the polygon
                        addToStack(screenshot());
                        break;
                    }

                    case 86:
                    {
                        //stops all tools from working
                        //funny because 86
                        break;
                    }

                    default:
                    {
                        break;
                    }
                }
            }
        });
    }

    /**
     * Pushes an image onto a stack
     * @param i
     */
    public void addToStack(Image i){
        actionsU.push(i);
    }

    /**
     * Takes the number of sides user dictates and makes the arrays that size +1
     * the first paired point is the user's first mouse click
     * Then the method performs math (based off of the user's first click and
     * mouse release) to create the other paired points that represent the
     * vertices of the polygon once both arrays are filled with the paired
     * points then a polygon is drawn onto the canvas
     * @param canvas
     * @param numPoints
     * @param xClickPoint
     * @param yClickPoint
     * @param secondClickX
     * @param secondClickY
     */
    public void drawPolygon(Canvas canvas, int numPoints, double xClickPoint, double yClickPoint, double secondClickX, double secondClickY){
        double[] xPoints = new double[numPoints+1];
        double[] yPoints = new double[numPoints+1];

        xPoints[0] = secondClickX;
        yPoints[0] = secondClickY;

        final double angleStep = Math.PI * 2 / numPoints;

        double radius = Math.sqrt(((secondClickX-xClickPoint)*
                (secondClickX-xClickPoint)) + ((secondClickY-yClickPoint)*
                (secondClickY-yClickPoint)));

        double angle = Math.atan2(secondClickY-yClickPoint,
                secondClickX-xClickPoint);

        for (int i = 0; i < numPoints+1; i++, angle += angleStep) {
            xPoints[i] = Math.cos(angle) * radius + xClickPoint;
            yPoints[i] = Math.sin(angle) * radius + yClickPoint;
        }

        gc.fillPolygon(xPoints, yPoints, numPoints+1);


        gc.strokePolygon(xPoints, yPoints, numPoints+1);
    }  //creates the real polygon that is on the canvas
    public Image screenshot()
    {
        WritableImage tempImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT); //makes image size of canvas
        /*makes the tempImage that was only as big as canvas now equal to a
        screenshot of the current canvas*/
        canvas.snapshot(null, tempImage);
        ImageView imageView = new ImageView(tempImage);
        return imageView.getImage();
    }

    /**
     * Sets the ColorPicker Value to the value chosen in the MenuBuild class
     * @param cp
     */
    public void setColorPicker(ColorPicker cp)
    {
        colorPicker = cp;
    }

}
