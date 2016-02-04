package gui;





import interfaces.iElements;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class ControllPane extends GridPane {
	private iElements listener;
	private Rectangle process, decision;
	private Ellipse terminator;
	private Effect effect;
	
	public ControllPane(iElements ev) {
		listener = ev;
		
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setHgap(10);
	
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			handleClick(false, null);
		});
		
		effect = null;
		
		process = new Rectangle(30, 20);
		process.setFill(Color.WHITE);
		process.setStroke(Color.BLACK);
		process.setStrokeWidth(3);
		process.setEffect(effect);
		process.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
			handleClick(true,"process");
			process.setEffect(new DropShadow());
			e.consume();
		});
		this.add(process, 0, 0);
		

		decision = new Rectangle(20, 20);
		decision.setRotate(45);
		decision.setFill(Color.WHITE);
		decision.setStroke(Color.BLACK);
		decision.setStrokeWidth(3);
		decision.setEffect(effect);
		decision.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			handleClick(true,"decision");
			decision.setEffect(new DropShadow());
			e.consume();
		});
		this.add(decision, 1, 0);
		
		terminator = new Ellipse(20, 10);
		terminator.setFill(Color.WHITE);
		terminator.setStroke(Color.BLACK);
		terminator.setStrokeWidth(3);
		terminator.setEffect(effect);
		terminator.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			handleClick(true,"terminator");
			terminator.setEffect(new DropShadow());
			e.consume();
		});
		this.add(terminator, 2, 0);
		
	}
	
	private void handleClick(boolean symbol, String type) {
		if (symbol) {
			listener.selectSymbol(type);
		} else {
			listener.deselectSymbol();
			process.setEffect(effect);
			decision.setEffect(effect);
			terminator.setEffect(effect);
		}
	}
	
}
