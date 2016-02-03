package symbols;

import interfaces.iElements;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

public class TerminatorSymbol extends Symbols {
	private Ellipse elipse;
	private iElements listener;
	private Text symbolText;

	public TerminatorSymbol (iElements listener) {
		this.listener = listener;
		elipse = new Ellipse(40,20);
		elipse.setFill(Color.WHITE);
		elipse.setStroke(Color.BLACK);
		elipse.setStrokeWidth(2);
		this.getChildren().add(elipse);
		symbolText = new Text(super.getTextFromDialog());
		symbolText.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			symbolText.setText(super.changeText(symbolText.getText()));
			updateSize();
		});
		updateSize();

		this.addEventHandler(MouseEvent.ANY, (e) -> {

			if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				listener.moveElement(e);
			}
		});

		this.getChildren().add(symbolText);
	}

	private void updateSize() {
		double width = symbolText.getLayoutBounds().getWidth() + 5;
		double height = symbolText.getLayoutBounds().getHeight() + 2;
		elipse.radiusXProperty().set(width);
		elipse.radiusYProperty().set(height);
	}
}
