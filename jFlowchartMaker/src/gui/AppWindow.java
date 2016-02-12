package gui;

import interfaces.iControll;
import interfaces.iObjects;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import symbols.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Main gui-class
 */
public class AppWindow {

    private BorderPane root;
    private ContentArea content;
    private ControllPane controllpane;
    private MainMenu menubar;

    private iControll controll;

    private List<Symbols> selectedElements;
    private List<Connectors> selectedConnectors;
    private ObservableList<Node> elements;
    private boolean isSymbolSelected;

    private String symbolType;
    private Group currSymbol;

    /**
     * Gui-start class. Sets up gui and initializes different panels in view.
     * @param stage
     * @param controll
     */
    public AppWindow(Stage stage, iControll controll) {
        this.controll = controll;
        root = new BorderPane();
        Scene scene = new Scene(root, 600, 800);

        selectedElements = new ArrayList<>();
        selectedConnectors = new ArrayList<>();

        elements = FXCollections.observableArrayList();

        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.show();

        content = new ContentArea(controll);
        controllpane = new ControllPane(controll);
        menubar = new MainMenu(controll);

        root.setTop(menubar);
        root.setCenter(content);
        root.setBottom(controllpane);

        content.addEventHandler(MouseEvent.ANY, (e) -> {
            if(isSymbolSelected) {
                if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                    addElement(e);

                }
                if (e.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
                    currSymbol.toFront();
                    double x = currSymbol.getLayoutX() + e.getX();
                    double y = currSymbol.getLayoutY() + e.getY();
                    currSymbol.setTranslateX(x);
                    currSymbol.setTranslateY(y);
                }

                if (e.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
                    elements.add(currSymbol);
                }
                if (e.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
                    elements.remove(currSymbol);
                }
            }
        });

        elements.addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
                content.getChildren().clear();
                content.getChildren().addAll(elements);
            }
        });


        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.DELETE) {
                for (Symbols currElement : selectedElements) {
                    for (Connectors currConn : currElement.getConnections()) {
                        currConn.remove(currElement);
                        elements.remove(currConn.getPathReference());
                        elements.remove(currConn);
                    }
                    elements.remove(currElement);
                }
                controll.clearAll();
            }
        });

    }

    /*
    SETTERS FOR CONTROLLER TO UPDATE VIEW
    =====================================
    Methods to handle variables used for gui handling of elements and symbols.
    */

    public void setSelectedElements(List<Symbols> selected) {
        selectedElements = selected;
    }

    public void setSelectedConnectors(List<Connectors> selected) {
        selectedConnectors = selected;
    }

    public void setSymbolType(String type) {
        symbolType = type;
        currSymbol = getSymbolForMouse(type);
    }

    public void setCurrentSymbol(Group symbol) {
        currSymbol = symbol;
    }

    public void setSymbolSelected(boolean symbool) {
        isSymbolSelected = symbool;
    }

    // Element controll methods

    /**
     * Adds element to content area
     * @param e
     */
    public void addElement(MouseEvent e) {
        if (isSymbolSelected) {
            Symbols currElement = null;
            double x = content.getTranslateX() + e.getX();
            double y = content.getTranslateY() + e.getY();
            switch (symbolType) {
                case "process":
                    currElement = new ProcessSymbol(controll);
                    break;
                case "decision":
                    currElement = new DecisionSymbol(controll);
                    break;
                case "terminator":
                    currElement = new TerminatorSymbol(controll);
                    break;
                case "text":
                    currElement = new TextSymbol(controll);
                    break;
            }
            if (currElement != null) {
                currElement.setTranslateX(x);
                currElement.setTranslateY(y);
                elements.add(currElement);
            }
            controllpane.clearSelection();
            controll.clearAll();

        } else {
            controll.clearAll();
        }
        deselectSymbols();
    }

    /**
     * Removes an element from content area.
     * @param currElement
     */
    public void removeElement(iObjects currElement) {
        if (currElement instanceof Symbols) {
            List<Connectors> currConnections = ((Symbols) currElement).getConnections();
            for (Connectors currConn : currConnections) {
                currConn.remove((Symbols) currElement);
            }
        } else if (currElement instanceof Connectors) {
            // remove only connector
        }
        elements.remove(currElement);

    }

    /**
     * Moves an element on content area.
     * @param e
     */
    public void moveElement(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            if (e.getSource() instanceof Symbols) {
                Symbols currElement = (Symbols) e.getSource();
                double x = currElement.getTranslateX() + e.getX();
                double y = currElement.getTranslateY() + e.getY();

                currElement.setTranslateX(x);
                currElement.setTranslateY(y);
                if (currElement.isConnected()) {
                    List<Connectors> currConnections = currElement.getConnections();
                    for (Connectors currConn : currConnections) {
                        currConn.update();
                    }
                }
                currElement.toFront();
            }
        }
    }

    /**
     * Changes element color. Either fill or stroke.
     * @param type
     * @param col
     */
    public void setElementColor(String type, Color col) {
        if (selectedElements.size() > 0) {
            for (Symbols currElement : selectedElements) {
                switch (type) {
                    case "fill":
                        currElement.setFillColor(col);
                        break;
                    case "stroke":
                        currElement.setStrokeColor(col);
                        break;
                }
            }
        }
    }

    /**
     * Adds connectors between all chosen elements in the order they were chosen.
     */
    public void addConnector() {
	    int numberOfElements = selectedElements.size();
        int index = 0;

        if (numberOfElements > 1) {

            while (index < numberOfElements - 1) {
                Symbols start = selectedElements.get(index);
                Symbols end = selectedElements.get(index + 1);


                if (start.isConnectable() && end.isConnectable()) {
                    ArrowSymbol connector = new ArrowSymbol(start, end, controll);
                    start.setConnected(connector);
                    end.setConnected(connector);
                    Path arrow = connector.getArrow();
                    connector.setPathReference(arrow);
                    elements.add(arrow);
                }
                index++;
            }
        } else {
            // "NOT ENOUGH OBJECTS ERROR"-dialog to implement.
        }
        deselectSymbols();
    }

    /**
     * Unselects all elements
     */
    private void deselectSymbols() {
        for (Symbols currElement: selectedElements){
            currElement.setDeselected();
        }
    }

    /**
     * Clear all elements from content area
     */
    public void removeAll() {
        elements.clear();
    }

    /**
     * Returns object to display under mouse when symbol is selected.
     * @param type
     * @return
     */
    private Group getSymbolForMouse(String type) {
        Group currSymbol = new Group();
        switch (type) {
            case "process":
                currSymbol = getRectangle();
                break;
            case "decision":
                currSymbol = getRomb();
                break;
            case "terminator":
                currSymbol = getEllipse();
                break;
            case "text":
                currSymbol = getTextSymbol();
                break;
        }
        currSymbol.setOpacity(0.3);
        return currSymbol;
    }

    /*
        Methods that returns shapes to use when symbol is selected.
     */

    private Group getRectangle() {
        Group currShape = new Group();
        Rectangle currSymbol = new Rectangle(60, 40);
        currSymbol.setFill(Color.WHITE);
        currSymbol.setStroke(Color.BLACK);
        currSymbol.setStrokeWidth(3);
        currShape.getChildren().add(currSymbol);
        return currShape;
    }

    private Group getRomb() {
        Group currShape = new Group();
        Rectangle currSymbol = new Rectangle(40, 40);
        currSymbol.setRotate(45);
        currSymbol.setFill(Color.WHITE);
        currSymbol.setStroke(Color.BLACK);
        currSymbol.setStrokeWidth(3);
        currShape.getChildren().add(currSymbol);
        return currShape;
    }

    private Group getEllipse() {
        Group currShape = new Group();
        Ellipse currSymbol = new Ellipse(40, 20);
        currSymbol.setFill(Color.WHITE);
        currSymbol.setStroke(Color.BLACK);
        currSymbol.setStrokeWidth(3);
        currShape.getChildren().add(currSymbol);
        return currShape;
    }

    private Group getTextSymbol() {
        Group currShape = new Group();
        Text currSymbol = new Text("Abc");
        currSymbol.setFontSmoothingType(FontSmoothingType.LCD);
        currSymbol.setFont(Font.font("Serif", 24));
        currSymbol.setFill(Color.BLACK);
        currShape.getChildren().add(currSymbol);
        return currShape;
    }
}
