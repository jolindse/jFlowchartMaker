package symbols;

import gui.AppWindow;
import interfaces.iControll;
import interfaces.iObjects;
import javafx.scene.shape.Rectangle;

/**
 * Process symbol. Auto sized depending on text size.
 */
public class ProcessSymbol extends Symbols implements iObjects {

	private Rectangle square;

	public ProcessSymbol(iControll listener) {
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
		return square.getHeight();
	}

	@Override
	double calcWidth() {
        return square.getWidth();
	}

	@Override
    public String getSaveString() {
		String text = symbolText.getText().replaceAll("/[,\'\"]/","");
		return "Symbol,Process,"+id+","+x+","+y+","+width+","+height+","+text;
	}
}
