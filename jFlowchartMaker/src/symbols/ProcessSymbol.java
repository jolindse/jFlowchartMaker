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

	public ProcessSymbol(iElements listener) {
		super(listener,true);

		square = new Rectangle(100, 50);
		square.setFill(Color.WHITE);
		square.setStroke(Color.BLACK);
		square.setStrokeWidth(2);
		this.getChildren().add(square);
		updateSize();

		
		this.getChildren().add(symbolText);
	}

	void updateSize() {
		double width = symbolText.getLayoutBounds().getWidth() + 20;
		double height = symbolText.getLayoutBounds().getHeight() + 20;
		square.widthProperty().set(width);
		square.heightProperty().set(height);
	}
}
