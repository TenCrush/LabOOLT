package queue.visualization;

import java.lang.*;

import global.visualization.GlobalConst;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
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
    @FXML
    private Pane peekContainer;
    @FXML
    private Text currentNoOfElementTxt;
    @FXML
    private Button enqueueBtn;
    @FXML
    private Button dequeueBtn;
    @FXML
    private Button peekBtn;
    @FXML
    private Button clearAllBtn;


    private Queue<Integer> queue;
    private int enqueueNumberValue;
    private Queue<QueueViewModel> dsQueueViews;
    private static QueueViewModel peekedQueueView;


    public QueueController() {
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
            String text = this.inputData.getText().trim();
            String[] dsNumber = text.split("\\s+");
            for (String numString :
                    dsNumber) {
                int number = Integer.parseInt(numString.trim());
                this.queue.add(number);
            }
            if (this.queue.isEmpty()) {
                this.validateMessage.setText("Error! Enter at least 1 number!");
                this.validateMessage.setFill(Color.web(GlobalConst.ERROR_COLOR));
                this.startBtn.setDisable(true);
                return;
            }

            this.validateMessage.setText("Valid data!");
            this.validateMessage.setFill(Color.web(GlobalConst.SUCCESS_COLOR));
            this.startBtn.setDisable(false);

        } catch (Exception e) {
            this.validateMessage.setText("Error! Invalid data!");
            this.validateMessage.setFill(Color.web(GlobalConst.ERROR_COLOR));
            this.startBtn.setDisable(true);
        }
    }
    @FXML
    public void doQueueVisualize() {
        this.dsQueueViews = new LinkedList<>();
        for (int number : this.queue) {
            QueueViewModel queueView = new QueueViewModel();
            queueView.setText(String.valueOf(number));
            this.dsQueueViews.add(queueView);
        }

        int maximumElement = (int) this.queueContainer.getWidth() / GlobalConst.QUEUE_WIDTH;
        if ((this.queueContainer.getChildren().size() + this.dsQueueViews.size()) > maximumElement) {
            this.showPopup("Maximum number of element is reach");
            return;
        }
        this.disableAll();
        this.addListAnimation(this.dsQueueViews, 1);
    }
    @FXML
    public void validEnqueueValue() {
        this.dsQueueViews = new LinkedList<>();
        try {

            String enqueueValue = this.enqueueValue.getText();
            this.enqueueNumberValue = Integer.parseInt(enqueueValue.trim());
            QueueViewModel queueViewModel = new QueueViewModel();
            queueViewModel.setText(String.valueOf(this.enqueueNumberValue));
            this.dsQueueViews.add(queueViewModel);

            this.alertEnqueueValue.setText("Valid data!");
            this.alertEnqueueValue.setFill(Color.web(GlobalConst.SUCCESS_COLOR));
            this.enqueueBtn.setDisable(false);

        } catch (Exception e) {
            this.alertEnqueueValue.setText("Error! Unformat number");
            this.alertEnqueueValue.setFill(Color.web(GlobalConst.ERROR_COLOR));
            this.enqueueBtn.setDisable(true);

        }

    }

    public void addListAnimation(Queue<QueueViewModel> queueViews, int resetOption) {
        if (queueViews.isEmpty()) {
            if (resetOption == 1) {
                this.resetListData();
                this.startBtn.setDisable(true);
            }
            if (resetOption == 2) {
                this.resetSingleData();
                this.enqueueBtn.setDisable(true);
            }
            this.enableAllForm();
            this.enableBtn();
            return;
        }

        QueueViewModel queueView = queueViews.poll();
        ObservableList<Node> dsChildren = this.queueContainer.getChildren();
        queueView.setLayoutX(this.queueContainer.getWidth() - GlobalConst.QUEUE_WIDTH); // init location of queue visualization element
        this.increaseNoOfElement();

        dsChildren.add(queueView);

        int noOfChildres = dsChildren.size();
        TranslateTransition fadeInAnimation = new TranslateTransition(Duration.millis(GlobalConst.FADED_TIME));

        fadeInAnimation.setNode(queueView);
        fadeInAnimation.setByX(-(this.queueContainer.getWidth() - GlobalConst.QUEUE_WIDTH * noOfChildres - GlobalConst.VIEW_OFFSET));  //dia diem moi can di chuyen toi

        QueueController self = this;
        fadeInAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addListAnimation(queueViews, resetOption);
            }
        });
        fadeInAnimation.play();
    }

    public void removeAll() {
        if (this.queueContainer.getChildren().isEmpty()) {
            this.enableBtn();
            return;
        }

        Node queueView = this.queueContainer.getChildren().get(0);
        this.decreaseNoOfElement();
        FadeTransition fadeOutAnimation = new FadeTransition(Duration.millis(GlobalConst.FADED_TIME));
        TranslateTransition runAnimation = new TranslateTransition(Duration.millis(GlobalConst.FADED_TIME));

        fadeOutAnimation.setNode(queueView);
        runAnimation.setNode(queueView);

        runAnimation.setByX(-(this.queueContainer.getWidth() + GlobalConst.QUEUE_WIDTH));  //dia diem moi can di chuyen toi

        fadeOutAnimation.setFromValue(1);
        fadeOutAnimation.setToValue(0);

        QueueController self = this;

        fadeOutAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                self.queueContainer.getChildren().remove(0);
                self.removeAll();
            }
        });

        runAnimation.play();
        fadeOutAnimation.play();
    }

    public void removeFirst() {
        if (this.queueContainer.getChildren().isEmpty()) {
            return;
        }
        Node queueView = this.queueContainer.getChildren().get(0);
        this.decreaseNoOfElement();
        FadeTransition fadeOutAnimation = new FadeTransition(Duration.millis(GlobalConst.FADED_TIME));
        TranslateTransition runAnimation = new TranslateTransition(Duration.millis(GlobalConst.FADED_TIME));

        fadeOutAnimation.setNode(queueView);
        runAnimation.setNode(queueView);

        runAnimation.setByX(-(this.queueContainer.getWidth() + GlobalConst.QUEUE_WIDTH));  //dia diem moi can di chuyen toi

        fadeOutAnimation.setFromValue(1);
        fadeOutAnimation.setToValue(0);

        QueueController self = this;

        fadeOutAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                self.queueContainer.getChildren().remove(0);
                self.relocationQueueViews(0);
            }
        });
        runAnimation.play();
        fadeOutAnimation.play();
    }

    public void peekAnimation() {
        if (this.peekContainer.getChildren().size() > 0)
            this.peekContainer.getChildren().clear();

        if (this.queueContainer.getChildren().isEmpty()) {
            return;
        }

        QueueViewModel queueViewOriginal = (QueueViewModel) this.queueContainer.getChildren().get(0);
        peekedQueueView = new QueueViewModel();
        peekedQueueView.setText(queueViewOriginal.getText());
        this.peekContainer.getChildren().add(peekedQueueView);
        TranslateTransition runAnimation = new TranslateTransition(Duration.millis(GlobalConst.FADED_TIME));
        runAnimation.setNode(peekedQueueView);
        runAnimation.setByY(-200);
        QueueController self = this;

        runAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                self.enableBtn();
            }
        });
        runAnimation.play();
    }

    public void increaseNoOfElement() {
        this.currentNoOfElementTxt.setText(String.valueOf(this.queueContainer.getChildren().size() + 1));
    }

    public void decreaseNoOfElement() {
        this.currentNoOfElementTxt.setText(String.valueOf(this.queueContainer.getChildren().size() - 1));
    }

    public void showPopup(String message) {
        ButtonType confirmBtn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(confirmBtn);
        dialog.setTitle("Warning");
        dialog.setContentText(message);
        dialog.getDialogPane().lookupButton(confirmBtn).setDisable(false);
        dialog.show();

    }

    public void enqueue() {
        this.dsQueueViews = new LinkedList<QueueViewModel>();
        int maximumElement = (int) this.queueContainer.getWidth() / GlobalConst.QUEUE_WIDTH;
        if ((this.queueContainer.getChildren().size()) >= maximumElement) {
            this.showPopup("Maximum number of element is reach");
            return;
        }
        String enqueueValue = this.enqueueValue.getText();
        this.enqueueNumberValue = Integer.parseInt(enqueueValue.trim());
        QueueViewModel queueViewModel = new QueueViewModel();
        queueViewModel.setText(String.valueOf(this.enqueueNumberValue));
        this.dsQueueViews.add(queueViewModel);

        this.disableAll();
        addListAnimation(this.dsQueueViews, 2);
    }

    public void dequeue() {
        if (this.queueContainer.getChildren().isEmpty()) {
            this.showPopup("Queue is empty!!!");
            return;
        }

        this.disableAll();
        this.removeFirst();
    }

    public void peekInQueue(){
        if (this.queueContainer.getChildren().isEmpty()) {
            this.showPopup("Queue is empty!!!");
            return;
        }

        if (this.queueContainer.getChildren().isEmpty()) {
            this.showPopup("Queue is empty!!!");
            return;
        }
        this.disableAll();
        this.peekAnimation();
    }

    public void clearAll() {
        if (this.queueContainer.getChildren().isEmpty()) {
            this.showPopup("Queue is empty!!!");
            return;
        }
        this.disableAll();
        this.removeAll();
    }

    public void relocationQueueViews(int index) {

        if (this.queueContainer.getChildren().size() == index) {
            this.enableBtn();
            return;
        }

        QueueViewModel queueView = (QueueViewModel) this.queueContainer.getChildren().get(index);
        TranslateTransition runAnimation = new TranslateTransition(Duration.millis(GlobalConst.FADED_TIME));
        runAnimation.setNode(queueView);
        runAnimation.setToX(queueView.getTranslateX() - GlobalConst.QUEUE_WIDTH);  //dia diem moi can di chuyen toi
        QueueController self = this;
        runAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                self.disableAll();
                self.relocationQueueViews(index + 1);
            }
        });
        runAnimation.play();
    }

    public void enableStartBtn() {
        this.queue = new LinkedList<>();
        try {
            String text = this.inputData.getText().trim();
            String[] dsNumber = text.split("\\s+");
            for (String numString :
                    dsNumber) {
                int number = Integer.parseInt(numString.trim());
                this.queue.add(number);
            }
            if (this.queue.isEmpty()) {
                this.startBtn.setDisable(true);
                return;
            }
            this.startBtn.setDisable(false);
        } catch (Exception e) {
            this.startBtn.setDisable(true);
        }
    }

    public void enableEnqueueBtn() {
        try {
            String enqueueValue = this.enqueueValue.getText();
            this.enqueueNumberValue = Integer.parseInt(enqueueValue.trim());
            QueueViewModel queueViewModel = new QueueViewModel();
            queueViewModel.setText(String.valueOf(this.enqueueNumberValue));
            this.dsQueueViews.add(queueViewModel);
            this.enqueueBtn.setDisable(false);
        } catch (Exception e) {
            this.enqueueBtn.setDisable(true);
        }
    }

    public void enableRemoveBtn() {
        if (this.queueContainer.getChildren().size() > 0) {
            this.dequeueBtn.setDisable(false);
            this.clearAllBtn.setDisable(false);
            this.peekBtn.setDisable(false);
            return;
        }
        this.dequeueBtn.setDisable(true);
        this.clearAllBtn.setDisable(true);
        this.peekBtn.setDisable(true);
    }

    public void resetListData() {
        this.inputData.setText(null);
        this.validateMessage.setText(null);

    }

    public void resetSingleData() {
        this.enqueueValue.setText(null);
        this.alertEnqueueValue.setText(null);

    }

    public void enableAllForm() {
        this.inputData.setDisable(false);
        this.enqueueValue.setDisable(false);
    }

    public void enableBtn() {
        this.inputData.setDisable(false);
        this.enqueueValue.setDisable(false);
        this.enableRemoveBtn();
        this.enableStartBtn();
        this.enableEnqueueBtn();
    }

    public void disableAll() {

        this.inputData.setDisable(true);
        this.enqueueValue.setDisable(true);

        this.startBtn.setDisable(true);
        this.dequeueBtn.setDisable(true);
        this.clearAllBtn.setDisable(true);
        this.enqueueBtn.setDisable(true);
        this.peekBtn.setDisable(true);

    }
}
