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
	public void addElement(String type) {
		Symbols currSymbol = null;
		switch(type) {
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
		if (currSymbol != null){
		controller.addElement(currSymbol);
		}
		contentpane.addElement(controller.getElements());
	}

	@Override
	public void removeElement(Symbols currSymbol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveElement(MouseEvent e) {
		if(e.isPrimaryButtonDown()){
		Symbols currSymbol = (Symbols)e.getSource();
		double x = e.getSceneX()+currSymbol.getTranslateX()-(currSymbol.getWidth()/2);
		double y = (e.getSceneY()+currSymbol.getTranslateY()-(controllpane.getHeight()+(currSymbol.getHeight()/2)));
		currSymbol.setLayoutX(x);
		currSymbol.setLayoutY(y);
		}
	}

	@Override
	public void selectElement(MouseEvent e) {
		Symbols currSymbol = (Symbols)e.getSource();
		controller.setSelected(currSymbol);
		
	}


	
	
}


