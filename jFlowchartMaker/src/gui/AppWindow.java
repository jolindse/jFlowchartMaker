package gui;

import java.util.ArrayList;
import java.util.List;

import application.App;
import interfaces.iConnectors;
import interfaces.iElements;
import interfaces.iSelections;
import interfaces.iSymbols;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import symbols.ArrowSymbol;
import symbols.Connectors;
import symbols.DecisionSymbol;
import symbols.ProcessSymbol;
import symbols.Symbols;
import symbols.TerminatorSymbol;

public class AppWindow implements iElements, iSymbols, iConnectors, iSelections {

	private BorderPane root;
	private ContentArea content;
	private ControllPane controllpane;
	private MainMenu menubar;
		
	private List<Symbols> selectedElements;
	private List<Connectors> selectedConnectors;
	private ObservableList<Node> elements;
	private boolean symbolSelected;
	private String symbolType;

	public AppWindow(Stage stage, App controller) {
		root = new BorderPane();
		Scene scene = new Scene(root, 600, 800);

		selectedElements = new ArrayList<>();
		selectedConnectors = new ArrayList<>();
		
		elements = FXCollections.observableArrayList();
		
		
		stage.setScene(scene);
		stage.show();

		content = new ContentArea(this);
		controllpane = new ControllPane(this);
		menubar = new MainMenu();
		
		VBox topBox = new VBox();
		topBox.getChildren().addAll(menubar,controllpane);
		
		root.setTop(topBox);
		root.setCenter(content);
		
		elements.addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
				content.getChildren().clear();
				content.getChildren().addAll(elements);
				}
			});

	}
	
	// LISTENERS
	
	// iELEMENTS methods

	@Override
	public void addElement(MouseEvent e) {
		if (symbolSelected) {
			Symbols currElement = null;
			double x = content.getTranslateX()+e.getX();
			double y = content.getTranslateY()+e.getY();
			switch (symbolType) {
			case "process":
				currElement = new ProcessSymbol(this);
				break;
			case "decision":
				currElement = new DecisionSymbol(this);
				break;
			case "terminator":
				currElement = new TerminatorSymbol(this);
				break;
			}
			if (currElement != null) {
				currElement.setTranslateX(x);
				currElement.setTranslateY(y);
				elements.add(currElement);
			}
			controllpane.clearSelection();
		
		} else {
			clearSelected();	
			}
		}

	@Override
	public void removeElement(Symbols currElement) {
		List<Connectors> currConnections = currElement.getConnections();
		for(Connectors currConn: currConnections){
			currConn.remove();
		}
		elements.remove(currElement);
	}

	@Override
	public void moveElement(MouseEvent e) {
		if (e.isPrimaryButtonDown()) {
			Symbols currElement = (Symbols) e.getSource();
			double x = currElement.getTranslateX() + e.getX();
			double y = currElement.getTranslateY() + e.getY();

			currElement.setTranslateX(x);
			currElement.setTranslateY(y);
			if (currElement.isConnected()) {
				List<Connectors> currConnections = currElement.getConnections();
				for (Connectors currConn: currConnections){
					currConn.update();
				}
			}
			currElement.toFront();
		}
	}
	
	// iSELECTIONS methods

	@Override
	public void selectElement(Symbols currElement) {
		currElement.setSelected();
		selectedElements.add(currElement);
	}

	@Override
	public void selectConnector(Connectors currConnector) {
		currConnector.setSelected();
		selectedConnectors.add(currConnector);
	}

	@Override
	public void clearSelected() {
		for(Symbols currElement: selectedElements){
			System.out.println("Borde sätta av skugga!");
			currElement.setDeselected();
		}
		selectedElements.clear();
		for(Connectors currConn: selectedConnectors){
			currConn.setDeselected();
		}
		selectedConnectors.clear();
	}
	
	
	// iSYMBOLS methods
	
	@Override
	public void selectSymbol(String type) {
		symbolType = type;
		symbolSelected = true;
	}

	@Override
	public void deselectSymbol() {
		symbolType = null;
		symbolSelected = false;
	}

	// iCONNECTORS methods
	
	@Override
	public void addConnector() {
		/*
		 *  Itterate through list and add for each start and end.
		 */
		
		int numberOfElements = selectedElements.size();
		int index = 0;
		
		if (numberOfElements > 1) {
			
			while(index < numberOfElements-1){
				Symbols start = selectedElements.get(index);
				Symbols end = selectedElements.get(index+1);
				
				ArrowSymbol connector = new ArrowSymbol(start, end);
				start.setConnected(connector);
				end.setConnected(connector);
				Path arrow = connector.getArrow();
				elements.add(arrow);
				index++;
			}
		} else {
			// NOT ENOUGH OBJECTS ERROR
		}
		
	}

	@Override
	public void redmoveConnector(Connectors currConn) {
		currConn.remove();
		elements.remove(currConn);
	}

}
