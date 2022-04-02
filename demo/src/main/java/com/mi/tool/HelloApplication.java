package com.mi.tool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        app = HelloApplication.this;

        this.stage = stage;
        System.out.println("\"start\" = " + "start");
        show(HelloApplication.class.getResource("hello-view.fxml"));
    }

    private static HelloApplication app;

    public static HelloApplication getApp() {

        return app;
    }

    public void show(URL url) {
        try {

            System.out.println("url = " + url);
            FXMLLoader fxmlLoader = new FXMLLoader(url);

            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("面向对象开发");
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }

    Stage stage = new Stage();

    public void close() {
        stage.close();
    }


}