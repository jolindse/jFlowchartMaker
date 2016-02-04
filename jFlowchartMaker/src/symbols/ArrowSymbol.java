package symbols;

import interfaces.iElements;
import javafx.scene.shape.Line;

public class ArrowSymbol extends Symbols {

	private double startX, startY, endX, endY;
	private Line line;
	
	
	public ArrowSymbol(iElements listener,double startX, double startY,double endX, double endY) {
		super(listener,false);
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		
		line = new Line(startX, startY, endX, endY);
		this.getChildren().add(line);
	}
	
	/*
	public void setStart(double[] start){
		this.start = start;
	}
	
	public void setEnd(double[] end) {
		this.end=end;
	}
	*/
	@Override
	void updateSize() {
		/*
		line.setStartX(start[0]);
		line.setStartY(start[1]);
		line.setEndX(end[0]);
		line.setEndY(end[1]);
		*/
	}

}
