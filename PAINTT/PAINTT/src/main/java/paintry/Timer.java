package paintry;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 * @author Chris
 */
public class Timer extends Task{

    private FileClass filer;

    private Timeline clock;  //creates a timer object

    private int timesAutoSaved = 0;  //The amount of times the file has been autosaved

    private int minutes, seconds;

    private StringProperty time;

    private boolean complete = false;  //Checks if the timer is done

    /**
     * This is the constructor if the clock class that instantiates
     * the minutes and seconds for the timer and instantiates the filer class
     * @param filer
     * @param label
     */
    public Timer(FileClass filer, Label label)
    {
        this.filer = filer;

        time = new SimpleStringProperty();

        minutes = 2;
        seconds = 3;

        label.textProperty().bind(time);
    }

    /**
     * gets the current string property of the time variable
     * @return
     */
    public StringProperty getTime()
    {
        return time;
    }

    /**
     * gets the current minute value of the minute variable
     * @return
     */
    public int getMinutes()
    {
        return minutes;
    }

    /**
     * gets the current second value of the second variable
     * @return
     */
    public int getSeconds()
    {
        return seconds;
    }

    /**
     * This determines the actions of the TimeLine and the time variable
     * @return
     * @throws Exception
     */
    @Override
    protected Object call() throws Exception
    {
        final Duration PROBE_FREQ = Duration.seconds(1);

        clock = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new EventHandler<ActionEvent>()
                        {
                            @Override
                            public void handle (ActionEvent event){
                                time.setValue(tickTock());
                            }
                        }
                ), new KeyFrame(PROBE_FREQ)
        );

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
        return time.getValue();
    }

    /**
     * Decreases Timer by a second and returns a formatted string of remaining
     * time
     * @return
     */
    private String tickTock()
    {
        if(complete)
        {
            timesAutoSaved++;
            System.out.println("AutoSaved " + timesAutoSaved + " times");

            filer.save();  //If thread hasn't been started then start in Saveas (condition)

            reset();
        }

        if(minutes >= 0)
        {
            if(seconds>0)
            {
                seconds--;
                if(minutes == 0 && seconds == 0)
                {
                    System.out.println("Timer is complete");
                    complete = true;
                }
            }

            else
            {
                seconds = 59;
                minutes--;
            }

            String sec;
            if (seconds > 9)
            {
                sec = Integer.toString(seconds);
            }
            else
            {
                sec = ("0" + seconds);
            }

            time.setValue("0" + minutes + ":" + sec);

            return ("0" + minutes + ":" + sec);
        }

        else
        {
            System.out.println("Timer is complete");
            complete = true;
        }
        return "00:00";
    }

    /**
     * Resets the timer back to the starting time
     */
    public void reset()
    {
        minutes = 2;
        seconds = 0;
        complete = false;
    }
}