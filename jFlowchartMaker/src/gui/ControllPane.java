package gui;





import interfaces.iElements;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class ControllPane extends GridPane {
	private iElements listener;
	private Rectangle process, decision;
	private Ellipse terminator;
	
	public ControllPane(iElements ev) {
		listener = ev;
		
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setHgap(10);
		
		process = new Rectangle(30, 20);
		process.setFill(Color.WHITE);
		process.setStroke(Color.BLACK);
		process.setStrokeWidth(3);
		process.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			listener.addElement("process");
		});
		this.add(process, 0, 0);
		

		decision = new Rectangle(20, 20);
		decision.setRotate(45);
		decision.setFill(Color.WHITE);
		decision.setStroke(Color.BLACK);
		decision.setStrokeWidth(3);
		decision.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			listener.addElement("decision");
		});
		this.add(decision, 1, 0);
		
		terminator = new Ellipse(20, 10);
		terminator.setFill(Color.WHITE);
		terminator.setStroke(Color.BLACK);
		terminator.setStrokeWidth(3);
		terminator.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			listener.addElement("terminator");
		});
		this.add(terminator, 2, 0);
		
	}
	
}
