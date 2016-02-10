package symbols;

import gui.AppWindow;
import javafx.scene.shape.Ellipse;

public class TerminatorSymbol extends Symbols {
	private Ellipse elipse;

	public TerminatorSymbol (AppWindow listener) {
		super(listener);
		elipse = new Ellipse(40,20);
		elipse.fillProperty().bind(FILL_COLOR);
		elipse.strokeProperty().bind(STROKE_COLOR);
		elipse.strokeWidthProperty().bind(STROKE_WIDTH);
		this.getChildren().add(elipse);

		updateSize();

		this.getChildren().add(symbolText);
	}

	void updateSize() {
		width = symbolText.getLayoutBounds().getWidth() + 5;
		height = symbolText.getLayoutBounds().getHeight() + 2;
		if (width<40) {
			width = 40;
		}
		
		if (height<20) {
			height = 20;
		}
		elipse.radiusXProperty().set(width);
		elipse.radiusYProperty().set(height);
	}

	@Override
	double calcHeight() {
		return height*2;
	}

	@Override
	double calcWidth() {
		return width*2;
	}
}
