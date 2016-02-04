package gui;

import application.App;
import interfaces.iElements;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import symbols.DecisionSymbol;
import symbols.ProcessSymbol;
import symbols.Symbols;
import symbols.TerminatorSymbol;

public class AppWindow implements iElements {
	private BorderPane root;
	private ContentArea contentpane;
	private ControllPane controllpane;
	private App controller;
	private boolean symbolSelected;
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

	@Override
	public void addElement(MouseEvent e) {
		System.out.println("addElement symbolSelected: "+symbolSelected+" symbolType: "+symbolType);
		if (symbolSelected) {
			Symbols currSymbol = null;
			switch (symbolType) {
			case "process":
				currSymbol = new ProcessSymbol(this, e.getX(), e.getY());
				break;
			case "decision":
				currSymbol = new DecisionSymbol(this, e.getX(), e.getY());
				break;
			case "terminator":
				currSymbol = new TerminatorSymbol(this, e.getX(), e.getY());
				break;
			}
			if (currSymbol != null) {
				controller.addElement(currSymbol);
			}
			contentpane.addElement(controller.getElements());
		}
	}

	@Override
	public void removeElement(Symbols currSymbol) {
		// TODO Auto-generated method stub

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

	@Override
	public void selectElement(MouseEvent e) {
		Symbols currSymbol = (Symbols) e.getSource();
		controller.setSelected(currSymbol);
	}

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

}
