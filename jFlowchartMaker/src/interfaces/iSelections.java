package interfaces;

import symbols.Connectors;
import symbols.Symbols;

public interface iSelections {
	public void selectElement(Symbols currSymbol);
	public void selectConnector(Connectors currConnector);
	public void clearSelected();
}
