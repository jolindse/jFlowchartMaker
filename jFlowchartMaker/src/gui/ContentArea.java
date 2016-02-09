package gui;

import interfaces.iElements;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ContentArea extends AnchorPane {

	private iElements listener;
	private Text testtext;
	
	public ContentArea (iElements ev) {
		listener = ev;
		
		
		this.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			listener.addElement(e);	
		});
		
		addEventHandler(MouseEvent.MOUSE_MOVED, (e) -> {
			
		});
	}

	public void addElement(ObservableList<Node> list) {
		this.getChildren().clear();
		this.getChildren().addAll(list);
		
	}
	
}
