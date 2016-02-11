package data;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import symbols.Connectors;
import symbols.Symbols;

public class Selections {

    private String symbolSelectedType;
    private boolean selectedSymbol, selectedElement, selectedConnectors;

    private BooleanProperty selected;
    private ObservableList<Symbols> elementsSelected;
    private ObservableList<Connectors> connectorsSelected;

    public Selections(){
        symbolSelectedType = null;
        selectedSymbol = false;
        selectedElement = false;
        selectedConnectors = false;
        selected = new SimpleBooleanProperty(false);

        elementsSelected = FXCollections.observableArrayList();
        connectorsSelected = FXCollections.observableArrayList();

        elementsSelected.addListener(new ListChangeListener<Symbols>() {
            @Override
            public void onChanged(Change<? extends Symbols> c) {
                if (elementsSelected.size()>0) {
                    selectedElement = true;
                } else {
                    selectedElement = false;
                }
            }
        });

        connectorsSelected.addListener(new ListChangeListener<Connectors>() {
            @Override
            public void onChanged(Change<? extends Connectors> c) {
                if(connectorsSelected.size()>0) {
                    selectedElement = true;
                } else {
                    selectedElement = false;
                }
            }
        });

    }

    private boolean checkSelected() {
        return true;
    }

    // SYMBOL SELECTIONS METHODS

    public void clearSelectedSymbols(){
        symbolSelectedType = null;
    }

    public void setSelectedSymbol(String type){
        symbolSelectedType = type;
    }

    public boolean isSymbolsSelected(){
        if(symbolSelectedType.equals(null)){
            return false;
        } else {
            return true;
        }
    }

    // ELEMENTS SELECTION METHODS

    public void clearSelectedElements(){
        elementsSelected.clear();
    }

    public void addSelectedElements(Symbols currElement){
        elementsSelected.add(currElement);
    }

    public void removeSelectedElement(Symbols currElement){
        elementsSelected.remove(currElement);
    }

}
