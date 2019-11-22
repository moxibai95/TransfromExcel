package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;
import sample.util.ExeclUtil;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private Button selectFile;

    private Stage stage;

    public static Workbook workbook = null;

    @FXML
    public void selectExcel(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Microsoft Excel 工作表 (.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("Microsoft Excel 工作表 (.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilter1);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            workbook = ExeclUtil.InputExcel(file);
        }
        open();
    }

    public void setStage(Stage mainStage) {
        this.stage = mainStage;
    }

    public void open() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/secondSample.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/secondSample.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 225));
        fxmlLoader.load();
        SecondController secondController =fxmlLoader.getController();
        secondController.setStage(stage);
        stage.show();
    }
}
