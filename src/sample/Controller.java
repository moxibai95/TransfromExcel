package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

public class Controller {

    @FXML
    private Button selectFile;

    private Stage stage;

    @FXML
    public void selectExcel(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Microsoft Excel 工作表 (.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("Microsoft Excel 工作表 (.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilter1);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Workbook workbook = ExeclUtil.InputExcel(file);
            ExeclUtil.hanleExcel(workbook);
            ExeclUtil.OutputExcel(workbook, file.getName());
        }
    }

    public void setStage(Stage mainStage) {
        this.stage = mainStage;
    }
}
