package gui;



import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainMenu extends MenuBar {

	private Menu file, symbols, transformation;
	
	public MainMenu(){
		file = new Menu("File");
		MenuItem clear = new MenuItem("Clear");
		MenuItem save = new MenuItem("Save...");
		MenuItem load = new MenuItem("Load...");
		MenuItem export = new MenuItem("Export to PDF");
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(clear,save,load,export,exit);
		
		symbols = new Menu("Symbols");
		MenuItem process = new MenuItem("Process");
		MenuItem terminator = new MenuItem("Terminator");
		MenuItem decision = new MenuItem("Decision");
		MenuItem connector = new MenuItem("Connection");
		symbols.getItems().addAll(process,terminator,decision,connector);
		
		transformation = new Menu("Manipulation");
		MenuItem changeText = new MenuItem("Change text...");
		MenuItem changeFillColor = new MenuItem("Change fill color");
		MenuItem changeStrokeColor = new MenuItem("Change border color");
		MenuItem deleteElement = new MenuItem("Remove symbol(s)");
		transformation.getItems().addAll(changeText,changeFillColor,changeStrokeColor,deleteElement);
		
		getMenus().addAll(file,symbols,transformation);
	}
}