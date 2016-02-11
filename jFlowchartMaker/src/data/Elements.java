package data;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import symbols.Connectors;
import symbols.Symbols;

public class Elements {

    private ObservableList<Symbols> elements;
    private ObservableList<Connectors> connections;


    public Elements() {
        elements = FXCollections.observableArrayList();
        connections = FXCollections.observableArrayList();

    }

    public void addElement(Symbols currElement){
        elements.add(currElement);
    }
}
