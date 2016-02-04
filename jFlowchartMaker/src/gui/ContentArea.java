package gui;

import interfaces.iElements;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import symbols.Symbols;

public class ContentArea extends AnchorPane {

	private iElements listener;
	private Text testtext;
	
	public ContentArea (iElements ev) {
		listener = ev;
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> listener.addElement(e));
	}

	public void addElement(ObservableList<Symbols> list) {
		this.getChildren().clear();
		this.getChildren().addAll(list);
		
	}
	
}
