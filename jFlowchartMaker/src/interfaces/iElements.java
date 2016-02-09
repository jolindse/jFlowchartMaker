package interfaces;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import symbols.Symbols;

public interface iElements {
	
	public void addElement(MouseEvent e);
	public void removeElement(Symbols currSymbol);
	public void moveElement(MouseEvent e);
	public void setElementColor(String type);
}
