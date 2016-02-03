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
	private Text symbolText;
	private iElements listener;

	public ProcessSymbol(iElements listener) {
		this.listener = listener;

		/*
		 * double minSizeW = 100; double minSizeH = 50;
		 */
		square = new Rectangle(100, 50);
		square.setFill(Color.WHITE);
		square.setStroke(Color.BLACK);
		square.setStrokeWidth(2);
		symbolText = new Text(super.getTextFromDialog());
		symbolText.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			symbolText.setText(super.getTextFromDialog());
			updateSize();
		});
		updateSize();

		this.addEventHandler(MouseEvent.ANY, (e) -> {

			if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				System.out.println("Mouse clicked subclass!"); // TEST
				listener.moveElement(e);
			}
		});

		/*
		 * 
		 * this.addEventHandler(MouseEvent.ANY, (e) -> {
		 * if(e.equals(MouseEvent.MOUSE_DRAGGED)){ System.out.println(
		 * "Mouse dragged!"); // TEST double x = this.getTranslateX() +
		 * e.getSceneX(); double y = this.getTranslateY() + e.getSceneY();
		 * square.setX(x); square.setY(y); } });
		 */
		this.getChildren().addAll(square, symbolText);
	}

	private void updateSize() {
		double width = symbolText.getLayoutBounds().getWidth() + 20;
		double height = symbolText.getLayoutBounds().getHeight() + 20;
		square.widthProperty().set(width);
		square.heightProperty().set(height);
	}
}
