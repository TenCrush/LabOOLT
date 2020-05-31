package queue.visualization;

import global.visualization.GlobalConst;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.visualization.MainController;
import sun.tools.java.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueController {
    @FXML
    private TextField inputData;
    @FXML
    private Text validateMessage;
    @FXML
    private TextField enqueueValue;
    @FXML
    private Button startBtn;
    @FXML
    private Text alertEnqueueValue;
    @FXML
    private Pane queueContainer;

    private Queue<Integer> queue;
    private int enqueueNumberValue;
    private boolean isValid;
    private Queue<QueueViewModel> dsQueueViews;


    public QueueController() {
        this.isValid = false;
        queue = new LinkedList<Integer>();
        dsQueueViews = new LinkedList<QueueViewModel>();
    }

    public void show() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Queue.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Queue visualization");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                MainController main = new MainController();
                try {
                    main.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        primaryStage.show();


    }

    @FXML
    public void validateData() {

        this.queue = new LinkedList<Integer>();
        try {
            String text = this.inputData.getText();
            String[] dsNumber = text.split("\\s+");
            for (String numString :
                    dsNumber) {
                int number = Integer.parseInt(numString.trim());
                this.queue.add(number);
            }
            if (this.queue.isEmpty()) {
                this.isValid = false;
                this.validateMessage.setText("Error! Enter at least 1 number!");
                this.validateMessage.setFill(Color.web(GlobalConst.ERROR_COLOR));
                this.enableStartButton();
                return;
            }

            this.isValid = true;
            this.validateMessage.setText("Valid data!");
            this.validateMessage.setFill(Color.web(GlobalConst.SUCCESS_COLOR));
            this.enableStartButton();

        } catch (Exception e) {
            this.isValid = false;
            this.validateMessage.setText("Error! Invalid data!");
            this.validateMessage.setFill(Color.web(GlobalConst.ERROR_COLOR));
            this.enableStartButton();

        }
    }

    public void enableStartButton() {
        this.startBtn.setDisable(!isValid);
    }

    @FXML
    public void doQueueVisualize() {
        for (int number : this.queue) {
            QueueViewModel queueView = new QueueViewModel();
            queueView.setText(String.valueOf(number));
            this.dsQueueViews.add(queueView);
        }

        int maximumElement = (int) this.queueContainer.getWidth() / GlobalConst.QUEUE_WIDTH;


        System.out.println("Full width " + this.queueContainer.getWidth());
//        System.out.println("Number of children" +);
        System.out.println("Number of dsQueueView" + this.dsQueueViews.size());
        QueueViewModel first = this.dsQueueViews.poll();

//        this.inputData.setText(null);
//        this.isValid = false;
//        this.enableStartButton();
//        this.inputData.setDisable(true);
//        this.enqueueValue.setDisable(true);
//        fadeIn(first);

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(loginButtonType);
        boolean disabled = false; // computed based on content of text fields, for example
        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
        dialog.show();
    }

    @FXML
    public void validEnqueueValue() {
        try {
            String enqueueValue = this.enqueueValue.getText();
            this.enqueueNumberValue = Integer.parseInt(enqueueValue);
            QueueViewModel queueViewModel = new QueueViewModel();
            queueViewModel.setText(String.valueOf(this.enqueueNumberValue));
            this.dsQueueViews.add(queueViewModel);

        } catch (Exception e) {
            this.alertEnqueueValue.setText("Error! Unformat number");
        }

    }

    public void fadeIn(QueueViewModel queueView) {

        ObservableList<Node> dsChildren = this.queueContainer.getChildren();
        queueView.setLayoutX(this.queueContainer.getWidth() - GlobalConst.QUEUE_WIDTH); // init location of queue visualization element

        dsChildren.add(queueView);
        int noOfChildres = dsChildren.size();
        TranslateTransition fadeInAnimation = new TranslateTransition();
        fadeInAnimation.setDuration(Duration.millis(GlobalConst.FADED_TIME));

        if (noOfChildres * GlobalConst.QUEUE_WIDTH > this.queueContainer.getWidth()) { // need resize all node
            int newSize = (int) this.queueContainer.getWidth() / noOfChildres;
            queueView.setSize(newSize, newSize);
        }
        if (noOfChildres * GlobalConst.QUEUE_WIDTH > this.queueContainer.getWidth()) {

            return;
        }
        fadeInAnimation.setNode(queueView);
        fadeInAnimation.setByX(-(this.queueContainer.getWidth() - GlobalConst.QUEUE_WIDTH * noOfChildres - GlobalConst.VIEW_OFFSET));  //dia diem moi can di chuyen toi
        QueueController self = this;
        fadeInAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!self.dsQueueViews.isEmpty())
                    fadeIn(self.dsQueueViews.poll());
                else {
                    self.inputData.setText(null);
                    self.inputData.setDisable(false);
                    self.enqueueValue.setDisable(false);
                }

            }
        });

        fadeInAnimation.play();

    }

    public void fadeOut(Pane container) {

    }

}
