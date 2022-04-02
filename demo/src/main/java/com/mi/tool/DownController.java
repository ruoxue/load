package com.mi.tool;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DownController {

    @FXML
    public TextField idVideo;
    @FXML
    public ProgressBar idPb;

    public void sendAdb(ActionEvent actionEvent) {
        HelloApplication helloApplication = HelloApplication.getApp();
        helloApplication.show(HelloApplication.class.getResource("hello-view.fxml"));

    }

    public void down(ActionEvent actionEvent) {

        String trim = idVideo.getText().trim();
//        idPb.setVisible(true);
//
//        idPb.setProgress(50);


    }
}
