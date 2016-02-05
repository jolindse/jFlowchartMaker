package symbols;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import interfaces.iElements;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class Symbols extends StackPane {

	protected iElements listener;
	protected boolean selected = false;
	protected boolean arrowed = false;
	protected boolean textOnStart;

	protected Text symbolText;
	protected ArrowSymbol arrow;
	
	public Symbols(iElements listener, boolean textOnStart) {
		this.listener = listener;
		if (textOnStart) {
			initText();
		}
		addHandlers();
	}

	void addHandlers() {

		this.addEventHandler(MouseEvent.ANY, (e) -> {
			if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				listener.moveElement(e);
			}

			if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
				Symbols currSymbol = (Symbols) e.getSource();
				if (e.isShiftDown()) {
					listener.addElementToSelections(currSymbol);
				} else {
					listener.selectElement(currSymbol);
				}
			}

			if (e.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
				setSelected();
			}

			if (e.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
				setDeselected();
			}
		});
	}

	protected void initText() {
		symbolText = new Text(getTextFromDialog());
		symbolText.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			if (e.getClickCount() == 2) {
				symbolText.setText(changeText(symbolText.getText()));
				updateSize();
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

	/*
	 * ANCHORPOINT METHODS FOR ARROWS
	 * 
	 * returns the X,Y of the arrow anchor points.
	 * 
	 */

	public Point2D getTopAnchor() {
		double x = getPosition()[0];
		double y = getPosition()[1];
		double height = getPosition()[2];
		double width = getPosition()[3];
		Point2D anchors = new Point2D(x+(width/2),y);
		return anchors;
	}

	public Point2D getBottomAnchor() {
		double x = getPosition()[0];
		double y = getPosition()[1];
		double height = getPosition()[2];
		double width = getPosition()[3];
		Point2D anchors = new Point2D(x+(width/2),y+height);
		return anchors;
	}

	public Point2D getLeftAnchor() {
		double x = getPosition()[0];
		double y = getPosition()[1];
		double height = getPosition()[2];
		double width = getPosition()[3];
		Point2D anchors = new Point2D(x,y+(height/2));
		return anchors;
	}

	public Point2D getRightAnchor() {
		double x = getPosition()[0];
		double y = getPosition()[1];
		double height = getPosition()[2];
		double width = getPosition()[3];
		double[] pos = getPosition();
		Point2D anchors = new Point2D(x+width,y+(height/2));
		return anchors;
	}

	protected double[] getPosition() {
		double[] values = new double[4];
		values[0] = this.getTranslateX();
		values[1] = this.getTranslateY();
		values[2] = this.getBoundsInLocal().getHeight();
		values[3] = this.getBoundsInLocal().getWidth();
		return values;
	}

	public Point2D[] getArrowAnchors(Symbols endElement) {

		Point2D[] anchors = new Point2D[2];
		Point2D startTop, startBottom, startLeft, startRight, endTop, endBottom, endLeft, endRight, finalStart,
				finalEnd;

		List<Point2D> startList = new ArrayList<>();
		List<Point2D> endList = new ArrayList<>();
		
		finalStart = null;
		finalEnd = null;
		
		startTop = this.getTopAnchor();
		startBottom = this.getBottomAnchor();
		startRight = this.getRightAnchor();
		startLeft = this.getLeftAnchor();
		startList.add(startTop);
		startList.add(startBottom);
		startList.add(startRight);
		startList.add(startLeft);
		
		endTop = endElement.getTopAnchor();
		endBottom = endElement.getBottomAnchor();
		endRight = endElement.getRightAnchor();
		endLeft = endElement.getLeftAnchor();
		endList.add(endTop);
		endList.add(endBottom);
		endList.add(endRight);
		endList.add(endLeft);

		double dist = 1000000;

		for (Point2D currStart : startList) {
			for (Point2D currEnd : endList) {
				double currDist = currStart.distance(currEnd);
				if (dist > currDist) {
					finalStart = currStart;
					finalEnd = currEnd;
					dist = currDist;
				}
			}

		}
		anchors[0] = finalStart;
		anchors[1] = finalEnd;
		
		return anchors;
	}

	public void setArrowed(ArrowSymbol arrow) {
		this.arrow = arrow;
		arrowed = true;
	}
	
	public void removeArrow() {
		arrow = null;
		arrowed = false;
	}
	
	// SELECTION METHODS

	private void setSelected() {
		this.setEffect(new DropShadow(5, Color.BLACK));
	}

	public void setDeselected() {
		this.setEffect(null);
	}

	abstract void updateSize();

}
