package symbols;

import interfaces.iElements;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ArrowSymbol extends Symbols {

	// private double startX, startY, endX, endY;
	private SimpleDoubleProperty startX, startY, endX, endY;
	private double paneStartX, paneStartY, height, width;
	private Line line;
	private Symbols[] connectedSymbols;

	public ArrowSymbol(iElements listener, Point2D startPoint, Point2D endPoint, Symbols startSymbol, Symbols endSymbol) {
		super(listener, false);
		System.out.println("Start and endpoint "+startPoint+" , "+endPoint); // TEST
		startX = new SimpleDoubleProperty(startPoint.getX());
		startY = new SimpleDoubleProperty(startPoint.getY());
		endX = new SimpleDoubleProperty(endPoint.getX());
		endY = new SimpleDoubleProperty(endPoint.getY());;
		connectedSymbols = new Symbols[2];
		connectedSymbols[0] = startSymbol;
		connectedSymbols[1] = endSymbol;
		
		Path arrow = new Path();
		MoveTo start = new MoveTo();
		start.xProperty().bind(startX);
		start.yProperty().bind(startY);
		
		LineTo end = new LineTo();
		end.xProperty().bind(endX);
		end.yProperty().bind(endY);
		arrow.getElements().addAll(start,end);
		this.getChildren().add(arrow);
		
		//getStart();

	}
	/*

	private void getStart() {
		height = line.getBoundsInLocal().getHeight();
		width = line.getBoundsInLocal().getWidth();
		if (startX < endX && startY > endY) {
			paneStartX = startX;
			paneStartY = startY - height;
		} else if (startX > endX && startY > endY) {
			paneStartX = endX;
			paneStartY = endY;
		} else if (startX > endX && startY < endY) {
			paneStartX = endX;
			paneStartY = endY-height;

		} else {
			paneStartX = startX;
			paneStartY = startY;
		}
	}*/
	public double getStartX() {
		return paneStartX;
	}

	public double getStartY() {
		return paneStartY;
	}
	
	public Symbols[] getConnectedSymbols() {
		return connectedSymbols;
	}
	
	public void updateConnection(Point2D startPoint, Point2D endPoint) {
		startX.set(startPoint.getX());
		startY.set(startPoint.getY());
		endX.set(endPoint.getX());
		endY.set(endPoint.getY());
	}
	
	/*
	public void updateArrow(Point2D start, Point2D end) {
		startX = start.getX();
		startY = start.getY();
		endX = end.getX();
		endY = end.getY();
		getStart();
	}
	*/
	@Override
	void updateSize() {
		
	}

}
