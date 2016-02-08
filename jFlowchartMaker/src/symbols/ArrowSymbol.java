package symbols;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;

public class ArrowSymbol extends Connectors {

	
	private Path arrow;
	
	public ArrowSymbol(Symbols startSymbol, Symbols endSymbol) {
		super(startSymbol,endSymbol);
			
		arrow = new Path();
		MoveTo start = new MoveTo();
		start.xProperty().bind(startX);
		start.yProperty().bind(startY);
		
		LineTo end = new LineTo();
		end.xProperty().bind(endX);
		end.yProperty().bind(endY);
		
		
		arrow.getElements().addAll(start,end);
		arrow.setStroke(STROKE_COLOR);
		arrow.setStrokeWidth(STROKEWIDTH);
		
	}
	
	public Path getArrow(){
		return arrow;
	}
}
