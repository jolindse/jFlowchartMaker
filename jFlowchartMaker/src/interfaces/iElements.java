package interfaces;

import javafx.scene.input.MouseEvent;
import symbols.Symbols;

public interface iElements {

	public void selectSymbol(String type);
	public void deselectSymbol();
	public void addElement(MouseEvent e);
	public void removeElement(Symbols currSymbol);
	public void moveElement(MouseEvent e);
	
	public void selectElement(MouseEvent e);
	
}
