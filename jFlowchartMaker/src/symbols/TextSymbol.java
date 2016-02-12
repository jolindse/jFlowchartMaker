package symbols;

import gui.AppWindow;
import interfaces.iControll;
import interfaces.iObjects;
import javafx.scene.text.Font;

/**
 *  Text symbol. Can not be connected to other elements with connector.
 */
public class TextSymbol extends Symbols implements iObjects {

	public TextSymbol(iControll eh) {
		super(eh);
		connectable = false;
		
		symbolText.setFont(Font.font("Serif", 16));
		this.getChildren().add(symbolText);
	}

	@Override
	void updateSize() {
		// NO NEED. STUB METHOD
	}

	@Override
	double calcHeight() {
		// NO NEED. STUB METHOD
		return 0;
	}

	@Override
	double calcWidth() {
		// NO NEED. STUB METHOD
		return 0;
	}

	@Override
	public String getSaveString() {
		String text = symbolText.getText().replaceAll("/[,\'\"]/","");
		return "Symbol,Text,"+id+","+x+","+y+","+width+","+height+","+text;
	}

}
