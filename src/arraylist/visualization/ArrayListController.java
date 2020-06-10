package arraylist.visualization;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.visualization.MainController;

//import java.util.ArrayList;

public class ArrayListController {
    @FXML
    private TextField input = new TextField();

    @FXML
    private TextField input1 = new TextField();
    
    @FXML
    private TextField input2 = new TextField();
    
    @FXML
    private TextField input3 = new TextField();
    
    @FXML
    private TextField input4 = new TextField();
    
    @FXML
    private TextField input5 = new TextField();
    
    @FXML
    private Button createbtn;
    
    @FXML
    private Button clearbtn;
    
    @FXML
    private Label Label;
    
    @FXML
    private Label Label1;
    
    @FXML
    private Button addbtn;
    
//-------------------------------
    
//	private int count = 0;
	private int last = 0;
	private int size = 10;
//	private int nElements = 0;
	private int firstX = 250;
	private int firstY = 300;
//	private int radius = 20;
	@FXML
	AnchorPane mainPane = new AnchorPane();
	
	public ArrayListModel model = new ArrayListModel();
	public ArrayListModel list = new ArrayListModel();

    public void show() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ArrayList.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("ArrayList visualization");
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
	public ArrayListController() {
		// TODO Auto-generated constructor stub
	}
	public void initialize() {
//		init();
//		firstDraw();
	}
	@FXML
	public void create(ActionEvent event) {	
		mainPane.getChildren().removeIf(child->child instanceof Rectangle); 
//		for (int i=0;i<=last;i++) {
//			Text t = (Text) mainPane.lookup("#t"+i);
//			mainPane.getChildren().remove(t);
//		}
		last=0;
		size=Integer.parseInt(input.getText());
		init();
		List();
		model.print();
	}
	public void init() {
		model.setSize(size);
	}
	
	public void List() {
		int index = 0;
//		int space = 0;
//		int space2 = 0;
		for (int i=0;i<size;i++) {
			Rectangle rec = new Rectangle(firstX + index*50, firstY,50,50);
//			int x = firstX + index*(2*radius) + space;
			rec.setFill(null);
			rec.setStroke(Color.BLACK);
			rec.setId("rec"+index);
			mainPane.getChildren().add(rec);
			index++;
			Text t = new Text(firstX + index*50 - 30, firstY + 70,Integer.toString(i));
			t.setFill(Color.ROYALBLUE);
			t.setFont(Font.font(15));
			t.setId("num"+i);
			mainPane.getChildren().add(t);
//			space=space+10;
//			if (x > 750) {
//				space2++;
//				index=0;
//				space=0;
			}
		}
//	}
	
	public void clear(ActionEvent event) {
		mainPane.getChildren().removeIf(child->child instanceof Circle);
		mainPane.getChildren().removeIf(child->child instanceof Rectangle);
		for (int i=0;i<=last;i++) {
			Text t = (Text) mainPane.lookup("#t"+i);
			mainPane.getChildren().remove(t);
		}
		for (int i=0;i<=size;i++) {
			Text t2 = (Text) mainPane.lookup("#num"+i);
			mainPane.getChildren().remove(t2);
		}
		model.clear();
		last = 0;
		
	}
	public void add(ActionEvent event) {
		if (!model.checkFull()) {
			int tempnew = model.getLast()+1;
			model.append(tempnew);
			Rectangle rec = (Rectangle) mainPane.lookup("#rec"+last);

			Rectangle newRec = new Rectangle(rec.getX()+10, rec.getY()+10 ,30, 30);
			newRec.setFill(Color.BLACK);
			newRec.setStroke(Color.BLACK);
			newRec.setId("e"+(last));
			mainPane.getChildren().add(newRec);
			Text t = new Text(rec.getX()+20,rec.getY()+33,Integer.toString(model.getLast()));
			t.setFill(Color.WHITE);
			t.setFont(Font.font(20));
			t.setId("t"+last);
			mainPane.getChildren().add(t);
			last++;
		}
	}
	public void remove(ActionEvent event) {
		int del=Integer.parseInt(input1.getText());
			del++;
		if (del<=model.getSize()) {
			model.remove(del-1);
			Text t = (Text) mainPane.lookup("#t"+(del-1));
			mainPane.getChildren().remove(t);
			Rectangle rec = (Rectangle) mainPane.lookup("#e"+(del-1));
			mainPane.getChildren().remove(rec);
			for (int i=del;i<last;i++) { 
				Text t2 = (Text) mainPane.lookup("#t"+i);
				t2.setX(t2.getX()-50);
				t2.setY(t2.getY());
				t2.setId("t"+(i-1));
				Rectangle rec2 = (Rectangle) mainPane.lookup("#e"+i);
				rec2.setX(rec2.getX()-50);
				rec2.setY(rec2.getY());
				rec2.setId("e"+(i-1));
			}
			model.print();
			last--;
		}
		else {
//			System.out.println("no remove");
			return;
		}

	}
	public void Insert(ActionEvent event) {
		Label1.setText(null);
		int ins=Integer.parseInt(input2.getText());
		int pos=Integer.parseInt(input3.getText());
		System.out.println(last);
		System.out.println(pos);
		if (pos>last) { 
			Label1.setText("Error");
		}
		
		else if (!model.checkFull())	{
			for (int i=(model.getSize()-1);i>pos-1;i--) { 
				Text t2 = (Text) mainPane.lookup("#t"+i);
				t2.setX(t2.getX()+50);
				t2.setY(t2.getY());
				t2.setId("t"+(i+1));
				Rectangle rec2 = (Rectangle) mainPane.lookup("#e"+i);
				rec2.setX(rec2.getX()+50);
				rec2.setY(rec2.getY());
				rec2.setId("e"+(i+1));
			}
			model.insert(ins,pos);
			Rectangle rec = (Rectangle) mainPane.lookup("#rec"+pos);
			Rectangle newRec = new Rectangle(rec.getX()+10, rec.getY()+10 ,30, 30);
			newRec.setFill(Color.BLACK);
			newRec.setStroke(Color.BLACK);
			newRec.setId("e"+(pos));
			mainPane.getChildren().add(newRec);
			Text t = new Text(rec.getX()+20,rec.getY()+33,Integer.toString(ins));
			t.setFill(Color.WHITE);
			t.setFont(Font.font(20));
			t.setId("t"+pos);
			mainPane.getChildren().add(t);
			last++;
			model.print();
		}
	}
	public void replace(ActionEvent event) {
		int rep=Integer.parseInt(input4.getText());
		int pos=Integer.parseInt(input5.getText());
		if(pos<=model.getSize()) {
			model.replace(pos, rep);
			Text t = (Text) mainPane.lookup("#t"+pos);
			t.setText(Integer.toString(rep));
		}
		model.print();
	}
	
	
}
