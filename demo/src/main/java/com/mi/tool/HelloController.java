package com.mi.tool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloController {
    @FXML
    public TextField idTxt;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField idX;
    @FXML
    private TextField idY;
    ExecutorService executorService;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    AdbUtil adbUtil = new AdbUtil();

    @FXML
    public void onStart(ActionEvent actionEvent) {

        String oriX = idX.getText().trim();


        String oriY = idY.getText().trim();

        String txt = idTxt.getText();

        do {

            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);


            executorService.execute(() -> {

                adbUtil.power();
                if (txt != null && !"".equals(txt)) {
                    adbUtil.input(txt);
                }

                if (oriX != null && !"".equals(oriX)) {

                    String[] splitX = oriX.split(";");
                    String[] splitY = oriY.split(";");
                    for (int i = 0; i < splitX.length; i++) {
                        adbUtil.click(Integer.parseInt(splitX[i]), Integer.parseInt(splitY[i]));

                    }
                }


            });

            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);


    }

    public void onEnd(ActionEvent actionEvent) {
        executorService.shutdown();

    }

    public void exit(ActionEvent actionEvent) {

        HelloApplication.getApp().close();

    }

    public void downScene(ActionEvent actionEvent) {


        HelloApplication downApplication = HelloApplication.getApp();
        downApplication.show(HelloApplication.class.getResource("down-view.fxml"));

    }
}