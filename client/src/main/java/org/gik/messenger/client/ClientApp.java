package org.gik.messenger.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ClientApp extends Application   {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        primaryStage.setOnCloseRequest(event -> controller.exitAction());
        primaryStage.setTitle("Skyline");

        primaryStage.setScene(new Scene(root, 300, 375));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}