package symbols;

import interfaces.iElements;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DecisionSymbol extends Symbols {

	private Rectangle romb;

	public DecisionSymbol(iElements listener,double x, double y) {
		super(listener);
		romb = new Rectangle(x,y,50,50);
		romb.setFill(Color.WHITE);
		romb.setStroke(Color.BLACK);
		romb.setStrokeWidth(2);
		romb.setRotate(45);
		this.getChildren().add(romb);
		/*
		symbolText = new Text(super.getTextFromDialog());
		symbolText.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			symbolText.setText(super.changeText(symbolText.getText()));
			updateSize();
		});
		*/
		updateSize();

		

		this.getChildren().add(symbolText);
	}

	void updateSize() {
		double width = symbolText.getLayoutBounds().getWidth() + 20;
		romb.widthProperty().set(width);
		romb.heightProperty().set(width);
	}
	
}
