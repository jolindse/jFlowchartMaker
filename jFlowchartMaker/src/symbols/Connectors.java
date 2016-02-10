package symbols;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Screen;

public abstract class Connectors {

	private Symbols start, end;
	private double x, y, width, height;
	private Path path;
	
	protected SimpleDoubleProperty startX, startY, endX, endY, arrowOneX, arrowOneY;
	protected double angle;
	protected Point2D startAnchors, endAnchors, distancePoint, wingOne, wingTwo;
	
	
	
	protected double STROKEWIDTH = 2;
	protected Color FILL_COLOR = Color.BLACK;
	protected Color STROKE_COLOR = Color.BLACK;

	public Connectors(Symbols start, Symbols end) {
		this.start = start;
		this.end = end;
		angle = 0;
		getAnchors();
		startX = new SimpleDoubleProperty(startAnchors.getX());
		startY = new SimpleDoubleProperty(startAnchors.getY());
		endX = new SimpleDoubleProperty(endAnchors.getX());
		endY = new SimpleDoubleProperty(endAnchors.getY());

        arrowOneX = new SimpleDoubleProperty(endAnchors.getX()-(Math.cos(angle)*20));
        arrowOneY = new SimpleDoubleProperty(endAnchors.getY()-(Math.cos(angle)*20));

	}
	

	
	public boolean getWings() {
		return startAnchors.getX() < endAnchors.getX();
	
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
		// System.out.println("LayoutX: "+currSym.getLayoutX()+" LayoutY: "+currSym.getLayoutY()); // TEST
		// System.out.println("TranslateX: "+currSym.getTranslateX()+" TranslateY: "+currSym.getTranslateY()); // TEST
		// System.out.println("TranslateX PROPERTIES: "+currSym.translateXProperty().getValue()+" TranslateY: "+currSym.translateYProperty().getValue()); // TEST


		x = currSym.getTranslateX();
		y = currSym.getTranslateY();
		
		//x = currSym.getTranslateX();
		//y = currSym.getTranslateY();
		

	
		height = currSym.calcHeight();
		width = currSym.calcWidth();
		System.out.println("ParentMinX: "+x+" ParentMinY :"+y+" Width: "+width+" Height: "+height); // TEST
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
        calcAngle();
		System.out.println("START ANCHORS: "+startAnchors+"\nEND ANCHORS: "+endAnchors);
		
	}

    private void calcAngle(){

        // double coords[] ={ endAnchors.getX()-10,endAnchors.getY()-10)
        //double yMinLenght=;
        // AffineTransform.getRotateInstance(Math.toRadians(angle), endAnchors.getX(), endAnchors.getY()).transform(pt, 0, pt, 0, 1);

        double pathAngle = startAnchors.angle(endAnchors);
        angle = (90-pathAngle);
        //System.out.println("Angle: "+angle.getValue()+" AnglePath: "+pathAngle+" arrowAngle (degrees): "+arrowAngle+" in radians: "+Math.toRadians(arrowAngle)); // TEST
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
        arrowOneX.set(endAnchors.getX()-(Math.cos(angle)*20));
        arrowOneY.set(endAnchors.getY()-(Math.cos(angle)*20));


    }

}
