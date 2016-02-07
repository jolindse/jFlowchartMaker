package symbols;

import gui.AppWindow;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;

public class DecisionSymbol extends Symbols {

	private Path romb;
	
	private DoubleProperty 	startPointX, 
							startPointY,
							rightLineX,
							rightLineY,
							bottomLineX,
							bottomLineY,
							leftLineX,
							leftLineY;
	private MoveTo topPoint;
	private LineTo 	rightLine,
					bottomLine,
					leftLine,
					topLine;

	public DecisionSymbol(AppWindow listener) {
		super(listener);
		
		startPointX = new SimpleDoubleProperty(25);
		startPointY = new SimpleDoubleProperty(0);
		
		rightLineX = new SimpleDoubleProperty(50);
		rightLineY = new SimpleDoubleProperty(10);
		
		bottomLineX = new SimpleDoubleProperty(25);
		bottomLineY = new SimpleDoubleProperty(20);
		
		leftLineX = new SimpleDoubleProperty(0);
		leftLineY = new SimpleDoubleProperty(10);
		
		romb = new Path();
		
		topPoint = new MoveTo();
		topPoint.xProperty().bind(startPointX);
		topPoint.yProperty().bind(startPointY);
		
		rightLine = new LineTo();
		rightLine.xProperty().bind(rightLineX);
		rightLine.yProperty().bind(rightLineY);
		
		bottomLine = new LineTo();
		bottomLine.xProperty().bind(bottomLineX);
		bottomLine.yProperty().bind(bottomLineY);
		
		leftLine = new LineTo();
		leftLine.xProperty().bind(leftLineX);
		leftLine.yProperty().bind(leftLineY);
		
		topLine = new LineTo();
		topLine.xProperty().bind(startPointX);
		topLine.yProperty().bind(startPointY);
		
		romb.getElements().addAll(topPoint,rightLine,bottomLine,leftLine,topLine);
		
		romb.fillProperty().bind(FILL_COLOR);
		romb.strokeProperty().bind(STROKE_COLOR);
		romb.strokeWidthProperty().bind(STROKE_WIDTH);
		romb.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		// romb.setStroke(STROKE_COLOR);
		// romb.setStrokeWidth(STROKE_WIDTH);
		updateSize();
		this.getChildren().addAll(romb,symbolText);
	}

	void updateSize() {
		double width = symbolText.getLayoutBounds().getWidth() + 30;
		double height = symbolText.getLayoutBounds().getHeight() +50;
		
		startPointX.set(width/2);
		leftLineY.set(height/2);
		rightLineX.set(width);
		rightLineY.set(height/2);
		bottomLineX.set(width/2);
		bottomLineY.set(height);
	}
	
}
