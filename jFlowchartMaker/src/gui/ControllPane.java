package gui;

import interfaces.iControll;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

public class ControllPane extends HBox {

	private ObjectProperty<Color> currColor;

	private iControll controll;
	private Rectangle process, decision;
	private Ellipse terminator;
	private Group arrow, text;
	private ColorPicker picker;


	public ControllPane(iControll eh) {
		controll = eh;

		currColor = new SimpleObjectProperty<>(Color.WHITE);

		setSpacing(10);
		setPadding(new Insets(10, 10, 10, 10));



		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controll.removeSymbolSelected();
			clearSelection();
		});



		process = new Rectangle(30, 20);
		process.setId("process");
		process.setFill(Color.WHITE);
		process.setStroke(Color.BLACK);
		process.setStrokeWidth(3);
		process.setEffect(null);
		process.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			clearSelection();
			setSelected("process");
			process.setEffect(new DropShadow());
			e.consume();
		});

		decision = new Rectangle(20, 20);
		decision.setRotate(45);
		decision.setFill(Color.WHITE);
		decision.setStroke(Color.BLACK);
		decision.setStrokeWidth(3);
		decision.setEffect(null);
		decision.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			clearSelection();
			setSelected("decision");
			decision.setEffect(new DropShadow());
			e.consume();
		});

		terminator = new Ellipse(20, 10);
		terminator.setFill(Color.WHITE);
		terminator.setStroke(Color.BLACK);
		terminator.setStrokeWidth(3);
		terminator.setEffect(null);
		terminator.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			clearSelection();
			setSelected("terminator");
			terminator.setEffect(new DropShadow());
			e.consume();
		});

		text = new Group();
		Text abc = new Text("Abc");
		abc.setFontSmoothingType(FontSmoothingType.LCD);
		abc.setFont(Font.font("Serif", 24));
		abc.setFill(Color.BLACK);
		text.getChildren().add(abc);
		text.setEffect(null);
		text.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			clearSelection();
			setSelected("text");
			text.setEffect(new DropShadow());
			e.consume();
		});

		arrow = new Group();
		Rectangle topRec = new Rectangle(0, 0, 15, 15);
		topRec.setStrokeWidth(2);
		topRec.setFill(Color.WHITE);
		topRec.setStroke(Color.BLACK);
		Rectangle bottomRec = new Rectangle(25, 10, 15, 15);
		bottomRec.setStrokeWidth(2);
		bottomRec.setFill(Color.WHITE);
		bottomRec.setStroke(Color.BLACK);
		Line line = new Line(15, 7.5, 25, 17.5);
		arrow.getChildren().addAll(topRec, bottomRec, line);
		arrow.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controll.addConnector();
			e.consume();
		});

		picker = new ColorPicker();
		picker.setOnAction((e) -> {
			currColor.set(picker.getValue());
		});

		Region spacer = new Region();
		setHgrow(spacer, Priority.ALWAYS);
		spacer.setMinWidth(Region.USE_PREF_SIZE);

		Rectangle changeFillColor = new Rectangle(20, 20);
		changeFillColor.fillProperty().bind(currColor);
		changeFillColor.setStroke(Color.BLACK);
		changeFillColor.setStrokeWidth(3);
		changeFillColor.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controll.setElementColor("fill");
			e.consume();
		});

		Rectangle changeBorderColor = new Rectangle(20, 20);
		changeBorderColor.setFill(Color.WHITE);
		changeBorderColor.strokeProperty().bind(currColor);
		changeBorderColor.setStrokeWidth(3);
		changeBorderColor.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controll.setElementColor("stroke");
			e.consume();
		});


		this.getChildren().addAll(process, decision, terminator, text, arrow, spacer, changeFillColor,
				changeBorderColor, picker);
	}

	// MAKE SHAPES METHODS

	private Group getRectangle() {
		Group currShape = new Group();
		Rectangle currSymbol = new Rectangle(60, 40);
		currSymbol.setFill(Color.WHITE);
		currSymbol.setStroke(Color.BLACK);
		currSymbol.setStrokeWidth(3);
		currShape.getChildren().add(currSymbol);
		return currShape;
	}

	private Group getRomb() {
		Group currShape = new Group();
		Rectangle currSymbol = new Rectangle(40, 40);
		currSymbol.setRotate(45);
		currSymbol.setFill(Color.WHITE);
		currSymbol.setStroke(Color.BLACK);
		currSymbol.setStrokeWidth(3);
		currShape.getChildren().add(currSymbol);
		return currShape;
	}

	private Group getEllipse() {
		Group currShape = new Group();
		Ellipse currSymbol = new Ellipse(40, 20);
		currSymbol.setFill(Color.WHITE);
		currSymbol.setStroke(Color.BLACK);
		currSymbol.setStrokeWidth(3);
		currShape.getChildren().add(currSymbol);
		return currShape;
	}

	private Group getTextSymbol() {
		Group currShape = new Group();
		Text currSymbol = new Text("Abc");
		currSymbol.setFontSmoothingType(FontSmoothingType.LCD);
		currSymbol.setFont(Font.font("Serif", 24));
		currSymbol.setFill(Color.BLACK);
		currShape.getChildren().add(currSymbol);
		return currShape;
	}

	public void setSelected(String type) {
			controll.setSymbolSelected(type);
	}

	public void clearSelection() {
		process.setEffect(null);
		decision.setEffect(null);
		terminator.setEffect(null);
		text.setEffect(null);
		}

	public Group getSymbolForMouse(String type) {
		Group currSymbol = new Group();
		switch (type) {
			case "process":
				currSymbol = getRectangle();
				break;
			case "decision":
				currSymbol = getRomb();
				break;
			case "terminator":
				currSymbol = getEllipse();
				break;
			case "text":
				currSymbol = getTextSymbol();
				break;
		}
		currSymbol.setOpacity(0.3);
		return currSymbol;
	}

	public Color getColor(){
		return picker.getValue();
	}
}
