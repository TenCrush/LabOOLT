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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
            this.fadeIn(queueView);
        }
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
        queueView.setLayoutX(this.queueContainer.getWidth() - GlobalConst.QUEUE_WIDTH);
        dsChildren.add(queueView);

        TranslateTransition fadeInAnimation = new TranslateTransition();
        fadeInAnimation.setDuration(Duration.millis(GlobalConst.FADED_TIME));
        fadeInAnimation.setNode(queueView);
        fadeInAnimation.setByX(-(this.queueContainer.getWidth() - GlobalConst.QUEUE_WIDTH - GlobalConst.VIEW_OFFSET));
        fadeInAnimation.play();

//        this.queueContainer.getChildren().add(queueView);
//        set default location for queue view
//        this.setLayoutX(container.getWidth() - GlobalConst.QUEUE_WIDTH);
//
//        ObservableList<Node> dsChildren = container.getChildren();
//        dsChildren.add(this);
//
//        Parent parent = container.getParent();
//        parent.getLayoutX();
//
//        int noOfChildren = dsChildren.size();
//        double containerWidth = container.getWidth();
//
//        System.out.println("no of children :" + noOfChildren);
//        System.out.println("length of container : " + containerWidth);
//        System.out.println("First location container: " + container.getLayoutX());
//        System.out.println("First location container: " + parent.getLayoutX());
//



    }

    public void fadeOut(Pane container) {

    }

}
