package symbols;

import gui.AppWindow;
import javafx.scene.shape.Rectangle;

public class ProcessSymbol extends Symbols {

	private Rectangle square;

	public ProcessSymbol(AppWindow listener) {
		super(listener);

		square = new Rectangle(100, 50);
		square.fillProperty().bind(FILL_COLOR);
		square.strokeProperty().bind(STROKE_COLOR);
		square.strokeWidthProperty().bind(STROKE_WIDTH);
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
