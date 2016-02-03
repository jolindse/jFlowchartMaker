package gui;

import interfaces.iElements;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import symbols.Symbols;

public class ContentArea extends AnchorPane {

	private iElements listener;
	private Text testtext;
	
	public ContentArea (iElements ev) {
		listener = ev;
	}

	public void addElement(ObservableList<Symbols> list) {
		this.getChildren().clear();
		this.getChildren().addAll(list);
		
	}
	
}
