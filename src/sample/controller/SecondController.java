package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;
import sample.util.ExeclUtil;
import sample.util.SendMailUtil;

import java.io.File;

public class SecondController {

    @FXML
    private Button sendMail;

    public TextField begin;

    public TextField finish;

    public TextField title;

    public TextField content;

    public TextField reciver;

    public TextField loginName;

    public TextField password;

    private Stage stage;

    @FXML
    public void sendMail(ActionEvent actionEvent) {
        Workbook workbook = Controller.workbook;
        Integer firstIndex = Integer.parseInt(begin.getText()) - 1;
        Integer lastIndex = Integer.parseInt(finish.getText()) - 1;
        File file = ExeclUtil.hanle(workbook, firstIndex, lastIndex);
        String mailTitle = title.getText();
        String mailContent = content.getText();
        String[] recivers = reciver.getText().split(",");
        String loginNames = loginName.getText();
        String pass = password.getText();
        SendMailUtil.sendTextMail(mailTitle, mailContent, recivers, file, loginNames, pass);
    }

    public void setStage(Stage mainStage) {
        this.stage = mainStage;
    }

}
