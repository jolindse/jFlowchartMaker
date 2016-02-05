package symbols;

import interfaces.iElements;
import javafx.scene.shape.Line;

public class ArrowSymbol extends Symbols {

	private double startX, startY, endX, endY;
	private double paneStartX, paneStartY, height, width;
	private Line line;

	public ArrowSymbol(iElements listener, double startX, double startY, double endX, double endY) {
		super(listener, false);
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;

		line = new Line(startX, startY, endX, endY);
		this.getChildren().add(line);
		height = line.getBoundsInLocal().getHeight();
		width = line.getBoundsInLocal().getWidth();
		getStart();

	}

	private void getStart() {
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
	}

	public double getStartX() {
		return paneStartX;
	}

	public double getStartY() {
		return paneStartY;
	}

	@Override
	void updateSize() {
		/*
		 * line.setStartX(start[0]); line.setStartY(start[1]);
		 * line.setEndX(end[0]); line.setEndY(end[1]);
		 */
	}

}
