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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import symbols.ArrowSymbol;
import symbols.Connectors;
import symbols.DecisionSymbol;
import symbols.ProcessSymbol;
import symbols.Symbols;
import symbols.TerminatorSymbol;
import symbols.TextSymbol;

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
	private Group currSymbol;

	public AppWindow(Stage stage) {
		root = new BorderPane();
		Scene scene = new Scene(root, 600, 800);

		selectedElements = new ArrayList<>();
		selectedConnectors = new ArrayList<>();

		elements = FXCollections.observableArrayList();

		stage.setScene(scene);
		stage.setMinHeight(800);
		stage.setMinWidth(600);
		stage.show();

		content = new ContentArea(this);
		controllpane = new ControllPane(this);
		menubar = new MainMenu(this,controllpane);

		/*
		 * VBox topBox = new VBox(); topBox.getChildren().addAll(menubar,
		 * controllpane);
		 */

		root.setTop(menubar);
		root.setCenter(content);
		root.setBottom(controllpane);

		content.addEventHandler(MouseEvent.ANY, (e) -> {
			if (symbolSelected) {
				if (e.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
					currSymbol.toFront();
					double x = currSymbol.getLayoutX() + e.getX();
					double y = currSymbol.getLayoutY() + e.getY();
					currSymbol.setTranslateX(x);
					currSymbol.setTranslateY(y);
				}

				if (e.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
					elements.add(currSymbol);
				}
				if (e.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
					elements.remove(currSymbol);
				}
			}
		});

		elements.addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
				content.getChildren().clear();
				content.getChildren().addAll(elements);
			}
		});

		scene.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.DELETE) {
				for (Symbols currElement : selectedElements) {
					for (Connectors currConn : currElement.getConnections()) {
						currConn.remove(currElement);
						elements.remove(currConn.getPathReference());
						elements.remove(currConn);
					}
					elements.remove(currElement);
				}
				clearSelected();
			}
		});

	}

	// LISTENERS

	// iELEMENTS methods

	@Override
	public void addElement(MouseEvent e) {
		if (symbolSelected) {
			Symbols currElement = null;
			double x = content.getTranslateX() + e.getX();
			double y = content.getTranslateY() + e.getY();
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
			case "text":
				currElement = new TextSymbol(this);
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
		for (Connectors currConn : currConnections) {
			currConn.remove(currElement);
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
				for (Connectors currConn : currConnections) {
					currConn.update();
				}
			}
			currElement.toFront();
		}
	}

	@Override
	public void setElementColor(String type) {
		if (selectedElements.size() > 0) {
			for (Symbols currElement : selectedElements) {
				switch (type) {
				case "fill":
					currElement.setFillColor(controllpane.getColor());
					break;
				case "stroke":
					currElement.setStrokeColor(controllpane.getColor());
					break;
				}
			}
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
		for (Symbols currElement : selectedElements) {
			currElement.setDeselected();
		}
		selectedElements.clear();
		for (Connectors currConn : selectedConnectors) {
			currConn.setDeselected();
		}
		selectedConnectors.clear();

	}

	@Override
	public void clearAll() {
		elements.clear();
	}

	@Override
	public List<Symbols> getSelectedElement() {
		return selectedElements;
	}

	// iSYMBOLS methods

	@Override
	public void selectSymbol(String type) {
		symbolType = type;
		currSymbol = controllpane.getSymbolForMouse(type);
		currSymbol.setOpacity(0.3);
		symbolSelected = true;
	}

	@Override
	public void deselectSymbol() {
		symbolType = null;
		symbolSelected = false;
		elements.remove(currSymbol);
		currSymbol = null;
	}

	// iCONNECTORS methods

	@Override
	public void addConnector() {
		/*
		 * Itterate through list and add for each start and end.
		 */

		int numberOfElements = selectedElements.size();
		int index = 0;

		if (numberOfElements > 1) {

			while (index < numberOfElements - 1) {
				Symbols start = selectedElements.get(index);
				Symbols end = selectedElements.get(index + 1);

				if (start.isConnectable() && end.isConnectable()) {
					ArrowSymbol connector = new ArrowSymbol(start, end);
					start.setConnected(connector);
					end.setConnected(connector);
					Path arrow = connector.getArrow();
					connector.setPathReference(arrow);
					elements.add(arrow);
				}
				index++;
			}
		} else {
			// NOT ENOUGH OBJECTS ERROR
		}

	}

	@Override
	public void redmoveConnector(Connectors currConn) {

		elements.remove(currConn);
	}

}
