package symbols;

import gui.AppWindow;
import interfaces.iElements;
import interfaces.iSelections;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Symbols extends StackPane {

	protected iSelections listener;
	protected iElements elListener;
	protected boolean selected = false;
	protected boolean connected = false;
	protected boolean connectable = true;
	protected List<Connectors> connections;

	protected Effect ELEMENT_SHADOW = new DropShadow(5, Color.BLACK);

	protected ObjectProperty<Color> FILL_COLOR, STROKE_COLOR, TEXT_COLOR;
	protected SimpleDoubleProperty STROKE_WIDTH;

	protected Text symbolText;
	protected int id;
	protected double width, height, x, y;
	private static int idCounter = 0;
	
	public Symbols(AppWindow eh) {
		listener = eh;
		elListener = eh;

		setPadding(new Insets(0, 0, 0, 0));

		FILL_COLOR = new SimpleObjectProperty<>(Color.WHITE);
		STROKE_COLOR = new SimpleObjectProperty<>(Color.BLACK);
		TEXT_COLOR = new SimpleObjectProperty<>(Color.BLACK);
		STROKE_WIDTH = new SimpleDoubleProperty(2);

		connections = new ArrayList<>();
		symbolText = new Text(getTextFromDialog());
		symbolText.fillProperty().bind(TEXT_COLOR);
		symbolText.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			if (e.getClickCount() == 2) {
				symbolText.setText(changeText(symbolText.getText()));
				updateSize();
			}
		});

		this.addEventHandler(MouseEvent.ANY, (e) -> {
			if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				elListener.moveElement(e);
			}

			if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
				Symbols currSymbol = (Symbols) e.getSource();
				if (e.isShiftDown()) {
					listener.selectElement(currSymbol);
					setSelected();
					e.consume();
					// LÄGG IN DUBBELKLICK -> ÄNDRA TEXT
				} else {
					listener.selectElement(currSymbol);
					setSelected();
					e.consume();
				}
			}

			if (e.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
				setEffect(ELEMENT_SHADOW);
			}

			if (e.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
				if (!selected) {
					setEffect(null);
					setDeselected();
				}
			}
			id = idCounter;
			idCounter++;
		});
	}

	protected String getTextFromDialog() {
		String text = "";
		TextInputDialog textInput = new TextInputDialog();
		textInput.setTitle("Text");
		textInput.setHeaderText("Text on flowchart element");

		Optional<String> result = textInput.showAndWait();
		if (result.isPresent()) {
			text = result.get();
		}
		return text;
	}

	protected String changeText(String currText) {
		TextInputDialog textInput = new TextInputDialog(currText);
		textInput.setTitle("Text");
		textInput.setHeaderText("Text on flowchart element");
		Optional<String> result = textInput.showAndWait();
		if (result.isPresent()) {
			currText = result.get();
		}
		return currText;
	}

	// METHODS TO HANDLE CONNECTION

	public void setConnected(Connectors connection) {
		if (connectable) {
			connections.add(connection);
			connected = true;
		}
	}

	public void removeConnected(Connectors currConnection) {
		if (connectable) {
			connections.remove(currConnection);
			if (connections.isEmpty()) {
				connected = false;
			}
		}
	}

	public boolean isConnected() {
		return connected;
	}
	
	public boolean isConnectable() {
		return connectable;
	}

	public List<Connectors> getConnections() {
		return connections;
	}

	// SELECTION METHODS

	public void setSelected() {
		selected = true;
		setEffect(new DropShadow(20, Color.DARKBLUE));
	}

	public void setDeselected() {
		selected = false;
		setEffect(null);
	}

	// COLOR METHODS

	public void setFillColor(Color currColor) {
		FILL_COLOR.set(currColor);
	}

	public void setStrokeColor(Color currColor) {
		STROKE_COLOR.set(currColor);
	}

	// METHODS FOR SAVING
	
	
	private void getPosition() {
		
	x = getBoundsInParent().getMinX();
	y = getBoundsInParent().getMinY();

	height = calcHeight();
	width = calcWidth();
	}
	
    public int getSymbolId(){
        return id;
    }

	// IMPLEMENTED

	abstract void updateSize();
	abstract double calcHeight();
	abstract double calcWidth();
	// abstract String getSaveString();
}
