package com.example.dogcat;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Timer;
import java.util.TimerTask;
public class GameOver {
    public void setScore(String value)
    {
/*        ScaleTransition scaleTransition=new ScaleTransition();
        scaleTransition.setNode(score);
        scaleTransition.setCycleCount(200);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setByY(1);
        scaleTransition.setByX(1);
        scaleTransition.setToX(1.3);
        scaleTransition.setToY(1.3);
        scaleTransition.setDuration(Duration.millis(25));
        scaleTransition.play();*/
        int num=Integer.parseInt(value);
        int[] sum = {0};
        int[] count = {0};
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sum[0] +=num/200;
                score.setText(String.valueOf(sum[0]));
                if(count[0] >=200) {
                    score.setText(value);
                    cancel();
                    timer.cancel();
                }
                count[0]++;
            }
        },0,25);
    }
    @FXML Text score;
    @FXML Button button;
}
