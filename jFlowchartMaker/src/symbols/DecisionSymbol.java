package symbols;

import interfaces.iElements;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DecisionSymbol extends Symbols {

	private Rectangle romb;
	private iElements listener;
	private Text symbolText;

	public DecisionSymbol(iElements listener) {
		this.listener = listener;
		romb = new Rectangle(50, 50);
		romb.setFill(Color.WHITE);
		romb.setStroke(Color.BLACK);
		romb.setStrokeWidth(2);
		romb.setRotate(45);
		this.getChildren().add(romb);
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
			
			if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
				listener.selectElement(e);
			}
		});

		this.getChildren().add(symbolText);
	}

	private void updateSize() {
		double width = symbolText.getLayoutBounds().getWidth() + 20;
		romb.widthProperty().set(width);
		romb.heightProperty().set(width);
	}
	
}
