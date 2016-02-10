package interfaces;

import java.util.List;

import symbols.Connectors;
import symbols.Symbols;

public interface iSelections {
	void selectElement(Symbols currSymbol);
	void selectConnector(Connectors currConnector);
	List<Symbols> getSelectedElement();
	void clearSelected();
	void clearAll();
}
