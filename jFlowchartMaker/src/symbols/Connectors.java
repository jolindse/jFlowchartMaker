package symbols;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.List;

public abstract class Connectors {


	private double x, y, width, height;
	private Path path;
    private Point2D startAnchors, endAnchors,  wingOne, wingTwo;

	protected SimpleDoubleProperty startX, startY, endX, endY, wingOneX, wingOneY, wingTwoX, wingTwoY;
    protected Symbols start, end;
	protected double STROKEWIDTH = 2;
	protected Color STROKE_COLOR = Color.BLACK;

	public Connectors(Symbols start, Symbols end) {
		this.start = start;
		this.end = end;

		getAnchors();
		startX = new SimpleDoubleProperty(startAnchors.getX());
		startY = new SimpleDoubleProperty(startAnchors.getY());
		endX = new SimpleDoubleProperty(endAnchors.getX());
		endY = new SimpleDoubleProperty(endAnchors.getY());

        wingOneX = new SimpleDoubleProperty(wingOne.getX());
        wingOneY = new SimpleDoubleProperty(wingOne.getY());
        wingTwoX = new SimpleDoubleProperty(wingTwo.getX());
        wingTwoY = new SimpleDoubleProperty(wingTwo.getY());

	}
	
	private Point2D getTopAnchor(Symbols currSym) {
		getPosition(currSym);
		Point2D anchors = new Point2D(x + (width / 2), y);
		return anchors;
	}

	private Point2D getBottomAnchor(Symbols currSym) {
		getPosition(currSym);
		Point2D anchors = new Point2D(x + (width / 2), y + height);
		return anchors;
	}

	private Point2D getLeftAnchor(Symbols currSym) {
		getPosition(currSym);
		Point2D anchors = new Point2D(x, y + (height / 2));
		return anchors;
	}

	private Point2D getRightAnchor(Symbols currSym) {
		getPosition(currSym);
		Point2D anchors = new Point2D(x + width, y + (height / 2));
		return (anchors);
	}

	protected void getPosition(Symbols currSym) {

		x = currSym.getTranslateX();
		y = currSym.getTranslateY();

		height = currSym.calcHeight();
		width = currSym.calcWidth();
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

		double dist = 10000000;

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
        wingOne = setWings(40);
        wingTwo = setWings(-40);
    }

    private Point2D setWings(double diffAngle){

        /*
		double a = endAnchors.getX();
		double b = endAnchors.getY();

		// point B coordinates
		double c = startAnchors.getX();
		double d = startAnchors.getY();

		// calculate distance between the two points
		double DT = Math.sqrt(Math.pow((c - a), 2) + Math.pow((d - b), 2));

		double D = 20.0; // distance to point C

		double x;
		double y;
		double T = D / DT;

		// finding point C coordinate
		x = (1 - T) * a + T * c;
		y = (1 - T) * b + T * d;
        */

		// Calculate point to move
        x = endAnchors.getX()-0.05*(startAnchors.getX()-endAnchors.getX());
        y = endAnchors.getY()-0.05*(startAnchors.getY()-endAnchors.getY());


        // Rotate dot.
        x -= endAnchors.getX();
        y -= endAnchors.getY();
        double newX = (x*Math.cos(diffAngle)-y*Math.sin(diffAngle))+endAnchors.getX();
        double newY = (y*Math.cos(diffAngle)+x*Math.sin(diffAngle))+endAnchors.getY();

        return new Point2D(newX,newY);
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
        wingOneX.set(wingOne.getX());
        wingOneY.set(wingOne.getY());
        wingTwoX.set(wingTwo.getX());
        wingTwoY.set(wingTwo.getY());
    }

    // abstract String getSaveString();
}
