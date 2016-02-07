package symbols;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gui.AppWindow;
import gui.GuiConstans;
import interfaces.iElements;
import interfaces.iSelections;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class Symbols extends StackPane {

	protected iSelections listener;
	protected iElements elListener;
	protected boolean selected = false;
	protected boolean connected = false;
	protected List<Connectors> connections;
	protected double height, width, x, y;

	//protected double STROKE_WIDTH = 2;
	//protected Color FILL_COLOR = Color.WHITE;
	//protected Color STROKE_COLOR = Color.BLACK;
	//protected Color SELECTION_COLOR = Color.BLACK;
	//protected Color TEXT_COLOR = Color.BLACK;
	protected Effect ELEMENT_SHADOW = new DropShadow(5, Color.BLACK);

	protected ObjectProperty<Color> FILL_COLOR, STROKE_COLOR, TEXT_COLOR; 
	protected SimpleDoubleProperty STROKE_WIDTH;
	
	
	protected Text symbolText;

	public Symbols(AppWindow eh) {
		listener = eh;
		elListener = eh;

		// setBackground(new Background(new BackgroundFill(Color.AQUA,
		// CornerRadii.EMPTY, Insets.EMPTY))); // TEST
		// this.paddingProperty().set(new Insets(0, 0, 0, 0));
		
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

	public Point2D getTopAnchor() {
		getPosition();
		Point2D anchors = new Point2D(x + (width / 2), y);
		return anchors;
	}

	public Point2D getBottomAnchor() {
		getPosition();
		Point2D anchors = new Point2D(x + (width / 2), y + height);
		return anchors;
	}

	public Point2D getLeftAnchor() {
		getPosition();
		Point2D anchors = new Point2D(x, y + (height / 2));
		return anchors;
	}

	public Point2D getRightAnchor() {
		getPosition();
		Point2D anchors = new Point2D(x + width, y + (height / 2));
		return anchors;
	}

	protected void getPosition() {
		x = getTranslateX();
		y = getTranslateY();
		height = getBoundsInLocal().getHeight() - 10;
		width = getBoundsInLocal().getWidth() - 10;
	}

	// METHODS TO HANDLE CONNECTION

	public void setConnected(Connectors connection) {
		connections.add(connection);
		connected = true;
	}

	public void removeConnected(Connectors currConnection) {
		connections.remove(currConnection);
		if (connections.isEmpty()) {
			connected = false;
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public List<Connectors> getConnections() {
		return connections;
	}

	// SELECTION METHODS

	public void setSelected() {
		selected = true;
		STROKE_COLOR.set(Color.CYAN);
	}

	public void setDeselected() {
		selected = false;
		setEffect(null);
		STROKE_COLOR.set(Color.BLACK);
	}

	// COLOR METHODS

	// IMPLEMENTED

	abstract void updateSize();

}
