package symbols;

import interfaces.iElements;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

public class TerminatorSymbol extends Symbols {
	private Ellipse elipse;

	public TerminatorSymbol (iElements listener, double x, double y) {
		super(listener);
		elipse = new Ellipse(x,y,40,20);
		elipse.setFill(Color.WHITE);
		elipse.setStroke(Color.BLACK);
		elipse.setStrokeWidth(2);
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
