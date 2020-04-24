package org.gik.messenger.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private NetWork netWork;
    @FXML
    public TextField msgField;
    @FXML
    public TextArea mainArea;

    public void sendMsgAction(ActionEvent event) {
        netWork.sendMessage(msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }

    public void exitAction() {
        netWork.close();
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        netWork = new NetWork(new Callback() {

            @Override
            public void callback(String... str) {
                mainArea.appendText(str[0]+"\n");
            }
        });
    }
}
