package gui;

import application.App;
import interfaces.iElements;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import symbols.ArrowSymbol;
import symbols.DecisionSymbol;
import symbols.ProcessSymbol;
import symbols.Symbols;
import symbols.TerminatorSymbol;

public class AppWindow implements iElements {
	private BorderPane root;
	private ContentArea contentpane;
	private ControllPane controllpane;
	private App controller;
	private Symbols selectedSymbol;
	private boolean symbolSelected, elementSelected;
	private String symbolType;

	public AppWindow(Stage stage, App controller) {
		this.controller = controller;
		root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);

		stage.setScene(scene);
		stage.show();

		contentpane = new ContentArea(this);
		controllpane = new ControllPane(this);

		root.setTop(controllpane);
		root.setCenter(contentpane);

	}

	// LISTENERS
	
	// ELEMENTS MANIPULATION

	@Override
	public void addElement(MouseEvent e) {
		System.out.println("addElement symbolSelected: "+symbolSelected+" symbolType: "+symbolType); // TEST
		if (symbolSelected) {
			Symbols currSymbol = null;
			double x = contentpane.getTranslateX()+e.getX();
			double y = contentpane.getTranslateY()+e.getY();
			System.out.println("X AND Y: "+x+" "+y); // TEST
			switch (symbolType) {
			case "process":
				currSymbol = new ProcessSymbol(this);
				break;
			case "decision":
				currSymbol = new DecisionSymbol(this);
				break;
			case "terminator":
				currSymbol = new TerminatorSymbol(this);
				break;
			}
			if (currSymbol != null) {
				currSymbol.setTranslateX(x);
				currSymbol.setTranslateY(y);
				controller.addElement(currSymbol);
				
			}
			controllpane.clearSelection();
			contentpane.addElement(controller.getElements());
		}
	}

	@Override
	public void removeElement(Symbols currSymbol) {
		

	}

	@Override
	public void moveElement(MouseEvent e) {
		if (e.isPrimaryButtonDown()) {
			Symbols currSymbol = (Symbols) e.getSource();
			double x = currSymbol.getTranslateX() + e.getX();
			double y = currSymbol.getTranslateY() + e.getY();

			currSymbol.setTranslateX(x);
			currSymbol.setTranslateY(y);
		}
	}
	
	// ELEMENTS SELECTION MANIPULATION

	@Override
	public void selectElement(Symbols currSymbol) {
		elementSelected = true;
		selectedSymbol = currSymbol;
	}

	@Override
	public void addElementToSelections(Symbols currSymbol) {
		if (elementSelected) {
			controller.setSelected(selectedSymbol, currSymbol);
			System.out.println("Elements added to selection: "+selectedSymbol+" "+currSymbol);
		}
		
	}

	@Override
	public void clearElementsSelected() {
		elementSelected = false;
		selectedSymbol = null;
	}
	
	
	// SYMBOLS MANIPULATION
	
	@Override
	public void selectSymbol(String type) {
		System.out.println("Symbol selected: "+type); // TEST
		symbolType = type;
		symbolSelected = true;

	}

	@Override
	public void deselectSymbol() {
		System.out.println("Symbols deselected");
		symbolType = null;
		symbolSelected = false;

	}

	@Override
	public void addArrow() {
		
		if (controller.isSelected()){
		System.out.println("Add an arrow"); // TEST
		Symbols[] elementsToConnect = controller.getSelected();
		Symbols start = elementsToConnect[0];
		Symbols end = elementsToConnect[1];
		double[] anchors = start.getArrowAnchors(end);
		
		Symbols arrow = new ArrowSymbol(this,anchors[0],anchors[1],anchors[2],anchors[3]);
		
		arrow.setTranslateX(anchors[0]);
		arrow.setTranslateY(anchors[1]);
		
		
		controller.addElement(arrow);
		contentpane.addElement(controller.getElements());
		
		} else {
			System.out.println("Not enought objects selected!");
		}
	}


}
