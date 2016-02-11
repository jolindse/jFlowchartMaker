package interfaces;

import symbols.Connectors;
import symbols.Symbols;

import java.util.List;

public interface iSelections {
	void selectElement(Symbols currSymbol);
	void selectConnector(Connectors currConnector);
	List<Symbols> getSelectedElement();
	void clearSelected();
	void clearAll();
}
