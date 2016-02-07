package application;

import java.util.ArrayList;
import java.util.List;

import gui.AppWindow;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import symbols.Connectors;
import symbols.Symbols;

public class App extends Application {

	private AppWindow view;
	
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		view = new AppWindow(primaryStage, this);

	}
	

}
