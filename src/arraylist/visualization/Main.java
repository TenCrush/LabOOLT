package arraylist.visualization;
	
//import java.awt.event.ActionListener;
//import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;



public class Main extends Application {
//    @FXML
//    private Button addbtn = new Button();
//    @FXML
//    private Label Label = new Label();
    int count=0;
    
    public static void main(String[] args) {
        launch(args);
        }

    @Override
    public void start(Stage primaryStage) throws Exception {
//    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("ArrayList.fxml"));
//    	Parent root = ;
        primaryStage.setTitle("ArrayList");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    }



