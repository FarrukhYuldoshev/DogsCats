package com.example.dogcat;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class gameController implements Initializable {
    boolean canImove=true;
    @FXML AnchorPane mainPane;
    @FXML GridPane gridPane1;
    @FXML GridPane gridPane2;
    Scene scene;
    Stage stage;
    @FXML Text time;
    @FXML
    ImageView leftobj;
    @FXML ImageView rightobj;
    @FXML Text score;
    Timer mainTimer;
    TimerTask timerTask;
    Timer createAnimals;
    TimerTask createAnimalsTask;
    Timer createAnimalsright;
    TimerTask createAnimalsTaskright;
    float createAnimalpropability=0.70f; //hayvon yaratish
    float chapong=0.07f;
    int speed=700;
    int minute=1;
    int second=30;
    short level=1;
    int toUplevel=20;
    int currentCount=0;
    int myscore=0;
    int[] scores=new int[]{120,150,170,190,220,250,290};
    boolean isleftinleft;
    boolean isrightinright;
    animal leftanimal=animal.cat;
    animal rightanimal=animal.cat;
    Boolean left=true;
    Boolean right=true;
    boolean spressed=true;
    boolean lpressed=true;
    boolean lastrightistrue=false;
    boolean lastleftistrue=false;
    Timer waitsecond;
    @FXML FontAwesomeIconView mistake;
    @FXML Text leveltext;

    enum animal{cat,dog,mouse;}
    void setSecond(int second)
    {
        if(second<0)
            setMinute(minute-1);
        else this.second=second;
    }
    void setMinute(int minute)
    {
        if(minute>=0)
        {
            this.minute=minute;
            second=59;
        }
    }

    public void setMyscore(int myscore) {
            this.myscore+=myscore;
            if(this.myscore<0)
            {
                this.myscore=0;
                score.setText(String.valueOf(this.myscore));
            }
            else score.setText(String.valueOf(this.myscore));
    }

    public void setCurrentCount(int currentCount) {
          if(this.currentCount<currentCount)
          {
              this.currentCount=currentCount;
              if(this.currentCount>=toUplevel) {
                  setLevel((short) (level+1));
                  this.currentCount = 0;
              }
          }
          else
          {
              this.currentCount=0;
              setLevel((short) (level-1));
          }
    }
    public void replace(animal leftanimal,animal replaceleft,animal rightanimal,animal replaceright,animal other)
    {
        ImageView imageView=(ImageView) gridPane1.getChildren().get(0);
        imageView.setImage(new Image("white"+leftanimal+".png"));
        ImageView imageView2=(ImageView)gridPane2.getChildren().get(0);
        imageView2.setImage(new Image("red"+rightanimal+".png"));
        for(int i=1; i<gridPane1.getChildren().size(); i++)
        {
           ImageView image=(ImageView) gridPane1.getChildren().get(i);
            if(image.getImage().getUrl().contains("white"+leftanimal+".png"))
                image.setImage(new Image("white"+replaceleft+".png"));
            else   image.setImage(new Image("white"+other+".png"));
        }
        for(int i=1; i<gridPane2.getChildren().size(); i++)
        {
            ImageView image=(ImageView) gridPane2.getChildren().get(i);
            if(image.getImage().getUrl().contains("red"+rightanimal+".png"))
                image.setImage(new Image("red"+replaceright+".png"));
            else   image.setImage(new Image("red"+other+".png"));
        }
    }
    void replaneOne(GridPane gridPane1,animal leftanimal,animal replace, animal other,String color)
    {
        ImageView imageView=(ImageView) gridPane1.getChildren().get(0);
        imageView.setImage(new Image(color+leftanimal+".png"));
        for(int i=1; i<gridPane1.getChildren().size(); i++)
        {
            ImageView image=(ImageView) gridPane1.getChildren().get(i);
            if(image.getImage().getUrl().contains(color+leftanimal+".png"))
                image.setImage(new Image(color+replace+".png"));
            else
                image.setImage(new Image(color+other+".png"));

        }
    }
    public void setLevel(short level) {
        if(level<=0) this.level=1;
        else if(level<=7) {
            Random random=new Random();
            toUplevel=random.nextInt(10)+15;
            if(level>this.level)
            {
                canImove=false;
                speed-=60;
                createAnimalpropability+=0.08f;
                chapong-=0.03f;
                createAnimalsTaskright.cancel();
                createAnimalsright.cancel();
                createAnimalsTask.cancel();
                createAnimals.cancel();
                 waitsecond = new Timer();
                if(level<=4) {
                    //=================================================================================================================================================================================
                    boolean temp = random.nextBoolean();
                    switch (rightanimal) {
                        case cat: {

                            if (temp) {
                                leftanimal = animal.dog;
                                rightanimal = animal.dog;
                                replace(leftanimal, animal.cat, rightanimal, animal.cat,animal.mouse);
                            } else {
                                leftanimal = animal.mouse;
                                rightanimal = animal.mouse;
                                replace(leftanimal, animal.dog, rightanimal, animal.dog,animal.cat);
                            }
                            break;
                        }
//=================================================================================================================================================================================
                        case mouse: {
                            if (temp) {
                                leftanimal = animal.dog;
                                rightanimal = animal.dog;
                                replace(leftanimal, animal.cat, rightanimal, animal.cat,animal.mouse);
                            } else {
                                leftanimal = animal.cat;
                                rightanimal = animal.cat;
                                replace(leftanimal, animal.dog, rightanimal, animal.dog,animal.mouse);
                            }
                            break;
                        }
//=================================================================================================================================================================================
                        case dog: //dog
                        {
                            if (temp) {
                                leftanimal = animal.mouse;
                                rightanimal = animal.mouse;
                                replace(leftanimal, animal.cat, rightanimal, animal.cat,animal.dog);
                            } else {
                                leftanimal = animal.cat;
                                rightanimal = animal.cat;
                                replace(leftanimal, animal.dog, rightanimal, animal.dog,animal.mouse);
                            }
                            break;
                        }
                    }
                    this.level=level;
                }
                else
                {
                    boolean temp = random.nextBoolean();
                    if(leftanimal==animal.cat)
                    {
                        if(temp)
                        {
                            leftanimal=animal.dog;
                            replaneOne(gridPane1,leftanimal,animal.mouse,animal.cat,"white");
                        }
                        else
                        {
                            leftanimal=animal.mouse;
                            replaneOne(gridPane1,leftanimal,animal.cat,animal.dog,"white");
                        }
                    }
                    else if(leftanimal==animal.dog)
                    {
                        if(temp)
                        {
                            leftanimal=animal.cat;
                            replaneOne(gridPane1,leftanimal,animal.dog,animal.mouse,"white");
                        }
                        else
                        {
                            leftanimal=animal.mouse;
                            replaneOne(gridPane1,leftanimal,animal.cat,animal.dog,"white");
                        }
                    }
                    else // sichqon bosa
                    {
                        if(temp)
                        {
                            leftanimal=animal.cat;
                            replaneOne(gridPane1,leftanimal,animal.dog,animal.mouse,"white");
                        }
                        else
                        {
                            leftanimal=animal.dog;
                            replaneOne(gridPane1,leftanimal,animal.cat,animal.mouse,"white");
                        }
                    }
                    temp=random.nextBoolean();
                    if(leftanimal==animal.cat)
                    {
                        if(temp)
                        {
                            rightanimal =animal.dog;
                            replaneOne(gridPane2,rightanimal,animal.mouse,animal.cat,"red");

                        }
                        else
                        {
                            rightanimal=animal.mouse;
                            replaneOne(gridPane2,rightanimal,animal.cat,animal.dog,"red");
                        }
                    }
                    else if(leftanimal==animal.dog)
                    {
                        if(temp)
                        {
                            rightanimal=animal.cat;
                            replaneOne(gridPane2,rightanimal,animal.dog,animal.mouse,"red");
                        }
                        else
                        {
                            rightanimal=animal.mouse;
                            replaneOne(gridPane2,rightanimal,animal.cat,animal.dog,"red");
                        }
                    }
                    else // sichqon bosa
                    {
                        if(temp)
                        {
                            rightanimal=animal.cat;
                            replaneOne(gridPane2,rightanimal,animal.dog,animal.mouse,"red");
                        }
                        else
                        {
                            rightanimal=animal.dog;
                            replaneOne(gridPane2,rightanimal,animal.cat,animal.mouse,"red");
                        }
                    }
                    this.level=level;
                }
                    waitsecond.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            createAnimalsTaskright=setRightTimerTask();
                            createAnimalsright=new Timer();
                            createAnimalsright.schedule(createAnimalsTaskright,0,speed);
                            createAnimalsTask=setLeftTimerTask();
                            createAnimals=new Timer();
                            createAnimals.schedule(createAnimalsTask,0,speed);
                            cancel();
                            waitsecond.cancel();
                            canImove=true;
                        }
                    },2000,10);
            }
            else if(this.level>1)
            {
                speed+=50;
                createAnimalpropability-=0.05f;
                chapong+=0.01f;
            }
            this.level=level;
        }
        leveltext.setText(String.valueOf(this.level)+"/7");
    }

    void showMistake()
    {
        if(!mistake.isVisible()) {
            setCurrentCount(currentCount-1);
            mistake.setVisible(true);
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(mistake);
            rotateTransition.setDuration(Duration.millis(100));
            rotateTransition.setFromAngle(10);
            rotateTransition.setToAngle(-10);
            rotateTransition.setAutoReverse(true);
            rotateTransition.setCycleCount(10);
            rotateTransition.play();
            rotateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mistake.setVisible(false);
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        move1();
        move2();
         timerTask=new TimerTask() {
            @Override
            public void run() {
                String text;
                if(minute<10)
                    text="0"+minute;
                else text=String.valueOf(minute);
                if(second<10)
                    text=text+":0"+second;
                else text=text+":"+second;
                setSecond(second-1);
                time.setText(text);
                if(second==0&&minute==0)
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameover.fxml"));
                            Scene tempscene = null;
                            GameOver gameOver=new GameOver();
                            fxmlLoader.setController(gameOver);
                            try {
                                tempscene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage temp=new Stage();
                            temp.initStyle(StageStyle.UNDECORATED);
                            temp.initOwner(stage);
                            temp.setScene(tempscene);
                            gameOver.setScore(score.getText());
                            temp.show();
                            gameOver.button.setOnMouseClicked(
                                    mouseEvent -> {
                                        stage.close();
                                        temp.close();
                                    }
                            );
                            createAnimalsTask.cancel();
                            createAnimals.cancel();
                            createAnimalsTaskright.cancel();
                            createAnimalsright.cancel();
                            if(waitsecond!=null)
                            waitsecond.cancel();
                            cancel();
                            mainTimer.cancel();
                        }
                    });
                }
            }
        };
         mainTimer=new Timer();
         mainTimer.scheduleAtFixedRate(timerTask,0,1000);
        //================================================================================================= gridpane-1==========================================================
         createAnimalsTask=setLeftTimerTask();
//================================================================================================= END of gridpane-1==========================================================
//================================================================================================= gridpane-2==========================================================
        createAnimalsTaskright=setRightTimerTask();
        //=================================================================================================END of gridpane-2==========================================================
        createAnimalsright=new Timer();
         createAnimals=new Timer();
         createAnimals.schedule(createAnimalsTask,0,speed);
         createAnimalsright.schedule(createAnimalsTaskright,0,speed);
    }
    TimerTask setRightTimerTask()
    {
      TimerTask timerTask1= new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(canImove) {
                            for (int i = 1; i < gridPane2.getChildren().size(); i++) {
                                Node node = gridPane2.getChildren().get(i);
                                int row = GridPane.getRowIndex(node);
                                int column = GridPane.getColumnIndex(node);
                                if (row < 4) {
                                    TranslateTransition translateTransition = new TranslateTransition();
                                    translateTransition.setNode(node);
                                    translateTransition.setDuration(Duration.millis(100));
                                    translateTransition.setByY(0);
                                    translateTransition.setToY(100);
                                    translateTransition.play();
                                    translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {
                                            node.setTranslateY(0);
                                            GridPane.setRowIndex(node, row + 1);
                                        }
                                    });
                                } else {
                                    ImageView imageView = (ImageView) gridPane2.getChildren().get(i);
                                    String url = imageView.getImage().getUrl();
                                    int tempcolumn;
                                    if (isrightinright)
                                        tempcolumn = 1;
                                    else tempcolumn = 0;
                                    setScoreAndCheck(rightanimal, row, column, tempcolumn, url, "red");
                                    gridPane2.getChildren().remove(i);
                                    i--;
                                }
                            }
                            Random random = new Random();
                            if (random.nextFloat() <= createAnimalpropability) {
                                if (lastrightistrue) {
                                    if (random.nextFloat() <= chapong)
                                        createLeftSide(gridPane2, rightanimal, "red");
                                    else {
                                        createRightSide(gridPane2, rightanimal, "red");
                                        lastrightistrue = false;
                                    }
                                } else {
                                    if (random.nextFloat() <= chapong)
                                        createRightSide(gridPane2, rightanimal, "red");
                                    else {
                                        createLeftSide(gridPane2, rightanimal, "red");
                                        lastrightistrue = true;
                                    }
                                }
                            }
                            setCurrentCount(currentCount + 1);
                        }
                    }

                });

            }
        };
        return timerTask1;
    }
    void setScoreAndCheck(animal rightanimal,int row,int column,int tempcolumn,String url,String color)
    {
        switch (rightanimal)
        {
            case cat:{
                if(url.contains(color+"mouse.png")) {
                    if (row + 1 == 5 && column == tempcolumn)
                        setMyscore(scores[level - 1]);
                    else {
                        setMyscore(-400);
                                showMistake();
                    }
                }
                else if(url.contains(color+"dog.png"))
                {
                    if (row + 1 == 5 && column == tempcolumn) {
                        setMyscore(-400);
                                showMistake();
                    }
                    else
                        setMyscore(scores[level - 1]);
                }
                break;
            }
            case dog:{
                if(url.contains(color+"cat.png")) {
                    if (row + 1 == 5 && column == tempcolumn)
                        setMyscore(scores[level - 1]);
                    else {
                        setMyscore(-400);
                                showMistake();
                    }
                }
                else if(url.contains(color+"mouse.png"))
                {
                    if (row + 1 == 5 && column == tempcolumn) {
                        setMyscore(-400);
                                showMistake();
                    }
                    else setMyscore(scores[level - 1]);
                }
                break;
            }
            case mouse:{
                if(url.contains(color+"cat.png")||url.contains(color+"dog.png")) {
                    if (row + 1 == 5 && column == tempcolumn) {
                        setMyscore(-400);
                                showMistake();
                    }
                    else setMyscore(scores[level - 1]);
                }
                break;
            }
        }
    }
    TimerTask setLeftTimerTask()
    {
       TimerTask timerTask1= new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(canImove){
                        for (int i = 1; i < gridPane1.getChildren().size(); i++) {
                            Node node = gridPane1.getChildren().get(i);
                            int row = GridPane.getRowIndex(node);
                            int column = GridPane.getColumnIndex(node);
                            if (row < 4) {
                                TranslateTransition translateTransition = new TranslateTransition();
                                translateTransition.setNode(node);
                                translateTransition.setDuration(Duration.millis(100));
                                translateTransition.setByY(0);
                                translateTransition.setToY(100);
                                translateTransition.play();
                                translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        node.setTranslateY(0);
                                        GridPane.setRowIndex(node, row + 1);
                                    }
                                });
                            } else {
                                ImageView imageView = (ImageView) gridPane1.getChildren().get(i);
                                String url = imageView.getImage().getUrl();
                                int tempcolumn;
                                if (isleftinleft)
                                    tempcolumn = 0;
                                else tempcolumn = 1;
                                setScoreAndCheck(leftanimal, row, column, tempcolumn, url, "white");
                                gridPane1.getChildren().remove(i);
                                i--;
                            }
                        }
                        Random random = new Random();
                        if (random.nextFloat() <= createAnimalpropability) {
                            if (lastleftistrue) {
                                if (random.nextFloat() <= chapong)
                                    createLeftSide(gridPane1, leftanimal, "white");
                                else {
                                    createRightSide(gridPane1, leftanimal, "white");
                                    lastleftistrue = false;
                                }
                            } else {
                                if (random.nextFloat() <= chapong)
                                    createRightSide(gridPane1, leftanimal, "white");
                                else {
                                    createLeftSide(gridPane1, leftanimal, "white");
                                    lastleftistrue = true;
                                }
                            }
                        }
                    }
                    }
                });
            }
        };
       return timerTask1;
    }

    void createLeftSide(GridPane gridPane2,animal leftanimal,String color)
    {
        Random random=new Random();
        switch (leftanimal)
        {
            case cat: {
                if(random.nextFloat()>=0.5f)
                    createLeft(color+"dog.png",gridPane2);
                else
                    createLeft(color+"mouse.png",gridPane2);
                break;
            }
            case dog:{
                if(random.nextFloat()>=0.5f)
                    createLeft(color+"cat.png",gridPane2);
                else
                    createLeft(color+"mouse.png",gridPane2);
                break;
            }
            case mouse:{
                if(random.nextFloat()>=0.5f)
                    createLeft(color+"dog.png",gridPane2);
                else
                    createLeft(color+"cat.png",gridPane2);
                break;
            }
        }
    }
    void createRightSide(GridPane gridPane2,animal rightanimal,String color)
    {
        Random random=new Random();
        switch (rightanimal)
        {
            case cat: {
                if(random.nextFloat()>=0.5f)
                    createRight(color+"dog.png",gridPane2);
                else
                    createRight(color+"mouse.png",gridPane2);
                break;
            }
            case dog:{
                if(random.nextFloat()>=0.5f)
                    createRight(color+"cat.png",gridPane2);
                else
                    createRight(color+"mouse.png",gridPane2);
                break;
            }
            case mouse:{
                if(random.nextFloat()>=0.5f)
                    createRight(color+"dog.png",gridPane2);
                else
                    createRight(color+"cat.png",gridPane2);
                break;
            }
        }
    }
    void createRight(String img,GridPane gridPane2)
    {
        Image image=new Image(img);
        ImageView imageView=new ImageView(image);
        imageView.setRotate(180);
        imageView.setTranslateX(90);
        gridPane2.getChildren().add(imageView);
        GridPane.setRowIndex(imageView,0);
        GridPane.setColumnIndex(imageView,1);
    }
    void createLeft(String img,GridPane gridPane2)
    {
        Image image=new Image(img);
        ImageView imageView=new ImageView(image);
        imageView.setRotate(180);
        imageView.setTranslateX(90);
        gridPane2.getChildren().add(imageView);
        GridPane.setRowIndex(imageView,0);
        GridPane.setColumnIndex(imageView,0);
    }

    void set()
    {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
               createAnimalsTask.cancel();
                createAnimals.cancel();
                createAnimalsTaskright.cancel();
                createAnimalsright.cancel();
                if(waitsecond!=null)
                    waitsecond.cancel();
                mainTimer.cancel();
                stage.close();
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()==KeyCode.S&&spressed)
                {
                            spressed=false;
                            move1();
                }
                if(keyEvent.getCode()==KeyCode.L&&lpressed)
                {
                            lpressed=false;
                            move2();
                }
            }
        });
    }

    void move1()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(left) {
                    isleftinleft=false;
                }
                else {
                    isleftinleft=true;
                }
                RotateTransition rotateTransition=new RotateTransition();
                rotateTransition.setDuration(Duration.millis(100));
                rotateTransition.setNode(leftobj);
                rotateTransition.setByAngle(0);
                TranslateTransition translateTransition=new TranslateTransition();
                translateTransition.setNode(leftobj);
                translateTransition.setDuration(Duration.millis(150));
                translateTransition.setByX(90);
                if(left) {
                    translateTransition.setToX(310);
                    rotateTransition.setToAngle(30);
                }
                else {
                    translateTransition.setToX(-100);
                    rotateTransition.setToAngle(-30);
                }
                ParallelTransition parallelTransition=new ParallelTransition(translateTransition,rotateTransition);
                parallelTransition.play();
                parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        spressed=true;
                        if(left) {
                            GridPane.setColumnIndex(leftobj, 1);
                            leftobj.setRotate(0);
                            leftobj.setTranslateX(90);
                            left=false;
                        }
                        else {
                            GridPane.setColumnIndex(leftobj, 0);
                            leftobj.setRotate(0);
                            leftobj.setTranslateX(90);
                            left=true;
                        }
                    }
                });
            }
        });

    }
    void move2()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(right) {
                    isrightinright=true;
                }
                else {
                    isrightinright=false;
                }
                RotateTransition rotateTransition=new RotateTransition();
                rotateTransition.setDuration(Duration.millis(100));
                rotateTransition.setNode(rightobj);
                rotateTransition.setByAngle(0);
                TranslateTransition translateTransition=new TranslateTransition();
                translateTransition.setNode(rightobj);
                translateTransition.setDuration(Duration.millis(150));
                translateTransition.setByX(90);
                if(right) {
                    translateTransition.setToX(310);
                    rotateTransition.setToAngle(30);
                }
                else {
                    translateTransition.setToX(-100);
                    rotateTransition.setToAngle(-30);
                }
                ParallelTransition parallelTransition=new ParallelTransition(translateTransition,rotateTransition);
                parallelTransition.play();
                parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        lpressed=true;
                        if(right) {
                            GridPane.setColumnIndex(rightobj, 1);
                            rightobj.setRotate(0);
                            rightobj.setTranslateX(90);
                            right=false;
                        }
                        else {
                            GridPane.setColumnIndex(rightobj, 0);
                            rightobj.setRotate(0);
                            rightobj.setTranslateX(90);
                            right=true;
                        }
                    }
                });
            }
        });
    }
}
