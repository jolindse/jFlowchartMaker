package interfaces;

import java.util.List;

import symbols.Connectors;
import symbols.Symbols;

public interface iSelections {
	public void selectElement(Symbols currSymbol);
	public void selectConnector(Connectors currConnector);
	public List<Symbols> getSelectedElement();
	public void clearSelected();
	public void clearAll();
}
