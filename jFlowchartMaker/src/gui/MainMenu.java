package gui;

import interfaces.iControll;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
	Menu class
 */

public class MainMenu extends MenuBar {

	private Menu file, symbols, transformation;

	private iControll controll;

	/**
	 * Adds all items to menus.
	 * @param eh
     */
	public MainMenu(iControll eh){

		controll = eh;
		file = new Menu("File");
		
		MenuItem clear = new MenuItem("Clear");
		clear.setOnAction((e) ->{
			controll.removeAll();
		});
		MenuItem save = new MenuItem("Save...");
		save.setDisable(true);
		MenuItem load = new MenuItem("Load...");
		load.setDisable(true);
		MenuItem export = new MenuItem("Export to PDF");
		export.setDisable(true);
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction((e) -> {
			Platform.exit();
		});
		file.getItems().addAll(clear,getSeparator(),save,load,getSeparator(),export,getSeparator(),exit);
		
		symbols = new Menu("Symbols");
		MenuItem process = new MenuItem("Process");
		process.setOnAction((e)-> {
			controll.setSymbolSelected("process");
		});
		MenuItem terminator = new MenuItem("Terminator");
		terminator.setOnAction((e) ->{
			controll.setSymbolSelected("terminator");
		});
		MenuItem decision = new MenuItem("Decision");
		decision.setOnAction((e) ->{
			controll.setSymbolSelected("decision");
		});
		MenuItem text = new MenuItem("Text");
		text.setOnAction((e) ->{
			controll.setSymbolSelected("decision");
		});
		MenuItem connector = new MenuItem("Connection");
		connector.setOnAction((e)->{
			controll.addConnector();
		});
		symbols.getItems().addAll(process,terminator,decision,text,getSeparator(),connector);
		
		transformation = new Menu("Manipulation");
		MenuItem changeText = new MenuItem("Change text...");
		MenuItem changeFillColor = new MenuItem("Change fill color");
		changeFillColor.setOnAction((e) ->{
			controll.setElementColor("fill");
		});
		MenuItem changeStrokeColor = new MenuItem("Change border color");
		changeStrokeColor.setOnAction((e) ->{
			controll.setElementColor("stroke");
		});
		MenuItem deleteElement = new MenuItem("Remove symbol(s)");
		transformation.getItems().addAll(changeText,changeFillColor,changeStrokeColor,deleteElement);
		
		getMenus().addAll(file,symbols,transformation);
	}

	/**
	 * Returns an anonymous separator for use in menus.
	 * @return
     */
	private MenuItem getSeparator(){
		MenuItem separator = new SeparatorMenuItem();
		return separator;
	}
}