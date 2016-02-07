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
		double width = symbolText.getLayoutBounds().getWidth() + 5;
		double height = symbolText.getLayoutBounds().getHeight() + 2;
		elipse.radiusXProperty().set(width);
		elipse.radiusYProperty().set(height);
	}
}
