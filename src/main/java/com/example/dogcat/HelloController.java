package com.example.dogcat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloController {
    @FXML
    protected void onHelloButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game.fxml"));
        gameController controller=new gameController();
        fxmlLoader.setController(controller);
        Scene scene=new Scene(fxmlLoader.load());
        gameController gameController=new gameController();
        Stage stage=new Stage();
        stage.setTitle("Dogs vs cats");
        stage.setScene(scene);
        stage.show();
        controller.scene=scene;
        controller.stage=stage;
        controller.set();
    }
}