package interfaces;

import javafx.scene.input.MouseEvent;
import symbols.Symbols;

public interface iElements {

	public void addElement(String type);
	public void removeElement(Symbols currSymbol);
	public void moveElement(MouseEvent e);
	
}
