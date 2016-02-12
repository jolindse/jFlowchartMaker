package interfaces;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import symbols.Symbols;

/**
 * Interface that relays all selections and commands through "controller" to view.
 */
public interface iControll {

	void setSymbolSelected(String type);
 	void removeSymbolSelected();

    void addSelectedElement(iObjects currElement);
	void removeSelectedElement(iObjects currElement);
	void clearSelectedElements();

    void clearAll();


    void moveElement(MouseEvent e);
    void setCurrentColor(Color col);
    void setElementColor(String type);
    void addConnector();
    void removeElement();
    void removeAll();
}
