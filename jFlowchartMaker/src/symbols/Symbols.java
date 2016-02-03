package symbols;

import java.util.Optional;

import interfaces.iElements;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;

public abstract class Symbols extends StackPane {

	private boolean selected = false;
	private boolean arrowed = false;
	
	public String getTextFromDialog(){
		String text = "";
		TextInputDialog textInput = new TextInputDialog();
		textInput.setTitle("Text");
		textInput.setHeaderText("Text on flowchart element");
		
		Optional<String> result = textInput.showAndWait();
		if (result.isPresent()){
			text = result.get();
		}
		return text;
	}
	
	// GETTERS & SETTERS
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isArrowed() {
		return arrowed;
	}
	public void setArrowed(boolean arrowed) {
		this.arrowed = arrowed;
	}
	


}
