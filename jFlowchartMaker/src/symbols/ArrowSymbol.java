package symbols;

import interfaces.iObjects;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ArrowSymbol extends Connectors implements iObjects {

    private Path arrow;

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

    @Override
    public String getSaveString() {
        return "Connector,"+start.getSymbolId()+","+end.getSymbolId();
    }

    public Path getArrow() {
        return arrow;
    }
}
