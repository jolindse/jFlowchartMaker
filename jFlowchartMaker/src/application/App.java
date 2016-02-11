package application;

import gui.AppWindow;
import interfaces.iControll;
import interfaces.iObjects;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import symbols.Connectors;
import symbols.Symbols;

public class App extends Application implements iControll {

	private AppWindow view;
    private StringProperty symbolSelectedType;

    private boolean selected;
    private BooleanProperty symbolSelected;
    private ObservableList<Symbols> elementsSelected;
    private ObservableList<Connectors> connectorsSelected;

	@Override
	public void start(Stage primaryStage) throws Exception {

        controllerInit();
		view = new AppWindow(primaryStage, this);

	}

	public static void main(String[] args) {
		launch(args);
	}


	private void controllerInit() {

        symbolSelectedType = new SimpleStringProperty("");
        //selected = new SimpleBooleanProperty(false);
        symbolSelected = new SimpleBooleanProperty(false);

        elementsSelected = FXCollections.observableArrayList();
        connectorsSelected = FXCollections.observableArrayList();

        // Selection property listeners

        symbolSelectedType.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                view.setSymbolType(symbolSelectedType.get());
            }
        });

        elementsSelected.addListener(new ListChangeListener<Symbols>() {
            @Override
            public void onChanged(Change<? extends Symbols> c) {
                checkSelected();
                view.setSelectedElements(elementsSelected);
            }
            });

        connectorsSelected.addListener(new ListChangeListener<Connectors>() {
            @Override
            public void onChanged(Change<? extends Connectors> c) {
                checkSelected();
                view.setSelectedConnectors(connectorsSelected);
            }
        });

        symbolSelected.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                view.setSymbolSelected(symbolSelected.get());
            }
        });

    }


    private void checkSelected() {
        if (elementsSelected.size() > 0) {
            selected = true;
            } else if (connectorsSelected.size() > 0){
            selected = true;
            } else {
            selected = false;
        }
    }

    // ELEMENTS SELECTION METHODS

    @Override
    public void clearAll() {
        elementsSelected.clear();
        connectorsSelected.clear();
        symbolSelected.set(false);
        symbolSelectedType.set(null);
    }

    @Override
    public void setSymbolSelected(String type) {
        symbolSelectedType.set(type);
        symbolSelected.set(true);
        view.getCurrentSymbol();
    }

    @Override
    public void removeSymbolSelected() {
        view.setCurrentSymbol(null);
        symbolSelected.set(false);
        symbolSelectedType = null;
    }

    @Override
    public void addSelectedElement(iObjects currElement) {
        if (currElement instanceof Symbols) {
            System.out.println("Should add symbol to selected list!"); // TEST
            elementsSelected.add((Symbols) currElement);
        } else if (currElement instanceof Connectors) {
            connectorsSelected.add((Connectors) currElement);
        } else {
            System.out.println("ADD SELECTED: Cant get instance of currElement: " + currElement); // TEST
        }
    }

    @Override
    public void removeSelectedElement(iObjects currElement) {
        if (currElement instanceof Symbols) {
            elementsSelected.remove(currElement);
        } else if (currElement instanceof Connectors) {
            connectorsSelected.remove(currElement);
        } else {
            System.out.println("REMOVE SELECTED. Cant get instance of currElement: " + currElement); // TEST
        }
    }

    @Override
    public void clearSelectedElements() {
        elementsSelected.clear();
    }

    // ELEMENT MANIPULATION METHODS

    @Override
    public void moveElement(MouseEvent e) {
            view.moveElement(e);

    }

    @Override
    public void setElementColor(String type) {
        if(selected){
            view.setElementColor(type);
        }
    }

    @Override
    public void addElement() {
        // w8
    }

    @Override
    public void addConnector() {
        if(selected) {
            view.addConnector();
        }
    }

    @Override
    public void removeElement() {
        for (Symbols currElement: elementsSelected){
            view.removeElement((iObjects) currElement);
        }
        clearAll();
    }

    @Override
    public void removeAll() {
        clearAll();
        view.removeAll();
    }


}


