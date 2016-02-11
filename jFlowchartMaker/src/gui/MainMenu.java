package gui;



import interfaces.iConnectors;
import interfaces.iElements;
import interfaces.iSelections;
import interfaces.iSymbols;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MainMenu extends MenuBar {

	private Menu file, symbols, transformation;
	private iSelections selListner;
	private iElements elListener;
	private iSymbols symListener;
	private iConnectors connListener;
	
	public MainMenu(AppWindow eh, ControllPane toolbar){
		connListener = eh;
		selListner = eh;
		elListener = eh;
		file = new Menu("File");
		
		MenuItem clear = new MenuItem("Clear");
		clear.setOnAction((e) ->{
			selListner.clearAll();
		});
		MenuItem save = new MenuItem("Save...");
		MenuItem load = new MenuItem("Load...");
		MenuItem export = new MenuItem("Export to PDF");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction((e) -> {
			Platform.exit();
		});
		file.getItems().addAll(clear,getSeparator(),save,load,getSeparator(),export,getSeparator(),exit);
		
		symbols = new Menu("Symbols");
		MenuItem process = new MenuItem("Process");
		process.setOnAction((e)-> {
			toolbar.setSelected("process");
		});
		MenuItem terminator = new MenuItem("Terminator");
		terminator.setOnAction((e) ->{
			toolbar.setSelected("terminator");
		});
		MenuItem decision = new MenuItem("Decision");
		decision.setOnAction((e) ->{
			toolbar.setSelected("decision");
		});
		MenuItem text = new MenuItem("Text");
		text.setOnAction((e) ->{
			toolbar.setSelected("decision");
		});
		MenuItem connector = new MenuItem("Connection");
		connector.setOnAction((e)->{
			connListener.addConnector();
		});
		symbols.getItems().addAll(process,terminator,decision,text,getSeparator(),connector);
		
		transformation = new Menu("Manipulation");
		MenuItem changeText = new MenuItem("Change text...");
		MenuItem changeFillColor = new MenuItem("Change fill color");
		changeFillColor.setOnAction((e) ->{
			elListener.setElementColor("fill");
		});
		MenuItem changeStrokeColor = new MenuItem("Change border color");
		changeStrokeColor.setOnAction((e) ->{
			elListener.setElementColor("stroke");
		});
		MenuItem deleteElement = new MenuItem("Remove symbol(s)");
		transformation.getItems().addAll(changeText,changeFillColor,changeStrokeColor,deleteElement);
		
		getMenus().addAll(file,symbols,transformation);
	}

	private MenuItem getSeparator(){
		MenuItem separator = new SeparatorMenuItem();
		return separator;
	}
}