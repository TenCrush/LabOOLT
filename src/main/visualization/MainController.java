package main.visualization;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.omg.CORBA.Environment;
import queue.visualization.QueueController;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane mainContainer;

    public void showArrayListScreen() {

    }

    public void showQueueScreen() {
        QueueController queueVisualization = new QueueController();
        try {
            queueVisualization.show();
            Stage mainStage = (Stage) this.mainContainer.getScene().getWindow();
            mainStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHashTableScreen() {

    }

    public void exitProgram() {
        System.exit(0);
    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Data structure and algoritm visualization");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
