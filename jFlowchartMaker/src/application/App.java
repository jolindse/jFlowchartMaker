package application;

import gui.AppWindow;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import symbols.Symbols;

public class App extends Application {

	private AppWindow view;
	private ObservableList<Symbols> elementsAdded;
	private boolean selected = false;
	private Symbols[] elementsSelected;

	@Override
	public void start(Stage primaryStage) throws Exception {

		elementsAdded = FXCollections.observableArrayList();
		elementsSelected = new Symbols[2];

		view = new AppWindow(primaryStage, this);

	}

	public static void main(String[] args) {
		launch(args);
	}

	// METHODS FOR ADDED ELEMENTS

	public void addElement(Symbols currSymbol) {
		elementsAdded.add(currSymbol);
	}

	public void removeElement(Symbols currSymbol) {
		elementsAdded.remove(currSymbol);
	}
	
	public ObservableList<Symbols> getElements() {
		return elementsAdded;
	}

	// METHODS FOR CHOSEN ELEMENTS

	public void clearSelection() {
		elementsSelected = new Symbols[2];
		selected = false;
	}

	public void setSelected(Symbols startSymbol, Symbols endSymbol) {
		elementsSelected[0] = startSymbol;
		elementsSelected[1] = endSymbol;
		selected = true;
	}

	public Symbols[] getSelected() {
		return elementsSelected;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	// METHODS TO MANIPULATE VIEW
	

}
