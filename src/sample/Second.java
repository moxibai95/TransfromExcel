package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.SecondController;

public class Second extends Main {

    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/secondSample.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/secondSample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 225));
        fxmlLoader.load();
        SecondController secondController =fxmlLoader.getController();
        secondController.setStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void  showWindow() throws Exception {
        start(stage);
    }
}
