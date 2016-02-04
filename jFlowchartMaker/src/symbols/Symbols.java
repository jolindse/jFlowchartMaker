package symbols;

import java.util.Optional;

import interfaces.iElements;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class Symbols extends StackPane {
	
	protected iElements listener;
	protected boolean selected = false;
	protected boolean arrowed = false;
	
	protected Text symbolText;
	
	public Symbols (iElements listener) {
		this.listener = listener;
		addHandlers();
	}
	
	protected void addHandlers() {
	symbolText = new Text(getTextFromDialog());
	symbolText.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
		symbolText.setText(changeText(symbolText.getText()));
		updateSize();
	});
	
	this.addEventHandler(MouseEvent.ANY, (e) -> {
		if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			listener.moveElement(e);
		}
		
		if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
			listener.selectElement(e);
		}
		
		if (e.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
			setSelected();
		}
		if (e.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
			setDeselected();
		}
	});	}
	
	protected String getTextFromDialog(){
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
	
	protected String changeText(String currText) {
		TextInputDialog textInput = new TextInputDialog(currText);
		textInput.setTitle("Text");
		textInput.setHeaderText("Text on flowchart element");
		Optional<String> result = textInput.showAndWait();
		if (result.isPresent()){
			currText = result.get();
		}
		return currText;
	}
	

	/*
	 *  ANCHORPOINT METHODS FOR ARROWS
	 *  
	 *  returns the X,Y of the arrow anchor points.
	 *  
	 */
	
	protected double[] getTopAnchor(){
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0]-(pos[3]/2);
		anchors[1] = pos[1];
		return anchors;
	}
	
	public double[] getBottomAnchor(){
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0]-(pos[3]/2);
		anchors[1] = pos[1]+pos[2];
		return anchors;
	}
	
	public double[] getLeftAnchor(){
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0]-pos[3];
		anchors[1] = pos[1]+(pos[2]/2);
		return anchors;
	}
	
	public double[] getRightAnchor(){
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0]+pos[3];
		anchors[1] = pos[1]+(pos[2]/2);
		return anchors;
	}
	
	public double[] getPosition() {
		double[] values = new double[4];
		values[0] = this.getTranslateX();
		values[1] = this.getTranslateY();
		values[2] = this.getBoundsInLocal().getHeight();
		values[3] = this.getBoundsInLocal().getWidth();
		return values;
		
	}
	
	private void setSelected() {
		this.setEffect(new DropShadow(5, Color.BLACK));
	}
	
	public void setDeselected() {
		this.setEffect(null);
	}
	
	abstract void updateSize();
	
	
}
