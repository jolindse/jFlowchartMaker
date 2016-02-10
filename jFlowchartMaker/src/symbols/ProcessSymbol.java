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
		width = symbolText.getLayoutBounds().getWidth() + 20;
		height = symbolText.getLayoutBounds().getHeight() + 20;
		if (width<60) {
			width = 60;
		}
		
		if (height<40) {
			height = 40;
		}
		square.widthProperty().set(width);
		square.heightProperty().set(height);
	}

	@Override
	double calcHeight() {
		return height;
	}

	@Override
	double calcWidth() {

        return width;
	}
}
