package symbols;

import interfaces.iElements;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ProcessSymbol extends Symbols {

	private Rectangle square;
	private Text symbolText;
	private iElements listener;

	public ProcessSymbol(iElements listener) {
		this.listener = listener;

		square = new Rectangle(100, 50);
		square.setFill(Color.WHITE);
		square.setStroke(Color.BLACK);
		square.setStrokeWidth(2);
		this.getChildren().add(square);
		symbolText = new Text(super.getTextFromDialog());
		symbolText.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			symbolText.setText(super.getTextFromDialog());
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
		double width = symbolText.getLayoutBounds().getWidth() + 20;
		double height = symbolText.getLayoutBounds().getHeight() + 20;
		square.widthProperty().set(width);
		square.heightProperty().set(height);
	}
}
