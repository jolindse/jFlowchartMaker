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
	private double xPos, yPos;
	private iElement listener;
	
	public ProcessSymbol(iElements list) {
		super(list);
		listener = super.
		square = new Rectangle(100, 50);
		square.setFill(Color.WHITE);
		square.setStroke(Color.BLACK);
		square.setStrokeWidth(2);
		Text symbolText = new Text(super.getTextFromDialog());

		this.getChildren().addAll(square, symbolText);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			StackPane placePane = new StackPane();
			Rectangle placeHolder = new Rectangle(square.getWidth(), square.getHeight());
			placeHolder.setFill(Color.BLUE);
			placeHolder.setOpacity(40);
			placePane.getChildren().add(placeHolder);
			placePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					double x = placePane.getTranslateX() + event.getSceneX();
					double y = placePane.getTranslateY() + event.getSceneY();
					xPos = x;
					yPos = y;

				}
			});
			
		});

	}
}
