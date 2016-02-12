package symbols;

import gui.AppWindow;
import interfaces.iControll;
import interfaces.iObjects;
import javafx.scene.shape.Ellipse;

/**
 * Terminator symbol. Auto sized depending on text size.
 */
public class TerminatorSymbol extends Symbols implements iObjects {
	private Ellipse elipse;

	public TerminatorSymbol (iControll listener) {
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

	@Override
    public String getSaveString() {
		String text = symbolText.getText().replaceAll("/[,\'\"]/","");
		return "Symbol,Terminator,"+id+","+x+","+y+","+width+","+height+","+text;
	}
}
