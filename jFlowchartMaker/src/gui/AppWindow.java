package gui;

import application.App;
import interfaces.iElements;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import symbols.ProcessSymbol;
import symbols.Symbols;

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
		switch(type) {
		case "process":
			ProcessSymbol currSymbol = new ProcessSymbol(); 
			controller.addElement(currSymbol);
			contentpane.addElement(currSymbol);
			System.out.println("Process added.");
			break;
		case "decision":
			System.out.println("Decision added.");
			break;
		case "terminator":
			System.out.println("Terminator added.");
			break;
		
		}
		
	}

	@Override
	public void removeElement(Symbols currSymbol) {
		// TODO Auto-generated method stub
		
	}


	
	
}


