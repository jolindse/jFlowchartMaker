package symbols;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ArrowSymbol extends Connectors {

    private Path arrow;

	/*
    private double posetiveAngle = 45;
	private double negativeAngle = -45;
    private double wingLength = 40;
	*/

    public ArrowSymbol(Symbols startSymbol, Symbols endSymbol) {
        super(startSymbol, endSymbol);


        arrow = new Path();

        MoveTo start = new MoveTo();
        start.xProperty().bind(startX);
        start.yProperty().bind(startY);

        LineTo end = new LineTo();
        end.xProperty().bind(endX);
        end.yProperty().bind(endY);

        MoveTo endPoint = new MoveTo();
        endPoint.xProperty().bind(endX);
        endPoint.yProperty().bind(endX);

        LineTo wingOne = new LineTo();
		wingOne.xProperty().bind(wingOneX);
        wingOne.yProperty().bind(wingOneY);

        MoveTo back = new MoveTo();
        back.xProperty().bind(endX);
        back.yProperty().bind(endY);

        LineTo wingTwo = new LineTo();
        wingTwo.xProperty().bind(wingTwoX);
        wingTwo.yProperty().bind(wingTwoY);

        arrow.getElements().addAll(start, end, wingOne, back, wingTwo);
        arrow.setStroke(STROKE_COLOR);
        arrow.setStrokeWidth(STROKEWIDTH);

    }

    public Path getArrow() {
        return arrow;
    }
}
