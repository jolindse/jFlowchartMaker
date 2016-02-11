package gui;

import interfaces.iControll;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ContentArea extends AnchorPane {

    private iControll controll;

    public ContentArea(iControll eh) {
        controll = eh;
        this.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    public void addElement(ObservableList<Node> list) {
        this.getChildren().clear();
        this.getChildren().addAll(list);

    }

}
