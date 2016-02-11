package symbols;

import gui.AppWindow;
import interfaces.iObjects;
import javafx.scene.text.Font;

public class TextSymbol extends Symbols implements iObjects {

	public TextSymbol(AppWindow eh) {
		super(eh);
		connectable = false;
		
		symbolText.setFont(Font.font("Serif", 16));
		this.getChildren().add(symbolText);
	}

	@Override
	void updateSize() {
		// TODO Auto-generated method stub

	}

	@Override
	double calcHeight() {
		return 0;
	}

	@Override
	double calcWidth() {
		return 0;
	}

	@Override
	public String getSaveString() {
		String text = symbolText.getText().replaceAll("/[,\'\"]/","");
		return "Symbol,Text,"+id+","+x+","+y+","+width+","+height+","+text;
	}

}
