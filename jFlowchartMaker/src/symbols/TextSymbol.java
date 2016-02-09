package symbols;

import gui.AppWindow;
import javafx.scene.text.Font;

public class TextSymbol extends Symbols {

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

}
