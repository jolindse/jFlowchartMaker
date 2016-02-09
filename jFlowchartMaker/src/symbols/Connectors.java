package symbols;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Screen;

public abstract class Connectors {

	private Symbols start, end;
	private double x, y, width, height;
	private Path path;
	
	protected SimpleDoubleProperty startX, startY, endX, endY,angle;
	protected double startXv, startYv, endXv, endYv;
	protected Point2D startAnchors, endAnchors, distancePoint, wingOne, wingTwo;
	
	
	
	protected double STROKEWIDTH = 2;
	protected Color FILL_COLOR = Color.BLACK;
	protected Color STROKE_COLOR = Color.BLACK;

	public Connectors(Symbols start, Symbols end) {
		this.start = start;
		this.end = end;
		angle = new SimpleDoubleProperty();
		getAnchors();
		startX = new SimpleDoubleProperty(startAnchors.getX());
		startY = new SimpleDoubleProperty(startAnchors.getY());
		endX = new SimpleDoubleProperty(endAnchors.getX());
		endY = new SimpleDoubleProperty(endAnchors.getY());
	}
	

	
	public boolean getWings() {
		if (startAnchors.getX()<endAnchors.getX()){
			return true;
		} else {
			return false;
		}
	
	}
	
	private Point2D getTopAnchor(Symbols currSym) {
		//System.out.println("TopAnchor"); // TEST
		getPosition(currSym);
		Point2D anchors = new Point2D(x + (width / 2), y);
		return anchors;
	}

	private Point2D getBottomAnchor(Symbols currSym) {
		//System.out.println("BottomAnchor"); // TEST
		getPosition(currSym);
		Point2D anchors = new Point2D(x + (width / 2), y + height);
		return anchors;
	}

	private Point2D getLeftAnchor(Symbols currSym) {
		//System.out.println("LeftAnchor"); // TEST
		getPosition(currSym);
		Point2D anchors = new Point2D(x, y + (height / 2));
		return anchors;
	}

	private Point2D getRightAnchor(Symbols currSym) {
		//System.out.println("RightAnchor"); // TEST
		getPosition(currSym);
		Point2D anchors = new Point2D(x + width, y + (height / 2));
		return (anchors);
	}

	protected void getPosition(Symbols currSym) {
		System.out.println("LayoutX: "+currSym.getLayoutX()+" LayoutY: "+currSym.getLayoutY()); // TEST
		System.out.println("TranslateX: "+currSym.getTranslateX()+" TranslateY: "+currSym.getTranslateY()); // TEST
		

		x = currSym.getBoundsInParent().getMinX();
		y = currSym.getBoundsInParent().getMinY();
		
		//x = currSym.getTranslateX();
		//y = currSym.getTranslateY();
		
		System.out.println("ParentMinX: "+x+" ParentMinY :"+y); // TEST
	
		height = currSym.getBoundsInLocal().getHeight();
		width = currSym.getBoundsInLocal().getWidth();
		
	}
	
	private void getAnchors() {

		Point2D startTop, startBottom, startLeft, startRight, endTop, endBottom, endLeft, endRight, finalStart,
				finalEnd;

		List<Point2D> startList = new ArrayList<>();
		List<Point2D> endList = new ArrayList<>();

		finalStart = null;
		finalEnd = null;

		startTop = getTopAnchor(start);
		startBottom = getBottomAnchor(start);
		startRight = getRightAnchor(start);
		startLeft = getLeftAnchor(start);

		startList.add(startTop);
		startList.add(startBottom);
		startList.add(startRight);
		startList.add(startLeft);

		endTop = getTopAnchor(end);
		endBottom = getBottomAnchor(end);
		endRight = getRightAnchor(end);
		endLeft = getLeftAnchor(end);

		endList.add(endTop);
		endList.add(endBottom);
		endList.add(endRight);
		endList.add(endLeft);

		double dist = 1000000;  // Replace with scene diameter.

		for (Point2D currStart : startList) {
			for (Point2D currEnd : endList) {
				double currDist = currStart.distance(currEnd);
				if (dist > currDist) {
					finalStart = currStart;
					finalEnd = currEnd;
					dist = currDist;
				}
			}

		}
		startAnchors = finalStart;
		endAnchors = finalEnd;
		angle.set(Math.toDegrees(startAnchors.angle(endAnchors)));
		System.out.println("START ANCHORS: "+startAnchors+"\nEND ANCHORS: "+endAnchors);
		
	}

	public void remove(Symbols currSymbol) {
		if(start.equals(currSymbol)){
			end.removeConnected(this);
		}else{
			start.removeConnected(this);
		}
	}

	public void setPathReference(Path path){
		this.path = path;
	}
	
	public Path getPathReference(){
		return path;
	}
	
	public void setSelected() {

	}

	public void setDeselected() {

	}

	public void update() {
		getAnchors();
		
		startX.set(startAnchors.getX());
		startY.set(startAnchors.getY());
		endX.set(endAnchors.getX());
		endY.set(endAnchors.getY());
		
		startXv = startAnchors.getX();
		startYv = startAnchors.getY();
		endXv = startAnchors.getX();
		endYv = startAnchors.getY();
		
	}

}
