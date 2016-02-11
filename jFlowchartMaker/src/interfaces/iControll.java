package interfaces;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import symbols.Symbols;

public interface iControll {

	void setSymbolSelected(String type);
 	void removeSymbolSelected();

    void addSelectedElement(iObjects currElement);
	void removeSelectedElement(iObjects currElement);
	void clearSelectedElements();

    void clearAll();


    void moveElement(MouseEvent e);
    void setElementColor(String type);
    void addElement();
    void addConnector();
    void removeElement();
    void removeAll();
}
