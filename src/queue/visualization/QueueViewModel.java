package queue.visualization;

import global.visualization.GlobalConst;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class QueueViewModel extends StackPane {

    private Rectangle queueShape;

    public QueueViewModel() {

        this.queueShape = new Rectangle();
        this.queueShape.setWidth(GlobalConst.QUEUE_WIDTH);
        this.queueShape.setHeight(GlobalConst.QUEUE_HEIGHT);
        this.queueShape.setArcWidth(GlobalConst.QUEUE_RADIUS);
        this.queueShape.setArcHeight(GlobalConst.QUEUE_RADIUS);

        this.queueShape.setFill(Color.BLUEVIOLET.deriveColor(0, 1.2, 1, 0.6));
        this.queueShape.setStroke(Color.BLUEVIOLET);

    }

    public void setText(String text) {
        Text content = new Text(text);
        this.getChildren().addAll(this.queueShape, content);
    }

    public void setSize(int width, int height) {
        this.queueShape.setWidth(width);
        this.queueShape.setHeight(height);
    }

    public void setColor(String color) {
        Color customizeColor = Color.web(color);
        this.queueShape.setFill(customizeColor);
    }



}
