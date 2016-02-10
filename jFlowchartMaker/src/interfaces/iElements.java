package interfaces;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import symbols.Symbols;

public interface iElements {
	
	void addElement(MouseEvent e);
	void removeElement(Symbols currSymbol);
	void moveElement(MouseEvent e);
	void setElementColor(String type);
}
