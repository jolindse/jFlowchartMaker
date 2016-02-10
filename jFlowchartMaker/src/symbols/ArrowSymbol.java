package symbols;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ArrowSymbol extends Connectors {

	private Path arrow;
	private double posetiveAngle = 45;
	private double negativeAngle = -45;
    private double wingLength = 40;

	public ArrowSymbol(Symbols startSymbol, Symbols endSymbol) {
		super(startSymbol, endSymbol);


		System.out.println("Angle:"+angle); // TEST

		arrow = new Path();
		MoveTo start = new MoveTo();
		start.xProperty().bind(startX);
		start.yProperty().bind(startY);

		LineTo end = new LineTo();
		end.xProperty().bind(endX);
		end.yProperty().bind(endY);

		LineTo wingOne = new LineTo();
		//if (getWings()) {
			wingOne.xProperty().bind(arrowOneX);
			wingOne.yProperty().bind(arrowOneY);
		//} else {
		//	wingOne.xProperty().bind(end.xProperty().add(Math.cos(angle.get()) * 10));
		//	wingOne.yProperty().bind(end.yProperty().add(Math.sin(angle.get()) * 10));
		//}
		

		MoveTo back = new MoveTo();
		back.xProperty().bind(endX);
		back.yProperty().bind(endY);

		LineTo wingTwo = new LineTo();

		//if(getWings()){
			wingTwo.xProperty().bind(end.xProperty().add(Math.cos(angle) * wingLength));
			wingTwo.yProperty().bind(end.yProperty().add(Math.sin(angle) * wingLength));
		//} else {
		//	wingTwo.xProperty().bind(end.xProperty().subtract(Math.cos(angle.get()) * 10));
		//	wingTwo.yProperty().bind(end.yProperty().subtract(Math.sin(angle.get()) * 10));
		//}

		arrow.getElements().addAll(start, end, wingOne);
		arrow.setStroke(STROKE_COLOR);
		arrow.setStrokeWidth(STROKEWIDTH);

	}

	public Path getArrow() {
		return arrow;
	}
}
