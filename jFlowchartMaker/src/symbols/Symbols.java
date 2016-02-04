package symbols;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import interfaces.iElements;
import javafx.geometry.Point2D;
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
	protected boolean textOnStart;

	protected Text symbolText;

	public Symbols(iElements listener, boolean textOnStart) {
		this.listener = listener;
		if (textOnStart) {
			initText();
		}
		addHandlers();
	}

	protected void addHandlers() {

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

	private double[] getTopAnchor() {
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0] + (pos[3] / 2);
		anchors[1] = pos[1];
		return anchors;
	}

	private double[] getBottomAnchor() {
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0] + (pos[3] / 2);
		anchors[1] = pos[1] + pos[2];
		return anchors;
	}

	private double[] getLeftAnchor() {
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0] + pos[3];
		anchors[1] = pos[1] + (pos[2] / 2);
		return anchors;
	}

	private double[] getRightAnchor() {
		double[] anchors = new double[2];
		double[] pos = getPosition();
		anchors[0] = pos[0] + pos[3];
		anchors[1] = pos[1] + (pos[2] / 2);
		return anchors;
	}

	private double[] getPosition() {
		double[] values = new double[4];
		values[0] = this.getTranslateX();
		values[1] = this.getTranslateY();
		values[2] = this.getBoundsInLocal().getHeight();
		values[3] = this.getBoundsInLocal().getWidth();
		return values;
	}

	public double[] getArrowAnchors(Symbols endElement) {

		double[] anchors = new double[4];
		Point2D startTop, startBottom, startLeft, startRight, endTop, endBottom, endLeft, endRight, finalStart,
				finalEnd;

		List<Point2D> startList = new ArrayList<>();
		List<Point2D> endList = new ArrayList<>();
		
		finalStart = null;
		finalEnd = null;
		
		startTop = new Point2D(this.getTopAnchor()[0], this.getTopAnchor()[1]);
		startBottom = new Point2D(this.getBottomAnchor()[0], this.getBottomAnchor()[1]);
		startRight = new Point2D(this.getRightAnchor()[0], this.getRightAnchor()[1]);
		startLeft = new Point2D(this.getLeftAnchor()[0], this.getLeftAnchor()[1]);
		startList.add(startTop);
		startList.add(startBottom);
		startList.add(startRight);
		startList.add(startLeft);
		
		endTop = new Point2D(endElement.getTopAnchor()[0], endElement.getTopAnchor()[1]);
		endBottom = new Point2D(endElement.getBottomAnchor()[0], endElement.getBottomAnchor()[1]);
		endRight = new Point2D(endElement.getRightAnchor()[0], endElement.getRightAnchor()[1]);
		endLeft = new Point2D(endElement.getLeftAnchor()[0], endElement.getLeftAnchor()[1]);
		endList.add(endTop);
		endList.add(endBottom);
		endList.add(endRight);
		endList.add(endLeft);

		double dist = 1000000;

		for (Point2D currStart : startList) {
			System.out.println("New startpoint!!!!"); // TEST
			for (Point2D currEnd : endList) {
				System.out.println("New endpoint!"); // TEST
				double currDist = currStart.distance(currEnd);
				if (dist > currDist) {
					System.out.println("New smallest distance. Was: "+dist+" is now: "+currDist); // TEST
					finalStart = currStart;
					finalEnd = currEnd;
					System.out.println("Anchors, start: "+finalStart+" end: "+finalEnd); // TEST
					dist = currDist;
				}
			}

		}
		anchors[0] = finalStart.getX();
		anchors[1] = finalStart.getY();
		anchors[2] = finalEnd.getX();
		anchors[3] = finalEnd.getY();
		return anchors;
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
