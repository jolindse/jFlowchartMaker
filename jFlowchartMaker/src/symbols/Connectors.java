package symbols;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public abstract class Connectors {

	private Symbols start, end;
	protected SimpleDoubleProperty startX, startY, endX, endY, angle;
	protected Point2D startAnchors, endAnchors, distancePoint;

	protected double STROKEWIDTH = 2;
	protected Color FILL_COLOR = Color.BLACK;
	protected Color STROKE_COLOR = Color.BLACK;

	public Connectors(Symbols start, Symbols end) {
		this.start = start;
		this.end = end;
		getAnchors();
		
		startX = new SimpleDoubleProperty(startAnchors.getX());
		startY = new SimpleDoubleProperty(startAnchors.getY());
		angle = new SimpleDoubleProperty(Math.toDegrees(startAnchors.angle(endAnchors)));
		endX = new SimpleDoubleProperty(endAnchors.getX());
		endY = new SimpleDoubleProperty(endAnchors.getY());
		
	}

	private void getAnchors() {

		Point2D startTop, startBottom, startLeft, startRight, endTop, endBottom, endLeft, endRight, finalStart,
				finalEnd;

		List<Point2D> startList = new ArrayList<>();
		List<Point2D> endList = new ArrayList<>();

		finalStart = null;
		finalEnd = null;

		startTop = start.getTopAnchor();
		startBottom = start.getBottomAnchor();
		startRight = start.getRightAnchor();
		startLeft = start.getLeftAnchor();

		startList.add(startTop);
		startList.add(startBottom);
		startList.add(startRight);
		startList.add(startLeft);

		for (Point2D currPoint : startList) {

		}

		endTop = end.getTopAnchor();
		endBottom = end.getBottomAnchor();
		endRight = end.getRightAnchor();
		endLeft = end.getLeftAnchor();

		endList.add(endTop);
		endList.add(endBottom);
		endList.add(endRight);
		endList.add(endLeft);

		double dist = 1000000;

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
	}

	public void remove() {
		start.removeConnected(this);
		end.removeConnected(this);
	}

	public void setSelected() {

	}

	public void setDeselected() {

	}

	public void update() {
		getAnchors();
		startX.set(startAnchors.getX());
		startY.set(startAnchors.getY());
		angle.set(Math.toDegrees(startAnchors.angle(endAnchors)));
		endX.set(endAnchors.getX());
		endY.set(endAnchors.getY());
	}

}
