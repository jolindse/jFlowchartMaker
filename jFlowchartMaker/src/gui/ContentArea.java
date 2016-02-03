package gui;

import interfaces.iElements;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import symbols.Symbols;

public class ContentArea extends AnchorPane {

	private iElements listener;
	private Text testtext;
	
	public ContentArea (iElements ev) {
		listener = ev;
		testtext = new Text("ContensArea");
		this.getChildren().add(testtext);
	}

	public void addElement(Symbols currSymbol) {
		this.getChildren().add(currSymbol);
		
	}
	
}
